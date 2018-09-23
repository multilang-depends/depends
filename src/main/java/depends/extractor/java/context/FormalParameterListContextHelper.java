package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.javaextractor.Java9Parser.FormalParameterContext;
import depends.javaextractor.Java9Parser.FormalParameterListContext;
import depends.javaextractor.Java9Parser.IdentifierContext;
import depends.javaextractor.Java9Parser.UnannTypeContext;
import depends.javaextractor.Java9Parser.VariableDeclaratorIdContext;
import depends.util.Tuple;

public class FormalParameterListContextHelper {
	
	FormalParameterListContext context;
	List<String> parameterTypes;
	List<Tuple<String, String>> varList;
	
	public FormalParameterListContextHelper(FormalParameterListContext formalParameterListContext) {
		this.context = formalParameterListContext;
		parameterTypes = new ArrayList<>();
		varList = new ArrayList<>();
		if (formalParameterListContext!=null)
			extractParameterTypeList();
	}
	/**
	 * 
	 * formalParameterList
	 * :	formalParameters ',' lastFormalParameter
	 * |	lastFormalParameter
	 * |	receiverParameter
	 * @param context
	 * @return
	 */
	public void extractParameterTypeList() {
		if (context != null) {
			if (context.formalParameters() != null) {
				for (FormalParameterContext p : context.formalParameters().formalParameter()) {
					foundParameterDefintion(p.unannType(),p.variableDeclaratorId());
				}
			}
			/**
			 * lastFormalParameter:
			 *   	variableModifier* unannType annotation* '...' variableDeclaratorId
		     *     |formalParameter
			 */
			if (context.lastFormalParameter() != null) {
				if (context.lastFormalParameter().formalParameter() != null) {
					foundParameterDefintion(context.lastFormalParameter().formalParameter().unannType(),
							context.lastFormalParameter().formalParameter().variableDeclaratorId());
				}
				
				if (context.lastFormalParameter().unannType() != null) {
					foundParameterDefintion(context.lastFormalParameter().unannType(),
							context.lastFormalParameter().variableDeclaratorId());
				}
			}
			/**
			 * receiverParameter :	annotation* unannType (identifier '.')? 'this'
			 */
			if (context.receiverParameter() != null) {
				UnannTypeContext unannType =  context.receiverParameter().unannType();
				String type =  new UnannTypeContextHelper().calculateType(unannType);
				if (type!=null)
					this.parameterTypes.add(type);
				IdentifierContext var = context.receiverParameter().identifier();
				if (var!=null)
					varList.add(new Tuple<String, String>(type,var.Identifier().getText()));		
			}
		}
		return;
	}
	public Collection<String> getParameterTypeList(){
		return parameterTypes;
	}
	public Collection<Tuple<String, String>> getVarList(){
		return varList;
	}
	
	private void foundParameterDefintion(UnannTypeContext unannType, VariableDeclaratorIdContext variableDeclaratorId) {
		String type =  new UnannTypeContextHelper().calculateType(unannType);
		if (type!=null)
			this.parameterTypes.add(type);
		String var = variableDeclaratorId.identifier().getText();
		varList.add(new Tuple<String, String>(type,var));		
	}
}
