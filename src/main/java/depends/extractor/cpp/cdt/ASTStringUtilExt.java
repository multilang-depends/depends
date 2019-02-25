package depends.extractor.cpp.cdt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNameSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTQualifiedName;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateId;
import org.eclipse.cdt.internal.core.model.ASTStringUtil;

import depends.relations.Inferer;

/**
 * This extends the CDT ASTStringUtil class.
 * A tricky point here is that we have to use some of the reflection mechanism to invoke 
 * some private functions in ASTStringUtils class
 * It is not good, but it seems the most easiest one to reuse existing functions
 */
public class ASTStringUtilExt extends ASTStringUtil {
	public static String getName(IASTDeclSpecifier decl) {
		StringBuilder buffer = new StringBuilder();
		return appendBareDeclSpecifierString(buffer, decl).toString();
	}

	public static String getTypeIdString(IASTTypeId typeId) {
		StringBuilder sb = new StringBuilder();
		return appendBareTypeIdString(sb, typeId).toString();
	}

	private static StringBuilder appendBareDeclSpecifierString(StringBuilder buffer, IASTDeclSpecifier declSpecifier) {
		if (declSpecifier instanceof IASTCompositeTypeSpecifier) {
			final IASTCompositeTypeSpecifier compositeTypeSpec = (IASTCompositeTypeSpecifier) declSpecifier;
			appendBareNameString(buffer, compositeTypeSpec.getName());
		} else if (declSpecifier instanceof IASTElaboratedTypeSpecifier) {
			final IASTElaboratedTypeSpecifier elaboratedTypeSpec = (IASTElaboratedTypeSpecifier) declSpecifier;
			appendBareNameString(buffer, elaboratedTypeSpec.getName());
		} else if (declSpecifier instanceof IASTEnumerationSpecifier) {
			final IASTEnumerationSpecifier enumerationSpec = (IASTEnumerationSpecifier) declSpecifier;
			appendBareNameString(buffer, enumerationSpec.getName());
		} else if (declSpecifier instanceof IASTSimpleDeclSpecifier) {
			buffer.append(Inferer.buildInType.getRawName());
		} else if (declSpecifier instanceof IASTNamedTypeSpecifier) {
			final IASTNamedTypeSpecifier namedTypeSpec = (IASTNamedTypeSpecifier) declSpecifier;
			appendBareNameString(buffer, namedTypeSpec.getName());
		}
		return buffer;
	}

	private static StringBuilder appendBareNameString(StringBuilder buffer, IASTName name) {
		if (name instanceof ICPPASTQualifiedName) {
			final ICPPASTQualifiedName qualifiedName = (ICPPASTQualifiedName) name;
			final ICPPASTNameSpecifier[] segments = qualifiedName.getAllSegments();
			for (int i = 0; i < segments.length; i++) {
				if (i > 0) {
					buffer.append(".");
				}
				appendQualifiedNameStringWithReflection(buffer, segments[i]);
			}
		} else if (name instanceof ICPPASTTemplateId) {
			final ICPPASTTemplateId templateId = (ICPPASTTemplateId) name;
			appendQualifiedNameStringWithReflection(buffer, templateId.getTemplateName());
		} else if (name != null) {
			buffer.append(name.getSimpleID());
		}
		return buffer;
	}

	private static void appendQualifiedNameStringWithReflection(StringBuilder buffer, IASTName name) {
		try {
			Method m = ASTStringUtil.class.getDeclaredMethod("appendQualifiedNameString", StringBuilder.class,
					IASTName.class);
			m.setAccessible(true); // if security settings allow this
			m.invoke(null, buffer, name); // use null if the method is static
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err.println("Error: cannot invoke ASTStringUtils method of <appendQualifiedNameString>");
		}
	}

	private static void appendQualifiedNameStringWithReflection(StringBuilder buffer,
			ICPPASTNameSpecifier nameSpecifier) {
		try {
			Method m = ASTStringUtil.class.getDeclaredMethod("appendQualifiedNameString", StringBuilder.class,
					ICPPASTNameSpecifier.class);
			m.setAccessible(true); // if security settings allow this
			m.invoke(null, buffer, nameSpecifier); // use null if the method is static
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err.println("Error: cannot invoke ASTStringUtils method of <appendQualifiedNameString>");
		}
	}

	private static StringBuilder appendBareTypeIdString(StringBuilder buffer, IASTTypeId typeId) {
		return appendBareDeclSpecifierString(buffer, typeId.getDeclSpecifier());
	}

}