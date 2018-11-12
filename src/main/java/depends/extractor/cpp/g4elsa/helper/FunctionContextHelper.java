package depends.extractor.cpp.g4elsa.helper;
import java.util.ArrayList;
import java.util.List;

import depends.javaextractor.CElsaParser.DeclSpecifierContext;
import depends.javaextractor.CElsaParser.DeclaratorContext;
import depends.javaextractor.CElsaParser.ParameterDeclarationContext;
import depends.javaextractor.CElsaParser.ParametersContext;
import depends.util.Tuple;
public class FunctionContextHelper extends ContextHelper{
	public String functionName = "<not_defined_funcname>";
	public String returnType  = "<not_define_rettype>";
	public List<Tuple<String, String> > parameters = new ArrayList<>(); 
	public String getFunctionName() {
		return functionName;
	}
	public String getReturnType() {
		return returnType;
	}
	
	public List<Tuple<String, String> > getParameters() {
		return parameters;
	}

	public List<Tuple<String, String>> getParameters(DeclaratorContext declarator) {
		if (declarator.parameters()==null) return new ArrayList<>();
		ParametersContext params = declarator.parameters();
		while(params.parameters()!=null) {
			params = params.parameters();
		}
		if (params.parameterDeclarationClause()==null) return new ArrayList<>();
		
		for ( ParameterDeclarationContext param:params.parameterDeclarationClause().parameterDeclaration()) {
			Tuple<String, String> p = new Tuple<String, String>("", "");
			if (param.declSpecifier()!=null) {
				p.x = getTypeNameOf(param.declSpecifier());
			}
			if (param.declarator()!=null) {
				p.y = getName(param.declarator());
			}
			parameters.add(p);
		}
		return parameters;
	}
}
