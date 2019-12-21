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
	private Collection<GenericName> annotations;
	private Collection<Entity> resolvedAnnotations;
	private Collection<Entity> resolvedTypeParameters;
	public DecoratedEntity() {
	}

	public DecoratedEntity(GenericName rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
	}
	
	public void addAnnotation(GenericName name) {
		if(this.annotations==null)
			annotations  = new ArrayList<>();
		this.annotations.add(name);
	}
	
	public void addTypeParameter(List<GenericName> parameters) {
		this.getRawName().appendArguments(parameters);
	}
	

	
	public void addTypeParameter(GenericName parameter) {
		this.getRawName().appendArguments(parameter);
	}
	
	protected void appendTypeParameters(Collection<Entity> typeParameterEntities) {
		if (resolvedTypeParameters==null)
			resolvedTypeParameters = new ArrayList<>();
		resolvedTypeParameters.addAll(typeParameterEntities);
	}
	
	/**
	 * For all data in the class, infer their types.
	 * Should be override in sub-classes 
	 */
	public void inferLocalLevelEntities(Inferer inferer) {
		Collection<Entity> typeParameterEntities = typeParametersToEntities(inferer);
		appendTypeParameters(typeParameterEntities);
		resolvedAnnotations = identiferToEntities(inferer, annotations);
	}


	

	private Collection<Entity> typeParametersToEntities(Inferer inferer) {
		ArrayList<Entity> r = new ArrayList<>();
		for (GenericName typeParameter:this.getRawName().getArguments()) {
			toEntityList(inferer, r,typeParameter);
		}
		return r;
	}

	protected void toEntityList(Inferer inferer, ArrayList<Entity> r, GenericName typeParameter) {
		Entity entity = resolveEntity(inferer, typeParameter);
		if (entity != null)
			r.add(entity);
		for (GenericName arg: typeParameter.getArguments()) {
			toEntityList(inferer,r,arg);
		}
	}

	public Collection<Entity> getResolvedTypeParameters() {
		if (resolvedTypeParameters==null)
			return new ArrayList<>();
		return resolvedTypeParameters;
	}


	public Collection<Entity> getResolvedAnnotations() {
		if (resolvedAnnotations==null)
			return new ArrayList<>();
		return resolvedAnnotations;
	}

	public boolean isGenericTypeParameter(GenericName rawType) {
		boolean foundInCurrentLevel =  rawType.find(rawType);
		if (foundInCurrentLevel) return true;
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
	protected Collection<Entity> identiferToEntities(Inferer inferer, Collection<GenericName> identifiers) {
		if (identifiers==null) return null;
		if (identifiers.size()==0) return null;
		ArrayList<Entity> r = new ArrayList<>();
		for (GenericName name : identifiers) {
			Entity entity = resolveEntity(inferer, name);
			if (entity != null)
				r.add(entity);
		}
		return r;
	}

	private Entity resolveEntity(Inferer inferer, GenericName name) {
		Entity entity = inferer.resolveName(this, name, true);
		if (entity==null) {
			if (((ContainerEntity)getParent()).isGenericTypeParameter(name)) {
				entity = Inferer.genericParameterType;
			}
		}
		return entity;
	}
}
