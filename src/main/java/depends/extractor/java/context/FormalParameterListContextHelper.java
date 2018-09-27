package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import depends.javaextractor.JavaParser.FormalParameterContext;
import depends.javaextractor.JavaParser.FormalParameterListContext;
import depends.javaextractor.JavaParser.FormalParametersContext;
import depends.javaextractor.JavaParser.LastFormalParameterContext;
import depends.javaextractor.JavaParser.TypeTypeContext;
import depends.javaextractor.JavaParser.VariableModifierContext;
import depends.util.Tuple;

public class FormalParameterListContextHelper {

	private FormalParametersContext formalParameters;
	FormalParameterListContext context;

	List<String> parameterTypes;
	List<Tuple<String, String>> varList;
	private List<String> annotations;

	public FormalParameterListContextHelper(FormalParametersContext formalParameters) {
		this.formalParameters = formalParameters;
		this.context = this.formalParameters.formalParameterList();
		parameterTypes = new ArrayList<>();
		varList = new ArrayList<>();
		annotations = new ArrayList<>();
		if (context!=null)
			extractParameterTypeList();
	}

	public Collection<String> getParameterTypeList(){
		return parameterTypes;
	}
	public Collection<Tuple<String, String>> getVarList(){
		return varList;
	}

	
	public FormalParameterListContextHelper(FormalParameterListContext formalParameterListContext) {
		this.context = formalParameterListContext;
		parameterTypes = new ArrayList<>();
		varList = new ArrayList<>();
		annotations = new ArrayList<>();
		if (context!=null)
			extractParameterTypeList();
	}


	public void extractParameterTypeList() {
		if (context != null) {
			if (context.formalParameter() != null) {
				for (FormalParameterContext p : context.formalParameter()) {
					foundParameterDefintion(p.typeType(),p.variableDeclaratorId().IDENTIFIER(),p.variableModifier());
				}
				if (context.lastFormalParameter()!=null) {
					LastFormalParameterContext p = context.lastFormalParameter();
					foundParameterDefintion(p.typeType(),p.variableDeclaratorId().IDENTIFIER(),p.variableModifier());
				}
			}
		}
		return;
	}

	private void foundParameterDefintion(TypeTypeContext typeType, TerminalNode identifier, List<VariableModifierContext> variableModifier) {
		String type = ClassTypeContextHelper.getClassName(typeType);
		if (type!=null)
			this.parameterTypes.add(type);
		String var = identifier.getText();
		varList.add(new Tuple<String, String>(type,var));	

		for ( VariableModifierContext modifier:variableModifier) {
			if (modifier.annotation()!=null) {
				this.annotations.add(QualitiedNameContextHelper.getName(modifier.annotation().qualifiedName()));
			}
		}

	}

	public List<String> getAnnotations() {
		return annotations;
	}

}
