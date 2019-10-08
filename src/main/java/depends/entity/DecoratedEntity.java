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
import java.util.Collection;
import java.util.List;

import depends.relations.Inferer;

public abstract class DecoratedEntity extends Entity{
	private Collection<GenericTypeArgument> typeParameters  = new ArrayList<>();; // Generic type parameters like <T>, <String>, <? extends Object>
	private Collection<String> annotations = new ArrayList<>();
	private Collection<Entity> resolvedTypeParameters = new ArrayList<>();
	private Collection<Entity> resolvedAnnotations = new ArrayList<>();
	public DecoratedEntity() {
	}

	public DecoratedEntity(String rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
	}
	
	public void addTypeParameter(List<GenericTypeArgument> typeArguments) {
		typeParameters.addAll(typeArguments);
	}
	public void addAnnotation(String name) {
		this.annotations.add(name);
	}
	
	public void addTypeParameter(GenericTypeArgument typeName) {
		this.typeParameters.add(typeName);
	}
	
	/**
	 * For all data in the class, infer their types.
	 * Should be override in sub-classes 
	 */
	public void inferLocalLevelEntities(Inferer inferer) {
		resolvedTypeParameters = typeParametersToEntities(inferer);
		resolvedAnnotations = identiferToEntities(inferer, annotations);
	}
	
	

	private Collection<Entity> typeParametersToEntities(Inferer inferer) {
		ArrayList<Entity> r = new ArrayList<>();
		for (GenericTypeArgument typeParameter:typeParameters) {
			toEntityList(inferer, r,typeParameter);
		}
		return r;
	}

	private void toEntityList(Inferer inferer, ArrayList<Entity> r, GenericTypeArgument typeParameter) {
		Entity entity = resolveEntity(inferer, typeParameter.getName());
		if (entity != null)
			r.add(entity);
		for (GenericTypeArgument arg: typeParameter.getArguments()) {
			toEntityList(inferer,r,arg);
		}
	}

	public Collection<Entity> getResolvedTypeParameters() {
		return resolvedTypeParameters;
	}


	public Collection<Entity> getResolvedAnnotations() {
		return resolvedAnnotations;
	}

	public boolean isGenericTypeParameter(String rawType) {
		for (GenericTypeArgument typeParam:typeParameters) {
			if (typeParam.contains(rawType)) {
				return true;
			}
		}
		if (this.getParent()==null || !(this.getParent() instanceof ContainerEntity))
			return false;
		return ((ContainerEntity)getParent()).isGenericTypeParameter(rawType);
	}	
	/**
	 * A common utility function used to transfer the identifiers 
	 * to types.
	 * @param inferer - the inferer object 
	 * @param identifiers - the identifiers will be translated
	 * @return The translated Types
	 */
	protected Collection<Entity> identiferToEntities(Inferer inferer, Collection<String> identifiers) {
		ArrayList<Entity> r = new ArrayList<>();
		for (String name : identifiers) {
			Entity entity = resolveEntity(inferer, name);
			if (entity != null)
				r.add(entity);
		}
		return r;
	}

	private Entity resolveEntity(Inferer inferer, String name) {
		Entity entity = inferer.resolveName(this, name, true);
		if (entity==null) {
			if (((ContainerEntity)getParent()).isGenericTypeParameter(name)) {
				entity = Inferer.genericParameterType;
			}
		}
		return entity;
	}
}
