package depends.extractor;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.PackageEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;

public class HandlerContext {
	private EntityRepo entityRepo;
	private IdGenerator idGenerator;

	private FileEntity currentFileEntity;
	Stack<Entity> entityStack = new Stack<Entity>();

	public HandlerContext(EntityRepo entityRepo) {
		this.entityRepo = entityRepo;
		this.idGenerator = entityRepo;
		entityStack = new Stack<Entity>();
	}

	public FileEntity startFile(String fileName) {
		currentFileEntity = new FileEntity(fileName, idGenerator.generateId());
		entityStack.push(currentFileEntity);
		entityRepo.add(currentFileEntity);
		return currentFileEntity;
	}

	public Entity foundNewPackage(String packageName) {
		Entity pkgEntity = entityRepo.getEntity(packageName);
		if (pkgEntity == null) {
			pkgEntity = new PackageEntity(packageName, idGenerator.generateId());
			entityRepo.add(pkgEntity);
		}
		entityRepo.setParent(currentFileEntity,pkgEntity);
		return pkgEntity;
	}

	public Entity foundNewType(String classOrInterfaceName) {
		Entity currentTypeEntity = new TypeEntity(classOrInterfaceName, this.latestValidContainer(),
				idGenerator.generateId());
		entityRepo.add(currentTypeEntity);
		entityStack.push(currentTypeEntity);
		return currentTypeEntity;
	}

	public void foundMethodDeclarator(String methodName, Collection<VarEntity> parameters, String returnType, List<String> throwedType) {
		FunctionEntity functionEntity = new FunctionEntity(methodName, this.latestValidContainer(),
				idGenerator.generateId(),returnType,parameters);
		entityRepo.add(functionEntity);
		this.typeOrFileContainer().addFunction(functionEntity);
		entityStack.push(functionEntity);
		functionEntity.addThrowTypes(throwedType);
	}
	public void exitLastedEntity() {
		entityStack.pop();
	}

	public void foundNewImport(String importedTypeOrPackage) {
		currentFileEntity.addImport(importedTypeOrPackage);
	}

	public TypeEntity currentType() {
		for (int i = entityStack.size() - 1; i >= 0; i--) {
			Entity t = entityStack.get(i);
			if (t instanceof TypeEntity)
				return (TypeEntity) t;
		}
		return null;
	}
	
	public ContainerEntity typeOrFileContainer() {
		for (int i = entityStack.size() - 1; i >= 0; i--) {
			Entity t = entityStack.get(i);
			if (t instanceof TypeEntity)
				return (ContainerEntity) t;
			if (t instanceof FileEntity) {
				return (ContainerEntity)t;
			}
		}
		return null;
	}


	public FunctionEntity currentFunction() {
		for (int i = entityStack.size() - 1; i >= 0; i--) {
			Entity t = entityStack.get(i);
			if (t instanceof FunctionEntity)
				return (FunctionEntity) t;
		}
		return null;
	}

	public FileEntity currentFile() {
		return currentFileEntity;
	}

	public Entity latestValidContainer() {
		for (int i = entityStack.size() - 1; i >= 0; i--) {
			Entity t = entityStack.get(i);
			if (t instanceof FunctionEntity)
				return t;
			if (t instanceof TypeEntity)
				return t;
			if (t instanceof FileEntity)
				return t;
		}
		return null;
	}

	public ContainerEntity lastContainer() {
		for (int i = entityStack.size() - 1; i >= 0; i--) {
			Entity t = entityStack.get(i);
			if (t instanceof ContainerEntity)
				return (ContainerEntity) t;
		}
		return null;
	}

	public void foundAnnotation(String name) {
		lastContainer().addAnnotation(name);
	}

	public void foundImplements(String typeName) {
		currentType().addImplements(typeName);
	}

	public void foundExtends(String typeName) {
		currentType().addExtends(typeName);
	}


	public void foundTypeParametes(String typeName) {
		lastContainer().addTypeParameter(typeName);
	}


	public void foundVarDefinition(List<String> varNames, String type) {
		for (String varName : varNames) {
			foundVarDefintion(varName,type);
		}
	}


	public void foundVarDefintion(String varName, String type) {
		VarEntity var = new VarEntity(varName, type, lastContainer(), idGenerator.generateId());
		lastContainer().addVar(var);		
	}

	public void foundEnumConstDefinition(String varName) {
		String type = lastContainer().getRawName();
		foundVarDefintion(varName,type);
	}
}