package depends.extractor;

import java.util.Stack;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.PackageEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;

public class HandlerContext{
	private EntityRepo entityRepo;
	private FileEntity currentFileEntity;
	private String currentPackageName = "";
	Stack<Entity> entityStack = new Stack<Entity>();
	
	public HandlerContext(EntityRepo entityRepo) {
		this.entityRepo = entityRepo;
	}
	public FileEntity newFileEntity(String fileName) {
		currentFileEntity = new FileEntity(fileName,entityRepo.getCurrentIndex());
		return currentFileEntity;
	}

	public Entity newPackageEntity(String packageName) {
		this.currentPackageName = packageName;
		Entity pkgEntity = entityRepo.getEntity(packageName);
		if (pkgEntity ==null) {
			pkgEntity = new PackageEntity(packageName,-1,
				entityRepo.getCurrentIndex());
		}
		currentFileEntity.setParentId(pkgEntity.getId());
		pkgEntity.addChildId(currentFileEntity.getId());
		return pkgEntity;
	}
	
	private String resolveTypeNameDefinition(String name) {
		if (name.isEmpty()) return "";
		String prefix = "";
		for (int i=entityStack.size()-1;i>=0;i--) {
			Entity t = entityStack.get(i);
			if(! t.getFullName().isEmpty() &&
					!(t.getFullName().startsWith("<Anony>"))) {
				prefix = t.getFullName();
				break;
			}
		}
		if (prefix.isEmpty()) {
			if (currentPackageName.length()>0)
				return currentPackageName + "." + name;
			return name;
		}else {
			return  prefix + "." + name;
		}
	}
	
	public Entity newClassInterface(String classOrInterfaceName) {
		Entity currentTypeEntity = new TypeEntity(resolveTypeNameDefinition(classOrInterfaceName),
				currentFileEntity.getId(),
				entityRepo.getCurrentIndex());
        entityRepo.add(currentTypeEntity);
        entityStack.push(currentTypeEntity);
		return currentTypeEntity;
	}
	
	public void popEntity() {
		entityStack.pop();
	}
	
	public void newImport(String importedTypeOrPackage) {
		currentFileEntity.addImport(importedTypeOrPackage);
	}
	
	//TODO: should be refined
	public FileEntity currentFile() {
		return currentFileEntity;
	}
	
	public String resolveTypeNameRef(String typeName) {
		//if it is a full name like "java.io.Exception"
		if (typeName.indexOf('.')>0) return typeName;
		
		//if it is a singleName like "JavaHandler"
		// TODO: we still cannot handle on demand import like 
		// import package.name.*;
		if (currentFile().getImport(typeName)!=null)
			typeName = currentFile().getImport(typeName);
		else
			typeName = (currentPackageName.isEmpty()? "":".") + typeName;
		return typeName;
	}
	public Entity newFunctionEntity(String methodName) {
		Entity currentFunctionEntity = new FunctionEntity(resolveTypeNameDefinition(methodName),
				currentType().getId(),
				entityRepo.getCurrentIndex());
        entityStack.push(currentFunctionEntity);
		return currentFunctionEntity;
	}
	public Entity currentType() {
		for (int i=entityStack.size()-1;i>=0;i--) {
			Entity t = entityStack.get(i);
			if (t instanceof TypeEntity)
				return t;
		}
		return null;
	}
	public Entity currentFunction() {
		for (int i=entityStack.size()-1;i>=0;i--) {
			Entity t = entityStack.get(i);
			if (t instanceof FunctionEntity)
				return t;
		}
		return null;
	}
	public void addVar(String type, String varName) {
		VarEntity varEntity = new VarEntity(varName, type, lastContainer().getId(), entityRepo.getCurrentIndex());
		lastContainer().addVar(varEntity);
	}
	public Entity lastContainer() {
		return entityStack.peek();
	}
}