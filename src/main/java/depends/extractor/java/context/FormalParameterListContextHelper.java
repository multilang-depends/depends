package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import depends.entity.ContainerEntity;
import depends.entity.IdGenerator;
import depends.entity.types.VarEntity;
import depends.javaextractor.JavaParser.FormalParameterContext;
import depends.javaextractor.JavaParser.FormalParameterListContext;
import depends.javaextractor.JavaParser.FormalParametersContext;
import depends.javaextractor.JavaParser.LastFormalParameterContext;
import depends.javaextractor.JavaParser.TypeTypeContext;
import depends.javaextractor.JavaParser.VariableModifierContext;

public class FormalParameterListContextHelper {

	FormalParameterListContext context;
	private IdGenerator idGenerator;
	List<VarEntity> parameters;
	private List<String> annotations;
	private ContainerEntity container;

	public FormalParameterListContextHelper(FormalParameterListContext formalParameterListContext,ContainerEntity container, IdGenerator idGenerator) {
		this.context = formalParameterListContext;
		parameters = new ArrayList<>();
		this.container = container;
		annotations = new ArrayList<>();
		this.idGenerator = idGenerator;
		if (context!=null)
			extractParameterTypeList();
	}

	public FormalParameterListContextHelper(FormalParametersContext formalParameters,ContainerEntity container, IdGenerator idGenerator) {
		this(formalParameters.formalParameterList(),container,idGenerator);
	}


	public Collection<VarEntity> getParameterList(){
		return parameters;
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
		String var = identifier.getText();
		VarEntity entity = new VarEntity(var,type,container,idGenerator.generateId());
		parameters.add(entity);	

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
