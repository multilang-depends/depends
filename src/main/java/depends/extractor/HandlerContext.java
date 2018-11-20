package depends.extractor;

import java.util.List;
import java.util.Stack;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.IdGenerator;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeAliasEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.importtypes.Import;

public abstract class HandlerContext {
	protected EntityRepo entityRepo;
	protected IdGenerator idGenerator;

	protected FileEntity currentFileEntity;

	
	public HandlerContext(EntityRepo entityRepo) {
		this.entityRepo = entityRepo;
		this.idGenerator = entityRepo;
		entityStack = new Stack<Entity>();
	}

	public FileEntity startFile(String fileName) {
		currentFileEntity = new FileEntity(fileName, idGenerator.generateId(),true);
		pushToStack(currentFileEntity);
		entityRepo.add(currentFileEntity);
		return currentFileEntity;
	}

	

	public Entity foundNewType(String classOrInterfaceName) {
		TypeEntity currentTypeEntity = new TypeEntity(classOrInterfaceName, this.latestValidContainer(),
			idGenerator.generateId());
		pushToStack(currentTypeEntity);
	 	entityRepo.add(currentTypeEntity);
		return currentTypeEntity;
	}

	public void foundNewTypeAlias(String aliasName, String originalName) {
		if (aliasName.equals(originalName)) return; //it is a tricky, we treat same name no different. 
		//indeed it is not perfect -> the right match should depends on no-bare format like "struct a" instead of "a"
		TypeAliasEntity currentTypeEntity = new TypeAliasEntity(aliasName, this.latestValidContainer(),
				idGenerator.generateId(),originalName );
	 	entityRepo.add(currentTypeEntity);
		return ;		
	}
	
	public FunctionEntity foundMethodDeclarator(String methodName, String returnType, List<String> throwedType) {
		FunctionEntity functionEntity = new FunctionEntity(methodName, this.latestValidContainer(),
				idGenerator.generateId(),returnType);
		entityRepo.add(functionEntity);
		this.typeOrFileContainer().addFunction(functionEntity);
		pushToStack(functionEntity);
		functionEntity.addThrowTypes(throwedType);
		return functionEntity;
	}
	


	public void foundNewImport(Import imported) {
		currentFileEntity.addImport(imported);
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
		if (currentType()==null) {
			System.out.println("error: type do not exist");
		}
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
	
	protected Stack<Entity> entityStack = new Stack<Entity>();

	private void pushToStack(Entity entity) {
		entityStack.push(entity);
	}
	
	
	public void exitLastedEntity() {
		entityStack.pop();
	}
	
}