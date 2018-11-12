package depends.extractor.cpp.g4elsa.helper;

import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.CElsaParser.ClassHeadContext;
import depends.javaextractor.CElsaParser.ClassNameContext;
import depends.javaextractor.CElsaParser.ClassOrNamespaceNameContext;
import depends.javaextractor.CElsaParser.ClassSpecifierContext;
import depends.javaextractor.CElsaParser.ConversionFunctionIdContext;
import depends.javaextractor.CElsaParser.DeclSpecifierContext;
import depends.javaextractor.CElsaParser.DeclaratorContext;
import depends.javaextractor.CElsaParser.DeclaratorIdContext;
import depends.javaextractor.CElsaParser.ElaboratedTypeSpecifierContext;
import depends.javaextractor.CElsaParser.EnumNameContext;
import depends.javaextractor.CElsaParser.EnumSpecifierContext;
import depends.javaextractor.CElsaParser.IdExpressionContext;
import depends.javaextractor.CElsaParser.NamespaceNameContext;
import depends.javaextractor.CElsaParser.NestedNameSpecifierContext;
import depends.javaextractor.CElsaParser.OperatorFunctionIdContext;
import depends.javaextractor.CElsaParser.PtrOperatorContext;
import depends.javaextractor.CElsaParser.QualifiedIdContext;
import depends.javaextractor.CElsaParser.SimpleTypeSpecifierContext;
import depends.javaextractor.CElsaParser.TemplateIdContext;
import depends.javaextractor.CElsaParser.TypeNameContext;
import depends.javaextractor.CElsaParser.TypeSpecifierContext;
import depends.javaextractor.CElsaParser.TypedefNameContext;
import depends.javaextractor.CElsaParser.UnqualifiedIdContext;

public class ContextHelper {
	public String getName(DeclaratorContext declarator) {
		List<DeclaratorIdContext> ids = this.foundDeclaratorIds(declarator);
		if (ids.size()>0){
			DeclaratorIdContext funcNameDeclarator = ids.get(ids.size()-1);
			return getName(funcNameDeclarator);
		}
		return "";
	}
	
	public String getName(DeclaratorIdContext funcNameDeclarator) {
		if (funcNameDeclarator.idExpression()!=null) {
			return getName(funcNameDeclarator.idExpression());
		}else if (funcNameDeclarator.typeName()!=null) {
			StringBuilder qualifiedNamePrefix = new StringBuilder();
			if (funcNameDeclarator.getText().startsWith("::")) {
				qualifiedNamePrefix.append("::");
			}
			if (funcNameDeclarator.nestedNameSpecifier()!=null) {
				qualifiedNamePrefix.append(getName(funcNameDeclarator.nestedNameSpecifier()));
			}
			qualifiedNamePrefix.append(getName(funcNameDeclarator.typeName()));
			return qualifiedNamePrefix.toString() + getName(funcNameDeclarator.typeName());
		}
		return "";
	}

	public String getName(IdExpressionContext idExpression) {
		if (idExpression.unqualifiedId()!=null) return getName(idExpression.unqualifiedId());
		if (idExpression.qualifiedId()!=null) return getName(idExpression.qualifiedId());
		return "";
	}

	public String getName(UnqualifiedIdContext unqualifiedId) {
		if (unqualifiedId.Identifier()!=null) return unqualifiedId.Identifier().getText();
		if (unqualifiedId.className()!=null) return getName(unqualifiedId.className());
		if (unqualifiedId.operatorFunctionId()!=null) return getName(unqualifiedId.operatorFunctionId());
		if (unqualifiedId.conversionFunctionId()!=null) return getName(unqualifiedId.conversionFunctionId());
		if (unqualifiedId.templateId()!=null) return getName(unqualifiedId.templateId());
		return "";
	}

	public String getName(TemplateIdContext templateId) {
		return templateId.templateName().Identifier().getText();
	}

	public String getName(ConversionFunctionIdContext conversionFunctionId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<operator>");
		for (TypeSpecifierContext typeSpecifier:conversionFunctionId.typeSpecifier()) {
			sb.append(getName(typeSpecifier));
		}
		return  sb.toString();
	}

	public String getName(TypeSpecifierContext typeSpecifier) {
		if (typeSpecifier.simpleTypeSpecifier()!=null) return getName(typeSpecifier.simpleTypeSpecifier());
		if (typeSpecifier.classSpecifier()!=null) return getName(typeSpecifier.classSpecifier());
		if (typeSpecifier.enumSpecifier()!=null) return getName(typeSpecifier.enumSpecifier());
		if (typeSpecifier.elaboratedTypeSpecifier()!=null) return getName(typeSpecifier.elaboratedTypeSpecifier());
		//if (typeSpecifier.cvQualifier()!=null)  WE IGNORE Const Votiale
		if (typeSpecifier.ptrOperator()!=null) return getName(typeSpecifier.ptrOperator());
		return "";
	}

	public String getName(EnumSpecifierContext enumSpecifier) {
		if (enumSpecifier.Identifier()!=null) return enumSpecifier.Identifier().getText();
		return "<enum>anonymous";
	}

	public String getName(PtrOperatorContext ptrOperator) {
		//WE IGNORE ptr related behavior for dependency calculation
		return "";
	}

	public String getName(ElaboratedTypeSpecifierContext elaboratedTypeSpecifier) {
		StringBuilder sb = new StringBuilder();
		if (elaboratedTypeSpecifier.DotDot()!=null) sb.append("::");
		if (elaboratedTypeSpecifier.nestedNameSpecifier()!=null) sb.append(getName(elaboratedTypeSpecifier.nestedNameSpecifier()));
		if (elaboratedTypeSpecifier.Identifier()!=null) sb.append(elaboratedTypeSpecifier.Identifier().getText());
		if (elaboratedTypeSpecifier.templateId()!=null) sb.append(getName(elaboratedTypeSpecifier.templateId()));
		return sb.toString();
	}

	

	public String getName(ClassSpecifierContext classSpecifier) {
		return getName(classSpecifier.classHead());
	}

	public String getName(ClassHeadContext classHead) {
		StringBuilder sb = new StringBuilder();
		if (classHead.nestedNameSpecifier()!=null) sb.append(getName(classHead.nestedNameSpecifier()));
		if (classHead.Identifier()!=null) sb.append(classHead.Identifier().getText());
		if (classHead.templateId()!=null) sb.append(getName(classHead.templateId()));
		return sb.toString();
	}

	public String getName(SimpleTypeSpecifierContext simpleTypeSpecifier) {
		StringBuilder sb = new StringBuilder();
		if (simpleTypeSpecifier.DotDot()!=null)
			sb.append("::");
		if (simpleTypeSpecifier.nestedNameSpecifier()!=null) sb.append(getName(simpleTypeSpecifier.nestedNameSpecifier()));
		if (simpleTypeSpecifier.templateId()!=null) sb.append(getName(simpleTypeSpecifier.templateId()));
		if (sb.length()==0) return simpleTypeSpecifier.getText(); //basic type
		return sb.toString();
	}

	public String getName(OperatorFunctionIdContext operatorFunctionId) {
		return "<operator>" + operatorFunctionId.operatorFunc().getText();
	}

	public String getName(QualifiedIdContext qualifiedId) {
		StringBuilder sb = new StringBuilder();
		if (qualifiedId.DotDot()!=null)
			sb.append("::");
		if (qualifiedId.nestedNameSpecifier()!=null) sb.append(getName(qualifiedId.nestedNameSpecifier()));
		if (qualifiedId.templateId()!=null) sb.append(getName(qualifiedId.templateId()));
		if (qualifiedId.operatorFunctionId()!=null) sb.append(getName(qualifiedId.operatorFunctionId()));
		if (qualifiedId.Identifier()!=null) sb.append(qualifiedId.Identifier().getText());
		if (qualifiedId.unqualifiedId()!=null) sb.append(getName(qualifiedId.unqualifiedId()));
		if (qualifiedId.NestedName()!=null) sb.append(qualifiedId.NestedName());
		return sb.toString();
	}

	public String getName(TypeNameContext typeName) {
		if (typeName.className()!=null) return getName(typeName.className());
		if (typeName.enumName()!=null) return getName(typeName.enumName());
		if (typeName.typedefName()!=null) return getName(typeName.typedefName());
		return "";
	}
	public String getName(TypedefNameContext typedefName) {
		return typedefName.Identifier().getText();
	}

	public String getName(EnumNameContext enumName) {
		return enumName.Identifier().getText();
	}

	public String getName(NestedNameSpecifierContext ctx) {
		StringBuilder sb = new StringBuilder();
		sb.append(getName(ctx.classOrNamespaceName())).append("::");
		NestedNameSpecifierContext next = ctx.nestedNameSpecifier();
		while(next!=null) {
			sb.append(getName(next.classOrNamespaceName())).append("::");
			next = ctx.nestedNameSpecifier();
			//we ignore the 'template' in namespace specifier
		}
		return sb.toString();
	}

	public String getName(ClassOrNamespaceNameContext ctx) {
		if (ctx.className()!=null) return getName(ctx.className());
		if (ctx.namespaceName()!=null) return getName(ctx.namespaceName());
		return "";
	}

	public String getName(NamespaceNameContext namespaceName) {
		if (namespaceName.originalNamespaceName()!=null) return namespaceName.originalNamespaceName().Identifier().getText();
		if (namespaceName.namespaceAlias()!=null) return namespaceName.namespaceAlias().Identifier().getText();
		return "";
	}

	public String getName(ClassNameContext className) {
		if (className.Identifier()!=null) return className.Identifier().getText();
		if (className.templateId()!=null) return getName(className.templateId());
		return "";
	}

	
	public List<DeclaratorIdContext> foundDeclaratorIds(DeclaratorContext declarator) {
		while(true) {
			if (declarator.declaratorId()!=null)
				return declarator.declaratorId();
			declarator = declarator.declarator();
			if (declarator==null) return new ArrayList<>();
		}
	}
	
	public String getTypeNameOf(List<DeclSpecifierContext> declSpecifiers) {
		if (declSpecifiers==null) return "";
		for (DeclSpecifierContext declSpecifier:declSpecifiers) {
			if (declSpecifier.typeSpecifier()==null) continue;
				return getName(declSpecifier.typeSpecifier());
		}
		return "";
	}

}
