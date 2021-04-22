package depends.entity;

import depends.relations.IBindingResolver;

public class FunctionEntityImpl extends FunctionEntity {
	Entity implementedFunction = null;
	public FunctionEntityImpl() {
		super();
	}
    public FunctionEntityImpl(GenericName simpleName, Entity parent, Integer id, GenericName returnType) {
		super(simpleName,parent,id,returnType);
	}
	@Override
	public void inferLocalLevelEntities(IBindingResolver bindingResolver) {
		super.inferLocalLevelEntities(bindingResolver);
		implementedFunction = bindingResolver.lookupTypeInImported((FileEntity)(getAncestorOfType(FileEntity.class)),this.getQualifiedName());
	}
	public Entity getImplemented() {
		return implementedFunction;
	}

    
}
