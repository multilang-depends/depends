package depends.entity;

import java.util.ArrayList;
import java.util.Collection;

import depends.relations.Inferer;

public class AliasEntity extends Entity {
	private Entity referToEntity = new EmptyTypeEntity();
	private String originName;

	public AliasEntity(String simpleName, Entity parent, Integer id, String originTypeName) {
		super(simpleName, parent, id);
		this.originName = originTypeName;
	}

	public void inferLocalLevelEntities(Inferer inferer) {
		Entity entity = inferer.resolveName(this, originName, true);
		if (entity != null)
			referToEntity = entity;
		if (entity.equals(this)) {
			referToEntity = new EmptyTypeEntity();
		}
		referToEntity.inferLocalLevelEntities(inferer);
	}

	public Collection<TypeEntity> getResolvedTypeParameters() {
		if (!(referToEntity instanceof DecoratedEntity))
			return new ArrayList<>();
		DecoratedEntity origin = (DecoratedEntity) referToEntity;
		return origin.getResolvedTypeParameters();
	}

	public Collection<TypeEntity> getResolvedAnnotations() {
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

	protected FunctionEntity lookupFunctionLocally(String functionName) {
		if (!(referToEntity instanceof ContainerEntity))
			return null;
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.lookupFunctionLocally(functionName);
	}

	public FunctionEntity lookupFunctionInVisibleScope(String functionName) {
		if (!(referToEntity instanceof ContainerEntity))
			return null;
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.lookupFunctionInVisibleScope(functionName);
	}

	public VarEntity lookupVarsInVisibleScope(String varName) {
		if (!(referToEntity instanceof ContainerEntity))
			return null;
		ContainerEntity origin = (ContainerEntity) referToEntity;
		return origin.lookupVarsInVisibleScope(varName);
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

	public Collection<TypeEntity> getReturnTypes() {
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

	public Collection<TypeEntity> getThrowTypes() {
		if (!(referToEntity instanceof FunctionEntity))
			return new ArrayList<>();
		FunctionEntity origin = (FunctionEntity) referToEntity;
		return origin.getThrowTypes();
	}

	public Entity getOriginType() {
		return referToEntity;
	}
	

}
