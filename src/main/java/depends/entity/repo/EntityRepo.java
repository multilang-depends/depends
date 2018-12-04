package depends.entity.repo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.IdGenerator;
import depends.entity.MultiDeclareEntities;
import depends.entity.Relation;
import depends.entity.RelationCounter;
import depends.entity.TypeInfer;
import depends.entity.types.FileEntity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.extractor.BuiltInTypeIdenfier;
import depends.extractor.ImportLookupStrategy;
import depends.extractor.java.JavaImportLookupStrategy;
import depends.importtypes.Import;
import depends.util.Tuple;

public class EntityRepo implements IdGenerator,TypeInfer{
	public HashMap<String, Entity> allEntieisByName = new HashMap<>();
	public HashMap<Integer, Entity> allEntitiesById = new HashMap<>();
	private int nextAvaliableIndex;
	private BuiltInTypeIdenfier buildInProcessor = new NullParser();
	private ImportLookupStrategy importLookupStrategy = new JavaImportLookupStrategy();
	public ImportLookupStrategy getImportLookupStrategy() {
		return importLookupStrategy;
	}

	public void setImportLookupStrategy(ImportLookupStrategy importLookupStrategy) {
		this.importLookupStrategy = importLookupStrategy;
	}

	public EntityRepo() {
		nextAvaliableIndex = 0;
	}
	
	public Entity getEntity(String entityName) {
		return allEntieisByName.get(entityName);
	}
	
	public Entity getEntity(Integer entityId) {
		return allEntitiesById.get(entityId);
	}
	
	public void add(Entity entity) {
		allEntitiesById.put(entity.getId(), entity);
		String name = entity.getRawName();
		if (entity.getQualifiedName()!=null && !(entity.getQualifiedName().isEmpty()) ) {
			name = entity.getQualifiedName();
		}
		if (allEntieisByName.containsKey(name)) {
			Entity existedEntity = allEntieisByName.get(name);
			if (existedEntity instanceof MultiDeclareEntities) {
				((MultiDeclareEntities)existedEntity).add(entity);
			}else {
				MultiDeclareEntities eMultiDeclare = new MultiDeclareEntities(existedEntity,this.generateId());
				eMultiDeclare.add(entity);
				allEntieisByName.put(name, eMultiDeclare);
			}
		}else {
			allEntieisByName.put(name, entity);
		}
		if (entity.getParent()!=null)
			this.setParent(entity, entity.getParent());
	}
		
	public Collection<Entity> getEntities() {
		return allEntitiesById.values();
	}
	
	@Override
	public Integer generateId() {
		return nextAvaliableIndex++;
	}
	
	public int getAncestorOfType(int entityId, @SuppressWarnings("rawtypes") Class classType) throws EntityNotExistsException, NoRequestedTypeOfAncestorExistsException {
		Entity e = this.getEntity(entityId);
		if (e==null) throw new EntityNotExistsException(entityId);
		Entity ancestor = e.getAncestorOfType(classType);
		if (ancestor==null)
			throw new NoRequestedTypeOfAncestorExistsException(entityId,classType);
		return ancestor.getId();
	}
	
	public Set<String> resolveAllBindings() {
		System.out.println("Infer types of variables, methods and expressions....");
		inferTypes();
		System.out.println("Infer types done.");
		System.out.println("Dependency analaysing....");
		
		new RelationCounter(allEntitiesById.values()).computeRelations();
		
		System.out.println("Dependency done....");
		System.out.println("Post-processing of dependency....");
    	HashSet<String> unsolved = new HashSet<>();
        for (Entity entity:allEntitiesById.values()) {
        	Set<String> u = entity.resolveBinding(this);
			unsolved.addAll(u);
        }
		System.out.println("Post-processing of dependency done.");
		return unsolved;		

	}

	private void inferTypes() {
		for (Entity entity:allEntitiesById.values()) {
			if (!(entity instanceof FileEntity)) continue;
			entity.inferTypes(this);
		}
		for (Entity entity:allEntitiesById.values()) {
			if ((entity instanceof ContainerEntity))
				((ContainerEntity)entity).resolveExpressions(this);
		}
	}
	
	public void setParent(Entity child, Entity parent) {
		child.setParent(parent);
		parent.addChild(child);
	}
	@Override
	public InferData inferType(Entity fromEntity, String rawName) {
		return inferType(fromEntity, rawName, true) ;
	}
	
	@Override
	public InferData inferTypeWithoutImportSearch(Entity fromEntity, String rawName) {
		return inferType(fromEntity, rawName, false) ;
	}

	private InferData inferType(Entity fromEntity, String rawName, boolean searchImport) {
		if(rawName==null) return null;
		if (buildInProcessor.isBuiltInType(rawName)) {
			return  new InferData(buildInType,buildInType);
		}
		if (buildInProcessor.isBuiltInTypePrefix(rawName))  {
			return  new InferData(buildInType,buildInType);
		}
		
		//qualified name will first try global name directly
		if (rawName.contains(".")) {
			if (this.getTypeEntityByFullName(rawName)!=null) 
				return this.getTypeEntityByFullName(rawName);
		}
		
		//first we lookup the first symbol
		String[] names = rawName.split("\\.");
		if (names.length==0) return null;
		InferData type = lookupTypes(fromEntity,names[0],searchImport);
		if (type==null) return null;
		if (names.length==1 ) {
			return type;
		}
		//then find the subsequent symbols
		return findReferenceSince(type,names,1);
	}
	
	private InferData findReferenceSince(InferData sinceType, String[] names, int i) {
		if (i>=names.length) {
			return sinceType;
		}
		for (Entity child:sinceType.type.getChildren()) {
			if (child.getRawName().equals(names[i])) {
				return findReferenceSince(convertToInferData(child),names,i+1);
			}
		}
		return null;
	}

	private InferData lookupTypes(Entity fromEntity, String name, boolean searcImport) {
		if (name.equals("this")||name.equals("class")) {
			TypeEntity entityType = (TypeEntity)(fromEntity.getAncestorOfType(TypeEntity.class));
			return new InferData(entityType,entityType);
		}
		else if (name.equals("super")) {
			TypeEntity parent = (TypeEntity)(fromEntity.getAncestorOfType(TypeEntity.class));
			if (parent!=null) {
				TypeEntity parentType = parent.getInheritedType();
				return new InferData(parentType,parentType);
			}
		}
		
		InferData inferData = findTypeUnderSamePackage(fromEntity,name);
		if (inferData!=null) return inferData;
		if (searcImport)
			inferData = lookupTypeInImported(fromEntity.getAncestorOfType(FileEntity.class),name);
		return inferData;
	}

	private InferData lookupTypeInImported(Entity entity, String name) {
		if (entity==null) return null;
		if (!(entity instanceof FileEntity)) return null;
		FileEntity fileEntity = (FileEntity)entity;
		InferData type = importLookupStrategy.lookupImportedType(name, fileEntity,this);
		if (type!=null) return type;
		return new InferData(externalType,externalType);
	}

	private InferData tryToFindTypeEntityWithName(Entity fromEntity, String name) {
		if (!fromEntity.getRawName().equals(name)) return null;
		if (fromEntity instanceof MultiDeclareEntities) {
			for (Entity declaredEntitiy:((MultiDeclareEntities)fromEntity).getEntities()) {
				if (declaredEntitiy.getRawName().equals(name) && declaredEntitiy instanceof TypeEntity) {
					return convertToInferData(declaredEntitiy);
				}
			}
		}
		return convertToInferData(fromEntity);
	}

	private InferData convertToInferData(Entity entity) {
		if (entity instanceof TypeEntity) {
			return new InferData((TypeEntity)entity,entity);
		}
		if (entity instanceof VarEntity) {
			return new InferData(((VarEntity)entity).getType(),entity);
		}
		if (entity instanceof FunctionEntity) {
			return new InferData(((FunctionEntity)entity).getReturnType(),entity);
		}
		return null;
	}

	private InferData findTypeUnderSamePackage(Entity fromEntity,String name) {
		while(true) {
			 InferData type = tryToFindTypeEntityWithName(fromEntity,name);
			if (type!=null)  return type;
			for (Entity child:fromEntity.getChildren()) {
				type = tryToFindTypeEntityWithName(child,name);
				if (type!=null)  return type;
				if (child instanceof FileEntity) {
					for (Entity classUnderFile:child.getChildren()) {
						type = tryToFindTypeEntityWithName(classUnderFile,name);
						if (type!=null)  return type;
					}
				}
			}
			fromEntity = fromEntity.getParent();
			if (fromEntity==null) break;
		}
		return null;

	}


	public InferData getTypeEntityByFullName(String rawName) {
		Entity entity = this.getEntity(rawName);
		if(entity ==null) return null;
		return convertToInferData(entity);
	}
	
	
	public void setBuiltInTypeIdentifier(BuiltInTypeIdenfier buildInProcessor) {
		this.buildInProcessor = buildInProcessor;
	}

	
	
	@Override
	public FunctionEntity resolveFunctionBindings(Entity fromEntity, String functionName) {
		while(fromEntity!=null) {
			if (fromEntity instanceof ContainerEntity) {
				FunctionEntity func = findFunctionFromType( (ContainerEntity)fromEntity, functionName);
				if (func!=null) return func;
			}
			fromEntity = fromEntity.getParent();
		}
		return null;
	}

	private FunctionEntity findFunctionFromType(ContainerEntity type, String functionName) {
		for (FunctionEntity var:type.getFunctions()) {
			if (var.getRawName().equals(functionName))
				return var;
		}
		FunctionEntity funcType = null; 
		if (type instanceof TypeEntity) {
			TypeEntity typeType = (TypeEntity)type;
			for (TypeEntity inhertedType:typeType.getInheritedTypes()) {
				funcType= findFunctionFromType(inhertedType,functionName);
				if (funcType==null) break;
			}
			if (funcType!=null) return funcType;
			for (TypeEntity implType:typeType.getImplementedTypes()) {
				funcType= findFunctionFromType(implType,functionName);
				if (funcType==null) break;
			}
		}
		return funcType;
	}
	
	
	public void addRelation(int theEntityId, String entityFullName, String relationType) {
	        getEntity(theEntityId).addRelation(new Relation(relationType,entityFullName));
	}

	@Override
	public boolean isBuiltInTypePrefix(String prefix) {
		return buildInProcessor.isBuiltInTypePrefix(prefix);
	}

	@Override
	
	public List<Entity> getImportedRelationEntities(List<Import> importedNames){
		return importLookupStrategy.getImportedRelationEntities(importedNames,this);
	}
	
	@Override
	public List<Entity> getImportedTypes(List<Import> importedNames){
		return importLookupStrategy.getImportedTypes(importedNames,this);
	}
	@Override
	public List<Entity> getImportedFiles(List<Import> importedNames){
		return importLookupStrategy.getImportedFiles(importedNames,this);
	}

	@Override
	public TypeEntity inferTypeType(Entity fromEntity, String rawName) {
		InferData data = inferType(fromEntity, rawName);
		if (data==null) return null;
		return data.type;
	}

}
