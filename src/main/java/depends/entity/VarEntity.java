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

package depends.entity;

import java.util.ArrayList;
import java.util.List;

import depends.relations.Inferer;

public class VarEntity extends ContainerEntity {
	private String rawType;
	private TypeEntity type;
	private List<FunctionCall> functionCalls;
	public VarEntity() {
		
	}
	public VarEntity(String simpleName,  String rawType, Entity parent, int id) {
		super(simpleName,  parent,id);
		this.rawType = rawType;
		functionCalls = new ArrayList<>();
	}

	public void setRawType(String rawType) {
		this.rawType =rawType;
	}
	
	public String getRawType() {
		return rawType;
	}

	@Override
	public TypeEntity getType() {
		return type;
	}

	public void setType(TypeEntity type) {
		this.type = type;
	}

	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		Entity entity = inferer.resolveName(this, rawType, true);
		if (entity==null) return;
		this.setActualReferTo(entity);
		type = entity.getType();
		if (type==null) {
			if (((ContainerEntity)getParent()).isGenericTypeParameter(rawType)) {
				type = Inferer.genericParameterType;
			}
		}
		if (type==null) {
			fillCandidateTypes(inferer);
		}
	}

	public List<FunctionCall> getCalledFunctions() {
		return functionCalls;
	}

	public void addFunctionCall(String fname) {
		this.functionCalls.add(new FunctionCall(fname));
	}

	public void fillCandidateTypes(Inferer inferer) {
		if (type!=null) return ; //it is a strong type lang, do not need deduce candidate types
		if (functionCalls.size()==0) return; //no information avaliable for type deduction
		if (this.rawType==null) {
			this.type = new CandidateTypes(inferer.calculateCandidateTypes(this,this.functionCalls));
		}
	}
}
