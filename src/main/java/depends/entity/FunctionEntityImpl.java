package depends.entity;

import depends.relations.Inferer;

public class FunctionEntityImpl extends FunctionEntity {
	Entity implementedFunction = null;
	public FunctionEntityImpl() {
		super();
	}
    public FunctionEntityImpl(GenericName simpleName, Entity parent, Integer id, GenericName returnType) {
		super(simpleName,parent,id,returnType);
	}
	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		implementedFunction = inferer.lookupTypeInImported((FileEntity)(getAncestorOfType(FileEntity.class)),this.getQualifiedName());
	}
	public Entity getImplemented() {
		return implementedFunction;
	}

    
}
