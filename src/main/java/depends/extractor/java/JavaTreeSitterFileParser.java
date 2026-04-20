package depends.extractor.java;

import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.Expression;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.Entity;
import depends.entity.DecoratedEntity;
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
                || "enum_declaration".equals(nodeType)
                || "annotation_type_declaration".equals(nodeType)) {
            processType(node, source, context);
            return;
        }
        if ("method_declaration".equals(nodeType)) {
            processMethod(node, source, context);
            return;
        }
        if ("constructor_declaration".equals(nodeType)) {
            processConstructor(node, source, context);
            return;
        }
        if ("field_declaration".equals(nodeType)) {
            processField(node, source, context);
            return;
        }
        if ("constant_declaration".equals(nodeType)) {
            processField(node, source, context);
            return;
        }
        if ("enum_constant".equals(nodeType)) {
            processEnumConstant(node, source, context);
            return;
        }
        if ("resource".equals(nodeType)) {
            processResource(node, source, context);
            return;
        }
        if ("enhanced_for_statement".equals(nodeType)) {
            processEnhancedForStatement(node, source, context);
            return;
        }
        if ("local_variable_declaration".equals(nodeType)) {
            processLocalVariable(node, source, context);
            return;
        }
        if ("instanceof_expression".equals(nodeType)) {
            processInstanceofExpression(node, source, context);
            return;
        }
        if ("method_invocation".equals(nodeType)) {
            processMethodInvocation(node, source, context);
            return;
        }
        if ("object_creation_expression".equals(nodeType)) {
            processObjectCreation(node, source, context);
            return;
        }
        if ("cast_expression".equals(nodeType)) {
            processCast(node, source, context);
            return;
        }
        if ("assignment_expression".equals(nodeType) || "update_expression".equals(nodeType)) {
            processSetLikeExpression(node, source, context);
            return;
        }
        if ("field_access".equals(nodeType)) {
            processFieldAccess(node, source, context);
            return;
        }
        if ("lambda_expression".equals(nodeType)) {
            processLambdaExpression(node, source, context);
            return;
        }
        if ("method_reference".equals(nodeType)) {
            processMethodReference(node, source, context);
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
        TypeEntity typeEntity = context.foundNewType(GenericName.build(typeName), line);
        applyAnnotations(node, source, typeEntity);
        TSNode typeParametersNode = findChildByType(node, "type_parameters");
        processTypeParameters(typeParametersNode, source, context, true);

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

    private void processMethod(TSNode node, String source, JavaHandlerContext context) {
        TSNode declarator = findChildByType(node, "method_declarator");
        if (declarator == null) {
            declarator = node;
        }
        TSNode nameNode = declarator.getChildByFieldName("name");
        if (nameNode == null || nameNode.isNull()) {
            nameNode = node.getChildByFieldName("name");
        }
        if (nameNode == null || nameNode.isNull()) {
            nameNode = findFirstDescendantByType(declarator, "identifier");
        }
        if (nameNode == null || nameNode.isNull()) {
            return;
        }
        String methodName = sourceSlice(nameNode, source).trim();
        String returnType = extractMethodReturnType(node, source);
        List<String> throwedTypes = extractThrows(node, source);
        int line = node.getStartPoint().getRow() + 1;
        FunctionEntity method = context.foundMethodDeclarator(methodName, returnType, throwedTypes, line);
        applyAnnotations(node, source, method);
        processTypeParameters(findChildByType(node, "type_parameters"), source, context, false);
        addFormalParameters(declarator, source, context);
        walkChildren(node, source, context);
        context.exitLastedEntity();
    }

    private void processConstructor(TSNode node, String source, JavaHandlerContext context) {
        TSNode declarator = findChildByType(node, "constructor_declarator");
        if (declarator == null) {
            declarator = node;
        }
        TSNode nameNode = declarator.getChildByFieldName("name");
        if (nameNode == null || nameNode.isNull()) {
            nameNode = node.getChildByFieldName("name");
        }
        if (nameNode == null || nameNode.isNull()) {
            nameNode = findFirstDescendantByType(declarator, "identifier");
        }
        if (nameNode == null || nameNode.isNull()) {
            return;
        }
        String constructorName = sourceSlice(nameNode, source).trim();
        List<String> throwedTypes = extractThrows(node, source);
        int line = node.getStartPoint().getRow() + 1;
        FunctionEntity method = context.foundMethodDeclarator(constructorName, constructorName, throwedTypes, line);
        applyAnnotations(node, source, method);
        addFormalParameters(declarator, source, context);
        if (context.currentType() != null) {
            method.addReturnType(context.currentType());
        }
        walkChildren(node, source, context);
        context.exitLastedEntity();
    }

    private void processField(TSNode node, String source, JavaHandlerContext context) {
        String fieldType = extractFieldType(node, source);
        List<String> varNames = extractVariableNames(node, source);
        if (fieldType.isEmpty() || varNames.isEmpty()) {
            walkChildren(node, source, context);
            return;
        }
        List<VarEntity> vars = context.foundVarDefinitions(varNames, fieldType, new ArrayList<>(),
                node.getStartPoint().getRow() + 1);
        applyAnnotations(node, source, vars);
        walkChildren(node, source, context);
    }

    private void processLocalVariable(TSNode node, String source, JavaHandlerContext context) {
        String varType = extractFieldType(node, source);
        List<String> varNames = extractVariableNames(node, source);
        if (varType.isEmpty() || varNames.isEmpty()) {
            walkChildren(node, source, context);
            return;
        }
        List<VarEntity> vars = context.foundVarDefinitions(varNames, varType, new ArrayList<>(),
                node.getStartPoint().getRow() + 1);
        applyAnnotations(node, source, vars);
        walkChildren(node, source, context);
    }

    private void processEnumConstant(TSNode node, String source, JavaHandlerContext context) {
        TSNode nameNode = node.getChildByFieldName("name");
        if (nameNode == null || nameNode.isNull()) {
            nameNode = findFirstDescendantByType(node, "identifier");
        }
        if (nameNode == null || nameNode.isNull()) {
            walkChildren(node, source, context);
            return;
        }
        String enumConstName = sourceSlice(nameNode, source).trim();
        if (enumConstName.isEmpty()) {
            walkChildren(node, source, context);
            return;
        }
        VarEntity enumConst = context.foundEnumConstDefinition(enumConstName, node.getStartPoint().getRow() + 1);
        applyAnnotations(node, source, enumConst);
        walkChildren(node, source, context);
    }

    private void processResource(TSNode node, String source, JavaHandlerContext context) {
        String resourceType = extractFieldType(node, source);
        List<String> resourceNames = extractVariableNames(node, source);
        if (!resourceType.isEmpty() && !resourceNames.isEmpty()) {
            context.foundVarDefinitions(resourceNames, resourceType, new ArrayList<>(),
                    node.getStartPoint().getRow() + 1);
        }
        walkChildren(node, source, context);
    }

    private void processEnhancedForStatement(TSNode node, String source, JavaHandlerContext context) {
        TSNode typeNode = node.getChildByFieldName("type");
        TSNode nameNode = node.getChildByFieldName("name");
        String varType = typeNode == null || typeNode.isNull() ? "" : sourceSlice(typeNode, source).trim();
        String varName = nameNode == null || nameNode.isNull() ? "" : sourceSlice(nameNode, source).trim();
        if (varType.isEmpty() || varName.isEmpty()) {
            Matcher matcher = Pattern.compile("for\\s*\\(\\s*([A-Za-z_][A-Za-z0-9_$.<>]*)\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*:")
                    .matcher(sourceSlice(node, source));
            if (matcher.find()) {
                varType = matcher.group(1);
                varName = matcher.group(2);
            }
        }
        if (!varType.isEmpty() && !varName.isEmpty()) {
            context.foundVarDefinition(varName, GenericName.build(varType), new ArrayList<>(),
                    node.getStartPoint().getRow() + 1);
        }
        walkChildren(node, source, context);
    }

    private void processInstanceofExpression(TSNode node, String source, JavaHandlerContext context) {
        TSNode rightNode = node.getChildByFieldName("right");
        if (rightNode != null && !rightNode.isNull()) {
            Matcher matcher = Pattern.compile("([A-Za-z_][A-Za-z0-9_$.]*)\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*$")
                    .matcher(sourceSlice(rightNode, source).trim());
            if (matcher.find()) {
                String typeName = matcher.group(1);
                String varName = matcher.group(2);
                context.foundVarDefinition(varName, GenericName.build(typeName), new ArrayList<>(),
                        node.getStartPoint().getRow() + 1);
            }
        }
        walkChildren(node, source, context);
    }

    private void processMethodInvocation(TSNode node, String source, JavaHandlerContext context) {
        TSNode nameNode = node.getChildByFieldName("name");
        if (nameNode == null || nameNode.isNull()) {
            nameNode = findFirstDescendantByType(node, "identifier");
        }
        if (nameNode == null || nameNode.isNull()) {
            return;
        }
        String callName = sourceSlice(nameNode, source).trim();
        TSNode objectNode = node.getChildByFieldName("object");
        boolean isDotCall = objectNode != null && !objectNode.isNull();
        Expression callExpression = addExpression(context, node, null, callName,
                true, isDotCall, false, false, false, false);
        if (isDotCall) {
            addObjectExpression(objectNode, source, context, callExpression);
        }
        processInvocationArguments(node, source, context);
    }

    private void processObjectCreation(TSNode node, String source, JavaHandlerContext context) {
        TSNode typeNode = node.getChildByFieldName("type");
        if (typeNode == null || typeNode.isNull()) {
            typeNode = findFirstDescendantByType(node, "type_identifier");
        }
        String typeName = typeNode == null ? "" : sourceSlice(typeNode, source).trim();
        if (typeName.isEmpty()) {
            return;
        }
        Expression expression = addExpression(context, node, null, null,
                true, false, true, false, false, false);
        expression.setRawType(typeName);
        expression.disableDriveTypeFromChild();
        walkChildren(node, source, context);
    }

    private void processCast(TSNode node, String source, JavaHandlerContext context) {
        TSNode typeNode = node.getChildByFieldName("type");
        if (typeNode == null || typeNode.isNull()) {
            typeNode = findFirstDescendantByType(node, "type_identifier");
        }
        String typeName = typeNode == null ? "" : sourceSlice(typeNode, source).trim();
        if (typeName.isEmpty()) {
            return;
        }
        Expression expression = addExpression(context, node, null, null,
                false, false, false, true, false, false);
        expression.setRawType(typeName);
        expression.disableDriveTypeFromChild();
    }

    private void processSetLikeExpression(TSNode node, String source, JavaHandlerContext context) {
        TSNode leftNode = node.getChildByFieldName("left");
        if (leftNode == null || leftNode.isNull()) {
            leftNode = findFirstDescendantByType(node, "identifier");
        }
        if (leftNode == null || leftNode.isNull()) {
            return;
        }
        String leftIdentifier = extractSimpleIdentifier(leftNode, source);
        if (leftIdentifier.isEmpty()) {
            return;
        }
        addExpression(context, node, null, leftIdentifier,
                false, false, false, false, true, false);
    }

    private void processFieldAccess(TSNode node, String source, JavaHandlerContext context) {
        TSNode fieldNode = node.getChildByFieldName("field");
        if (fieldNode == null || fieldNode.isNull()) {
            fieldNode = findFirstDescendantByType(node, "identifier");
        }
        if (fieldNode == null || fieldNode.isNull()) {
            return;
        }
        String fieldName = sourceSlice(fieldNode, source).trim();
        if (fieldName.isEmpty()) {
            return;
        }
        Expression fieldExpression = addExpression(context, node, null, fieldName,
                false, true, false, false, false, false);
        TSNode objectNode = node.getChildByFieldName("object");
        if (objectNode != null && !objectNode.isNull()) {
            addObjectExpression(objectNode, source, context, fieldExpression);
        }
    }

    private void processLambdaExpression(TSNode node, String source, JavaHandlerContext context) {
        addLambdaParameters(node, source, context);
        walkChildren(node, source, context);
    }

    private void processMethodReference(TSNode node, String source, JavaHandlerContext context) {
        String raw = sourceSlice(node, source).trim();
        if (raw.isEmpty() || !raw.contains("::")) {
            return;
        }
        String[] parts = raw.split("::", 2);
        if (parts.length != 2) {
            return;
        }
        String left = parts[0].trim();
        String right = parts[1].trim();
        if (left.isEmpty() || right.isEmpty()) {
            return;
        }
        if ("new".equals(right)) {
            Expression createExpr = addExpression(context, node, null, null,
                    true, false, true, false, false, false);
            createExpr.setRawType(left);
            createExpr.disableDriveTypeFromChild();
            return;
        }

        Expression refCall = addExpression(context, node, null, right,
                true, true, false, false, false, false);
        addExpression(context, node, refCall, left,
                false, false, false, false, false, false);
    }

    private void processTypeParameters(TSNode typeParametersNode,
                                       String source,
                                       JavaHandlerContext context,
                                       boolean addDeclaredGenericToCurrentType) {
        if (typeParametersNode == null || typeParametersNode.isNull()) {
            return;
        }
        List<TSNode> params = findChildrenByType(typeParametersNode, "type_parameter");
        for (TSNode param : params) {
            TSNode nameNode = param.getChildByFieldName("name");
            String genericName = "";
            if (nameNode != null && !nameNode.isNull()) {
                genericName = sourceSlice(nameNode, source).trim();
            }
            if (addDeclaredGenericToCurrentType && context.currentType() != null && !genericName.isEmpty()) {
                context.currentType().addTypeParameter(GenericName.build(genericName));
            }

            String rawParam = sourceSlice(param, source).trim();
            List<String> boundNames = extractTypeNames(rawParam);
            for (String bound : boundNames) {
                if (bound.equals(genericName)) {
                    continue;
                }
                context.foundTypeParametes(GenericName.build(bound));
            }
        }
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

    private TSNode findChildByType(TSNode node, String childType) {
        int childCount = node.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            TSNode child = node.getNamedChild(i);
            if (child != null && !child.isNull() && childType.equals(child.getType())) {
                return child;
            }
        }
        return null;
    }

    private List<TSNode> findChildrenByType(TSNode node, String childType) {
        List<TSNode> result = new ArrayList<>();
        int childCount = node.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            TSNode child = node.getNamedChild(i);
            if (child != null && !child.isNull() && childType.equals(child.getType())) {
                result.add(child);
            }
        }
        return result;
    }

    private String extractMethodReturnType(TSNode methodNode, String source) {
        int childCount = methodNode.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            TSNode child = methodNode.getNamedChild(i);
            if (child == null || child.isNull()) {
                continue;
            }
            String type = child.getType();
            if ("modifiers".equals(type) || "type_parameters".equals(type) || "method_declarator".equals(type)
                    || "throws".equals(type) || "block".equals(type)) {
                continue;
            }
            return sourceSlice(child, source).trim();
        }
        return "";
    }

    private List<String> extractThrows(TSNode node, String source) {
        TSNode throwsNode = findChildByType(node, "throws");
        if (throwsNode == null) {
            return new ArrayList<>();
        }
        return extractTypeNames(sourceSlice(throwsNode, source));
    }

    private void addFormalParameters(TSNode declarator, String source, JavaHandlerContext context) {
        TSNode parametersNode = declarator.getChildByFieldName("parameters");
        if (parametersNode == null || parametersNode.isNull()) {
            parametersNode = findChildByType(declarator, "formal_parameters");
        }
        if (parametersNode == null || parametersNode.isNull()) {
            parametersNode = findFirstDescendantByType(declarator, "formal_parameters");
        }
        if (parametersNode == null || parametersNode.isNull()) {
            return;
        }

        List<TSNode> parameterNodes = new ArrayList<>();
        parameterNodes.addAll(findChildrenByType(parametersNode, "formal_parameter"));
        parameterNodes.addAll(findChildrenByType(parametersNode, "spread_parameter"));
        for (TSNode paramNode : parameterNodes) {
            TSNode nameNode = paramNode.getChildByFieldName("name");
            TSNode typeNode = paramNode.getChildByFieldName("type");
            if (nameNode == null || nameNode.isNull()) {
                continue;
            }
            String paramName = sourceSlice(nameNode, source).trim();
            VarEntity param = context.addMethodParameter(paramName);
            if (param == null) {
                continue;
            }
            if (typeNode != null && !typeNode.isNull()) {
                param.setRawType(GenericName.build(sourceSlice(typeNode, source).trim()));
            }
        }
    }

    private String extractFieldType(TSNode fieldNode, String source) {
        TSNode typeNode = fieldNode.getChildByFieldName("type");
        if (typeNode != null && !typeNode.isNull()) {
            return sourceSlice(typeNode, source).trim();
        }
        int childCount = fieldNode.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            TSNode child = fieldNode.getNamedChild(i);
            if (child == null || child.isNull()) {
                continue;
            }
            if (!"modifiers".equals(child.getType()) && !"variable_declarator".equals(child.getType())) {
                return sourceSlice(child, source).trim();
            }
        }
        return "";
    }

    private List<String> extractVariableNames(TSNode fieldNode, String source) {
        List<String> names = new ArrayList<>();
        List<TSNode> declarators = findChildrenByType(fieldNode, "variable_declarator");
        for (TSNode declarator : declarators) {
            TSNode nameNode = declarator.getChildByFieldName("name");
            if (nameNode != null && !nameNode.isNull()) {
                names.add(sourceSlice(nameNode, source).trim());
            }
        }
        return names;
    }

    private TSNode findFirstDescendantByType(TSNode node, String targetType) {
        int childCount = node.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            TSNode child = node.getNamedChild(i);
            if (child == null || child.isNull()) {
                continue;
            }
            if (targetType.equals(child.getType())) {
                return child;
            }
            TSNode found = findFirstDescendantByType(child, targetType);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private Expression addExpression(JavaHandlerContext context,
                                     TSNode keyNode,
                                     Expression parent,
                                     String identifier,
                                     boolean call,
                                     boolean dot,
                                     boolean create,
                                     boolean cast,
                                     boolean set,
                                     boolean logic) {
        if (context.lastContainer() == null) {
            return new Expression(entityRepo.generateId());
        }
        Expression expression = new Expression(entityRepo.generateId());
        expression.setLine(keyNode.getStartPoint().getRow() + 1);
        expression.setParent(parent);
        expression.setCall(call);
        expression.setDot(dot);
        expression.setCreate(create);
        expression.setCast(cast);
        expression.setSet(set);
        expression.setLogic(logic);
        if (identifier != null && !identifier.isEmpty()) {
            expression.setIdentifier(identifier);
        }
        // TSNode is stable enough as expression key during one parse walk.
        context.lastContainer().addExpression(keyNode, expression);
        return expression;
    }

    private String extractSimpleIdentifier(TSNode node, String source) {
        if ("identifier".equals(node.getType())) {
            return sourceSlice(node, source).trim();
        }
        TSNode idNode = findFirstDescendantByType(node, "identifier");
        if (idNode != null && !idNode.isNull()) {
            return sourceSlice(idNode, source).trim();
        }
        String raw = sourceSlice(node, source).trim();
        Matcher matcher = TYPE_NAME_PATTERN.matcher(raw);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private void addObjectExpression(TSNode objectNode, String source, JavaHandlerContext context, Expression parent) {
        if (objectNode == null || objectNode.isNull()) {
            return;
        }
        String objectType = objectNode.getType();
        if ("method_invocation".equals(objectType)) {
            TSNode nameNode = objectNode.getChildByFieldName("name");
            if (nameNode == null || nameNode.isNull()) {
                nameNode = findFirstDescendantByType(objectNode, "identifier");
            }
            if (nameNode == null || nameNode.isNull()) {
                return;
            }
            String nestedName = sourceSlice(nameNode, source).trim();
            TSNode nestedObject = objectNode.getChildByFieldName("object");
            boolean nestedDot = nestedObject != null && !nestedObject.isNull();
            Expression nestedCall = addExpression(context, objectNode, parent, nestedName,
                    true, nestedDot, false, false, false, false);
            if (nestedDot) {
                addObjectExpression(nestedObject, source, context, nestedCall);
            }
            return;
        }

        String objectIdentifier = extractSimpleIdentifier(objectNode, source);
        if (objectIdentifier.isEmpty()) {
            return;
        }
        addExpression(context, objectNode, parent, objectIdentifier,
                false, false, false, false, false, false);
    }

    private void processInvocationArguments(TSNode invocationNode, String source, JavaHandlerContext context) {
        TSNode argumentsNode = invocationNode.getChildByFieldName("arguments");
        if (argumentsNode == null || argumentsNode.isNull()) {
            argumentsNode = findChildByType(invocationNode, "argument_list");
        }
        if (argumentsNode == null || argumentsNode.isNull()) {
            return;
        }
        int childCount = argumentsNode.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            TSNode child = argumentsNode.getNamedChild(i);
            if (child == null || child.isNull()) {
                continue;
            }
            walk(child, source, context);
        }
    }

    private void addLambdaParameters(TSNode lambdaNode, String source, JavaHandlerContext context) {
        TSNode parametersNode = lambdaNode.getChildByFieldName("parameters");
        if (parametersNode == null || parametersNode.isNull()) {
            parametersNode = findChildByType(lambdaNode, "formal_parameters");
        }
        if (parametersNode == null || parametersNode.isNull()) {
            parametersNode = findChildByType(lambdaNode, "inferred_parameters");
        }
        if (parametersNode == null || parametersNode.isNull()) {
            return;
        }

        List<TSNode> explicitParameters = findChildrenByType(parametersNode, "formal_parameter");
        if (!explicitParameters.isEmpty()) {
            for (TSNode paramNode : explicitParameters) {
                TSNode nameNode = paramNode.getChildByFieldName("name");
                TSNode typeNode = paramNode.getChildByFieldName("type");
                if (nameNode == null || nameNode.isNull()) {
                    continue;
                }
                String paramName = sourceSlice(nameNode, source).trim();
                if (paramName.isEmpty()) {
                    continue;
                }
                String paramType = (typeNode == null || typeNode.isNull())
                        ? ""
                        : sourceSlice(typeNode, source).trim();
                if (paramType.isEmpty()) {
                    context.foundVarDefinition(paramName, GenericName.build("Object"), new ArrayList<>(),
                            paramNode.getStartPoint().getRow() + 1);
                } else {
                    context.foundVarDefinition(paramName, GenericName.build(paramType), new ArrayList<>(),
                            paramNode.getStartPoint().getRow() + 1);
                }
            }
            return;
        }

        List<TSNode> inferredParameters = findChildrenByType(parametersNode, "identifier");
        for (TSNode inferred : inferredParameters) {
            String paramName = sourceSlice(inferred, source).trim();
            if (paramName.isEmpty()) {
                continue;
            }
            context.foundVarDefinition(paramName, GenericName.build("Object"), new ArrayList<>(),
                    inferred.getStartPoint().getRow() + 1);
        }
    }

    private void applyAnnotations(TSNode node, String source, Entity entity) {
        if (!(entity instanceof DecoratedEntity)) {
            return;
        }
        List<GenericName> annotations = collectAnnotations(node, source);
        for (GenericName annotation : annotations) {
            ((DecoratedEntity) entity).addAnnotation(annotation);
        }
    }

    private void applyAnnotations(TSNode node, String source, List<VarEntity> vars) {
        if (vars == null || vars.isEmpty()) {
            return;
        }
        List<GenericName> annotations = collectAnnotations(node, source);
        if (annotations.isEmpty()) {
            return;
        }
        for (VarEntity var : vars) {
            for (GenericName annotation : annotations) {
                var.addAnnotation(annotation);
            }
        }
    }

    private List<GenericName> collectAnnotations(TSNode node, String source) {
        List<GenericName> annotations = new ArrayList<>();
        TSNode modifiers = findChildByType(node, "modifiers");
        if (modifiers == null || modifiers.isNull()) {
            return annotations;
        }
        collectAnnotationFromDescendants(modifiers, source, annotations);
        return annotations;
    }

    private void collectAnnotationFromDescendants(TSNode node, String source, List<GenericName> annotations) {
        if (node == null || node.isNull()) {
            return;
        }
        String nodeType = node.getType();
        if ("marker_annotation".equals(nodeType) || "annotation".equals(nodeType)) {
            GenericName annotation = extractAnnotationName(node, source);
            if (annotation != null) {
                annotations.add(annotation);
            }
            return;
        }
        int childCount = node.getNamedChildCount();
        for (int i = 0; i < childCount; i++) {
            collectAnnotationFromDescendants(node.getNamedChild(i), source, annotations);
        }
    }

    private GenericName extractAnnotationName(TSNode annotationNode, String source) {
        TSNode nameNode = annotationNode.getChildByFieldName("name");
        String raw;
        if (nameNode != null && !nameNode.isNull()) {
            raw = sourceSlice(nameNode, source).trim();
        } else {
            raw = sourceSlice(annotationNode, source).trim();
            if (raw.startsWith("@")) {
                raw = raw.substring(1);
            }
            int leftParen = raw.indexOf('(');
            if (leftParen >= 0) {
                raw = raw.substring(0, leftParen).trim();
            }
        }
        if (raw.isEmpty()) {
            return null;
        }
        return GenericName.build(raw);
    }
}
