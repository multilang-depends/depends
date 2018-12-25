package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import depends.relations.Inferer;

public abstract class DecoratedEntity extends Entity{
	private Collection<String> typeParameters; // Generic type parameters like <T>, <String>, <? extends Object>
	private Collection<String> annotations = new ArrayList<>();
	private Collection<TypeEntity> resolvedTypeParameters = new ArrayList<>();
	private Collection<TypeEntity> resolvedAnnotations = new ArrayList<>();
	
	public DecoratedEntity(String rawName, Entity parent, Integer id) {
		super(rawName, parent, id);
		typeParameters = new ArrayList<>();
	}
	
	public void addTypeParameter(List<String> typeArguments) {
		typeParameters.addAll(typeArguments);
	}
	public void addAnnotation(String name) {
		this.annotations.add(name);
	}
	
	public void addTypeParameter(String typeName) {
		this.typeParameters.add(typeName);
	}
	
	/**
	 * For all data in the class, infer their types.
	 * Should be override in sub-classes 
	 */
	public void inferLocalLevelEntities(Inferer inferer) {
		resolvedTypeParameters = identiferToTypes(inferer, typeParameters);
		resolvedAnnotations = identiferToTypes(inferer, annotations);
	}
	
	public Collection<TypeEntity> getResolvedTypeParameters() {
		return resolvedTypeParameters;
	}


	public Collection<TypeEntity> getResolvedAnnotations() {
		return resolvedAnnotations;
	}

	public boolean isGenericTypeParameter(String rawType) {
		if (this.typeParameters.contains(rawType)) return true;
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
	protected Collection<TypeEntity> identiferToTypes(Inferer inferer, Collection<String> identifiers) {
		ArrayList<TypeEntity> r = new ArrayList<>();
		for (String typeParameter : identifiers) {
			TypeEntity typeEntity = inferer.inferTypeFromName(this, typeParameter);
			if (typeEntity==null) {
				if (((ContainerEntity)getParent()).isGenericTypeParameter(typeParameter)) {
					typeEntity = Inferer.genericParameterType;
				}
			}
			if (typeEntity != null)
				r.add(typeEntity);
		}
		return r;
	}
}
