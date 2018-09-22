package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.javaextractor.Java9Parser.FormalParameterContext;
import depends.javaextractor.Java9Parser.FormalParameterListContext;

public class FormalParameterListContextHelper {
	
	FormalParameterListContext context;
	List<String> parameterTypes;
	
	public FormalParameterListContextHelper(FormalParameterListContext formalParameterListContext) {
		this.context = formalParameterListContext;
		parameterTypes = new ArrayList<>();
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
	public Collection<String> extractParameterTypeList() {
		if (context != null) {
			System.out.println(context.getText());
			if (context.formalParameters() != null) {
				System.out.println(context.formalParameters().getText());
				for (FormalParameterContext p : context.formalParameters().formalParameter()) {
					// Primitive type will be ignored
					addParameterType( new UnannTypeContextHelper().calculateType(p.unannType()));
				}
			}
			if (context.lastFormalParameter() != null) {
				if (context.lastFormalParameter().formalParameter() != null) {
					// Primitive type will be ignored
					addParameterType( new UnannTypeContextHelper().calculateType(
							context.lastFormalParameter().formalParameter().unannType()));
				}
				if (context.lastFormalParameter().unannType() != null) {
					addParameterType( new UnannTypeContextHelper().calculateType( context.lastFormalParameter().unannType()));
				}
			}
			if (context.receiverParameter() != null) {
				addParameterType( new UnannTypeContextHelper().calculateType(context.receiverParameter().unannType()));
			}
		}
		return parameterTypes;
	}
	private void addParameterType(String type) {
		if (type!=null)
			this.parameterTypes.add(type);
	}
}
