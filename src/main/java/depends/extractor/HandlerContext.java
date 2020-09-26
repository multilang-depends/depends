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

package depends.extractor;

import depends.entity.*;
import depends.entity.repo.EntityRepo;
import depends.entity.repo.IdGenerator;
import depends.importtypes.Import;
import depends.relations.Inferer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class HandlerContext {
	protected EntityRepo entityRepo;
	protected IdGenerator idGenerator;

	protected FileEntity currentFileEntity;
	protected Inferer inferer;

	
	public HandlerContext(EntityRepo entityRepo, Inferer inferer) {
		this.entityRepo = entityRepo;
		this.idGenerator = entityRepo;
		entityStack = new Stack<Entity>();
		this.inferer = inferer;
	}

	public FileEntity startFile(String fileName) {
		currentFileEntity = new FileEntity(fileName, idGenerator.generateId(),true);
		pushToStack(currentFileEntity);
		addToRepo(currentFileEntity);
		return currentFileEntity;
	}

	public void done() {
		entityRepo.addFile(this.currentFile());
	}

	public TypeEntity foundNewType(GenericName name, Integer startLine) {
		TypeEntity currentTypeEntity = new TypeEntity(name, this.latestValidContainer(),
				idGenerator.generateId());
		currentTypeEntity.setLine(startLine);
		pushToStack(currentTypeEntity);
		addToRepo(currentTypeEntity);
		currentFileEntity.addType(currentTypeEntity);
		return currentTypeEntity;
	}
	
	/**
	 * Tell the context object that a new type founded.
	 * @param name
	 * @param startLine
	 * @return
	 */
	public TypeEntity foundNewType(String name, Integer startLine) {
		return foundNewType(GenericName.build(name),startLine);
	}

	public AliasEntity foundNewAlias(String aliasName, String originalName) {
		if (aliasName.equals(originalName)) return null; //it is a tricky, we treat same name no different. 
		//indeed it is not perfect -> the right match should depends on no-bare format like "struct a" instead of "a"
		AliasEntity currentTypeEntity = new AliasEntity(GenericName.build(aliasName), this.latestValidContainer(),
				idGenerator.generateId(),GenericName.build(originalName) );
	 	addToRepo(currentTypeEntity);
		return currentTypeEntity;		
	}
	
	public AliasEntity foundNewAlias(GenericName aliasName, Entity referToEntity) {
		AliasEntity currentTypeEntity = new AliasEntity(aliasName, this.latestValidContainer(),
				idGenerator.generateId(),aliasName);
		currentTypeEntity.setReferToEntity(referToEntity);
	 	addToRepo(currentTypeEntity);
		return currentTypeEntity;		
	}
	
	/**
	 * Tell the context that a new method was found.
	 * Do not forget to tell the context leave the method when you finish
	 * the process of the method
	 * @param methodName
	 * @param returnType  - if no return type information avaliable, keep it as null;
	 * @param throwedType - if no throwed type information avaliable, keep it as empty list;  
	 * @return the new function enity
	 */
	public FunctionEntity foundMethodDeclarator(String methodName, String returnType, List<String> throwedType, Integer startLine) {
		FunctionEntity functionEntity = new FunctionEntity(GenericName.build(methodName), this.latestValidContainer(),
				idGenerator.generateId(),GenericName.build(returnType));
		functionEntity.setLine(startLine);
		addToRepo(functionEntity);
		this.typeOrFileContainer().addFunction(functionEntity);
		pushToStack(functionEntity);
		functionEntity.addThrowTypes(throwedType.stream().map(item->GenericName.build(item)).collect(Collectors.toList()));
		return functionEntity;
	}
	
	public FunctionEntity foundMethodDeclarator(String methodName, Integer startLine) {
		FunctionEntity functionEntity = new FunctionEntity(GenericName.build(methodName), this.latestValidContainer(),
				idGenerator.generateId(),null);
		functionEntity.setLine(startLine);
		addToRepo(functionEntity);
		this.typeOrFileContainer().addFunction(functionEntity);
		pushToStack(functionEntity);
		return functionEntity;		
	}

	
	public FunctionEntity foundMethodDeclarator(ContainerEntity containerEntity, String methodName, Integer startLine) {
		FunctionEntity functionEntity = new FunctionEntity(GenericName.build(methodName), containerEntity,
				idGenerator.generateId(),null);
		functionEntity.setLine(startLine);
		addToRepo(functionEntity);
		containerEntity.addFunction(functionEntity);
		pushToStack(functionEntity);
		functionEntity.addThrowTypes(new ArrayList<>());
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
	
	public ContainerEntity globalScope() {
		Entity global = entityRepo.getEntity(EntityRepo.GLOBAL_SCOPE_NAME);
		if (global==null) {
			global = new PackageEntity(EntityRepo.GLOBAL_SCOPE_NAME,idGenerator.generateId());
			addToRepo(global);
		}
		return (ContainerEntity)global;
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

	public void foundImplements(GenericName typeName) {
		currentType().addImplements(typeName);
	}

	public void foundExtends(String className) {
		foundExtends(GenericName.build(className));
	}
	
	public void foundExtends(GenericName typeName) {
		if (currentType()==null) {
			System.out.println("error: type do not exist");
			return ;
		}
		currentType().addExtends(typeName);
	}

	public void foundMixin(String name) {
		foundMixin(GenericName.build(name));
		
	}
	
	public void foundMixin(GenericName name) {
		lastContainer().addMixin(name);
		
	}

	public void foundTypeParametes(GenericName typeName) {
		lastContainer().addTypeParameter(typeName);
	}


	public List<VarEntity> foundVarDefinitions(List<String> varNames, String type, List<GenericName> typeArguments, Integer line) {
		return varNames.stream().map(item->foundVarDefinition(item,GenericName.build(type),typeArguments,line)).collect(Collectors.toList());
	}
	
	public VarEntity foundVarDefinition(ContainerEntity container,String varName,Integer line) {
		if (container==null) {
			System.out.println("fallback to file container for var " + varName + " in file "+ currentFile().getRawName());
			container = currentFile();
		}
		
		VarEntity var = getVarInLocalFile(container,GenericName.build(varName));
		if (var!=null) return var;
		var = new VarEntity(GenericName.build(varName), null, container, idGenerator.generateId());
		var.setLine(line);
		container.addVar(var);
		addToRepo(var);

		return var;
	}
	
	public VarEntity foundGlobalVarDefinition(ContainerEntity container,String varName,Integer line) {
		if (container==null) {
			System.out.println("fallback to file container for var " + varName + " in file "+ currentFile().getRawName());
			container = currentFile();
		}
		
		VarEntity var = getVarInLocalFile(container,GenericName.build(varName));
		if (var!=null) return var;
		var = new VarEntity(GenericName.build(varName), null, container, idGenerator.generateId());
		container.addVar(var);
		var.setLine(line);
		var.setQualifiedName(var.getRawName().toString());
		addToRepo(var);
		return var;
	}

	public VarEntity foundVarDefinition(String varName, GenericName type, List<GenericName> typeArguments,Integer line) {
		VarEntity var = new VarEntity(GenericName.build(varName), type, lastContainer(), idGenerator.generateId());
		var.setLine(line);
		var.addTypeParameter(typeArguments);
		lastContainer().addVar(var);	
		addToRepo(var);
		return var;
	}
	
	public VarEntity addMethodParameter(String paramName) {
		if (currentFunction()==null) return null;
		VarEntity varEntity = new VarEntity(GenericName.build(paramName),null,currentFunction(),idGenerator.generateId());
		currentFunction().addParameter(varEntity);
		return varEntity;
	}

	public VarEntity foundEnumConstDefinition(String varName,Integer line) {
		GenericName type = lastContainer().getRawName();
		return foundVarDefinition(varName,type,new ArrayList<>(),line);
	}
	
	protected Stack<Entity> entityStack = new Stack<Entity>();

	protected void pushToStack(Entity entity) {
		entityStack.push(entity);
	}
	
	
	public void exitLastedEntity() {
		//we never pop up the lastest one (FileEntity)
		if (entityStack.size()>1)
			entityStack.pop();
	}
	
	private VarEntity getVarInLocalFile(ContainerEntity container, GenericName varName) {
		Entity entity = inferer.resolveName(container, varName, false); 
		if (entity ==null ) return null;
		if (!entity.getAncestorOfType(FileEntity.class).equals(currentFileEntity)) return null;
		if (entity instanceof VarEntity) return (VarEntity)entity;
		return null;
	}

	public Entity foundEntityWithName(GenericName rawName) {
		return inferer.resolveName(lastContainer(), rawName, true);
	}
	
	public void addToRepo(Entity entity) {
		entityRepo.add(entity);
	}


}