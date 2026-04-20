package depends.extractor.java;

import depends.entity.GenericName;
import depends.entity.repo.EntityRepo;
import depends.importtypes.ExactMatchImport;
import depends.relations.IBindingResolver;
import org.treesitter.TSNode;
import org.treesitter.TSParser;
import org.treesitter.TSTree;
import org.treesitter.TreeSitterJava;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tree-sitter based Java parser.
 * Iteration-2 scope: package/import/type + extends/implements.
 */
public class JavaTreeSitterFileParser extends depends.extractor.FileParser {
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([\\w\\.]+)");
    private static final Pattern IMPORT_PATTERN = Pattern.compile("import\\s+(?:static\\s+)?([\\w\\.\\*]+)");
    private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("[A-Za-z_][A-Za-z0-9_$.]*");

    private final IBindingResolver bindingResolver;

    public JavaTreeSitterFileParser(EntityRepo entityRepo, IBindingResolver bindingResolver) {
        this.entityRepo = entityRepo;
        this.bindingResolver = bindingResolver;
    }

    @Override
    protected void parseFile(String fileFullPath) throws IOException {
        String source = new String(Files.readAllBytes(Paths.get(fileFullPath)), StandardCharsets.UTF_8);
        TSParser parser = new TSParser();
        if (!parser.setLanguage(new TreeSitterJava())) {
            throw new IOException("Failed to initialize Tree-sitter Java language");
        }
        TSTree tree = parser.parseString(null, source);
        JavaHandlerContext context = new JavaHandlerContext(entityRepo, bindingResolver);
        context.startFile(fileFullPath);
        walk(tree.getRootNode(), source, context);
    }

    private void walk(TSNode node, String source, JavaHandlerContext context) {
        String nodeType = node.getType();
        if ("package_declaration".equals(nodeType)) {
            processPackage(node, source, context);
            return;
        }
        if ("import_declaration".equals(nodeType)) {
            processImport(node, source, context);
            return;
        }
        if ("class_declaration".equals(nodeType)
                || "interface_declaration".equals(nodeType)
                || "enum_declaration".equals(nodeType)) {
            processType(node, source, context);
            return;
        }
        walkChildren(node, source, context);
    }

    private void walkChildren(TSNode node, String source, JavaHandlerContext context) {
        int childCount = node.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            TSNode child = node.getNamedChild(i);
            if (child == null || child.isNull()) {
                continue;
            }
            walk(child, source, context);
        }
    }

    private void processPackage(TSNode node, String source, JavaHandlerContext context) {
        String text = sourceSlice(node, source);
        Matcher matcher = PACKAGE_PATTERN.matcher(text);
        if (matcher.find()) {
            context.foundNewPackage(matcher.group(1));
        }
    }

    private void processImport(TSNode node, String source, JavaHandlerContext context) {
        String text = sourceSlice(node, source);
        Matcher matcher = IMPORT_PATTERN.matcher(text);
        if (!matcher.find()) {
            return;
        }
        String importName = matcher.group(1);
        if (importName.endsWith(".*")) {
            importName = importName.substring(0, importName.length() - 2);
        }
        context.foundNewImport(new ExactMatchImport(importName));
    }

    private void processType(TSNode node, String source, JavaHandlerContext context) {
        TSNode nameNode = node.getChildByFieldName("name");
        if (nameNode == null || nameNode.isNull()) {
            return;
        }
        String typeName = sourceSlice(nameNode, source).trim();
        int line = node.getStartPoint().getRow() + 1;
        context.foundNewType(GenericName.build(typeName), line);

        TSNode superClassNode = node.getChildByFieldName("superclass");
        if (superClassNode != null && !superClassNode.isNull()) {
            List<String> superTypes = extractTypeNames(sourceSlice(superClassNode, source));
            if (!superTypes.isEmpty()) {
                context.foundExtends(GenericName.build(superTypes.get(0)));
            }
        }

        TSNode interfacesNode = node.getChildByFieldName("interfaces");
        if (interfacesNode != null && !interfacesNode.isNull()) {
            List<String> interfaceTypes = extractTypeNames(sourceSlice(interfacesNode, source));
            if ("interface_declaration".equals(node.getType())) {
                for (String intf : interfaceTypes) {
                    context.foundExtends(GenericName.build(intf));
                }
            } else {
                for (String intf : interfaceTypes) {
                    context.foundImplements(GenericName.build(intf));
                }
            }
        }

        TSNode superInterfacesNode = node.getChildByFieldName("super_interfaces");
        if (superInterfacesNode != null && !superInterfacesNode.isNull()) {
            List<String> interfaceTypes = extractTypeNames(sourceSlice(superInterfacesNode, source));
            for (String intf : interfaceTypes) {
                context.foundExtends(GenericName.build(intf));
            }
        }

        walkChildren(node, source, context);
        context.exitLastedEntity();
    }

    private String sourceSlice(TSNode node, String source) {
        int start = Math.max(0, node.getStartByte());
        int end = Math.min(source.length(), node.getEndByte());
        if (start >= end) {
            return "";
        }
        return source.substring(start, end);
    }

    private List<String> extractTypeNames(String text) {
        String normalized = text
                .replace("extends", " ")
                .replace("implements", " ")
                .replace("<", " ")
                .replace(">", " ")
                .replace("&", " ");
        Matcher matcher = TYPE_NAME_PATTERN.matcher(normalized);
        List<String> names = new ArrayList<>();
        while (matcher.find()) {
            String candidate = matcher.group();
            if ("extends".equals(candidate) || "implements".equals(candidate)) {
                continue;
            }
            names.add(candidate);
        }
        return names;
    }
}
