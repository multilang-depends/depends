/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.extractor.java.context;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.VarEntity;
import depends.entity.repo.IdGenerator;
import depends.extractor.java.JavaParser.FormalParameterContext;
import depends.extractor.java.JavaParser.FormalParameterListContext;
import depends.extractor.java.JavaParser.FormalParametersContext;
import depends.extractor.java.JavaParser.LastFormalParameterContext;
import depends.extractor.java.JavaParser.TypeTypeContext;
import depends.extractor.java.JavaParser.VariableModifierContext;

public class FormalParameterListContextHelper {

	FormalParameterListContext context;
	private IdGenerator idGenerator;
	private List<String> annotations;
	private FunctionEntity container;

	public FormalParameterListContextHelper(FormalParameterListContext formalParameterListContext,FunctionEntity container, IdGenerator idGenerator) {
		this.context = formalParameterListContext;
		this.container = container;
		annotations = new ArrayList<>();
		this.idGenerator = idGenerator;
		if (context!=null)
			extractParameterTypeList();
	}

	public FormalParameterListContextHelper(FormalParametersContext formalParameters,FunctionEntity container, IdGenerator idGenerator) {
		this(formalParameters.formalParameterList(),container,idGenerator);
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
		GenericName type = GenericName.build(ClassTypeContextHelper.getClassName(typeType));
		GenericName varName = GenericName.build(identifier.getText());
		VarEntity varEntity = new VarEntity(varName,type,container,idGenerator.generateId());
		container.addParameter(varEntity);
		
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
