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

public class AliasEntity extends Entity {
	private Entity referToEntity = new EmptyTypeEntity();
	private GenericName originName;
	private List<Entity> referPath = new ArrayList<>();
	public AliasEntity() {
		
	}
	public AliasEntity(GenericName simpleName, Entity parent, Integer id, GenericName originTypeName) {
		super(simpleName, parent, id);
		this.originName = originTypeName;
	}

	public void inferLocalLevelEntities(Inferer inferer) {
		if (!(referToEntity instanceof EmptyTypeEntity)) return;
		Entity entity = inferer.resolveName(this, originName, true);
		while(entity instanceof AliasEntity) {
			AliasEntity aliasEntity = (AliasEntity)entity;
			if (this.referPath.contains(aliasEntity)) {
				entity = null;
				break;
			}
			this.referPath.add(aliasEntity);
			entity = inferer.resolveName(aliasEntity, aliasEntity.originName,true);
			if (entity==null) break;
			if (entity.equals(this)) {
				entity = null;
				break;
			}
		}
		if (entity != null)
			referToEntity = entity;
	}

	public Collection<Entity> getResolvedTypeParameters() {
		if (!(referToEntity instanceof DecoratedEntity))
			return new ArrayList<>();
		DecoratedEntity origin = (DecoratedEntity) referToEntity;
		return origin.getResolvedTypeParameters();
	}

	public Collection<Entity> getResolvedAnnotations() {
		if (!(referToEntity instanceof DecoratedEntity))
			return new ArrayList<>();
		DecoratedEntity origin = (DecoratedEntity) referToEntity;
		return origin.getResolvedAnnotations();
	}

	public ArrayList<VarEntity> getVars() {
		if (!(referToEntity instanceof ContainerEntity))
			return new ArrayList<>();
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.getVars();
	}

	public ArrayList<FunctionEntity> getFunctions() {
		if (!(referToEntity instanceof ContainerEntity))
			return new ArrayList<>();
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.getFunctions();
	}

	protected FunctionEntity lookupFunctionLocally(GenericName functionName) {
		if (!(referToEntity instanceof ContainerEntity))
			return null;
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.lookupFunctionLocally(functionName);
	}

	public List<Entity> lookupFunctionInVisibleScope(GenericName functionName) {
		if (!(referToEntity instanceof ContainerEntity))
			return null;
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.lookupFunctionInVisibleScope(functionName);
	}

	public Entity lookupVarsInVisibleScope(GenericName varName) {
		if (!(referToEntity instanceof ContainerEntity))
			return null;
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.lookupVarInVisibleScope(varName);
	}

	public Collection<ContainerEntity> getResolvedMixins() {
		if (!(referToEntity instanceof ContainerEntity))
			return new ArrayList<>();
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.getResolvedMixins();
	}

	public Collection<TypeEntity> getInheritedTypes() {
		if (referToEntity instanceof TypeEntity)
			return ((TypeEntity) referToEntity).getInheritedTypes();
		return new ArrayList<>();
	}

	public Collection<TypeEntity> getImplementedTypes() {
		if (referToEntity instanceof TypeEntity)
			return ((TypeEntity) referToEntity).getImplementedTypes();
		return new ArrayList<>();
	}

	public TypeEntity getInheritedType() {
		if (referToEntity instanceof TypeEntity)
			return ((TypeEntity) referToEntity).getInheritedType();
		return null;
	}

	public Collection<Entity> getReturnTypes() {
		if (!(referToEntity instanceof FunctionEntity))
			return new ArrayList<>();
		FunctionEntity origin = (FunctionEntity) referToEntity;
		return origin.getReturnTypes();
	}

	public TypeEntity getType() {
		return referToEntity.getType();
	}

	public Collection<VarEntity> getParameters() {
		if (!(referToEntity instanceof FunctionEntity))
			return new ArrayList<>();
		FunctionEntity origin = (FunctionEntity) referToEntity;
		return origin.getParameters();
	}

	public Collection<Entity> getThrowTypes() {
		if (!(referToEntity instanceof FunctionEntity))
			return new ArrayList<>();
		FunctionEntity origin = (FunctionEntity) referToEntity;
		return origin.getThrowTypes();
	}

	public Entity getOriginType() {
		return referToEntity;
	}
	public Entity getReferToEntity() {
		return referToEntity;
	}
	public void setReferToEntity(Entity referToEntity) {
		this.referToEntity = referToEntity;
	}
	

}
