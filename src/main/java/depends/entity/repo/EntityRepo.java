package depends.entity.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.IdGenerator;
import depends.entity.Relation;
import depends.entity.RelationCounter;
import depends.entity.TypeInfer;
import depends.entity.types.FileEntity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.PackageEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.extractor.BuiltInTypeIdenfier;
import depends.extractor.ImportLookupStrategy;
import depends.util.Tuple;

public class EntityRepo implements IdGenerator,TypeInfer{
	public HashMap<String, Entity> allEntieisByName = new HashMap<>();
	public HashMap<Integer, Entity> allEntitiesById = new HashMap<>();
	private int nextAvaliableIndex;
	private BuiltInTypeIdenfier buildInProcessor = new NullParser();
	private ImportLookupStrategy importLookupStrategy;
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
		if (entity.getQualifiedName()!=null) {
			if (entity.getQualifiedName().isEmpty()) {
				allEntieisByName.put(entity.getRawName(), entity);
			}else {
				allEntieisByName.put(entity.getQualifiedName(), entity);
			}
		}
		if (entity.getParent()!=null)
			this.setParent(entity, entity.getParent());
	}
	
	public void updateEntityNameIndex(String oldName, String newName, Entity entity) {
		if (oldName.isEmpty()) {
			allEntieisByName.put(newName, entity);
			return;
		}
		if (entity==null) return;
		allEntieisByName.remove(oldName);
		allEntieisByName.put(newName, entity);
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
	public TypeEntity inferType(Entity fromEntity, String rawName, boolean typeOnly) {
		if(rawName==null) return null;
		if (buildInProcessor.isBuiltInType(rawName)) return buildInType;
		if (buildInProcessor.isBuiltInTypePrefix(rawName)) return buildInType;
		
		//qualified name will first try global name directly
		if (rawName.contains(".")) {
			if (this.getTypeEntityByFullName(rawName)!=null) 
				return this.getTypeEntityByFullName(rawName);
		}
		
		//first we lookup the first symbol
		String[] names = rawName.split("\\.");
		if (names.length==0) return null;
		Entity type = lookupTypes(fromEntity,names[0],typeOnly);
		if (type==null) return null;
		if (names.length==1 ) {
			TypeEntity actualType = getType(type);
			if (actualType!=null) return actualType;
		}
		//then find the subsequent symbols
		type = findTypesSince(type,names,1,typeOnly);
		TypeEntity returnType = getType(type);
		if (typeOnly)
			return returnType;
		else if (returnType!=null)
			return returnType;
		return null;
	}
	
	/**
	 * A utility function to get the entity type
	 * @param type
	 * @return
	 */
	private static TypeEntity getType(Entity type) {
		if (type instanceof TypeEntity)
			return (TypeEntity)type;
		else if (type instanceof FunctionEntity) {
			return ((FunctionEntity)type).getReturnType();
		}else if (type instanceof VarEntity) {
			return ((VarEntity)type).getType();
		}
		return null;
	}

	private TypeEntity findTypesSince(Entity sinceType, String[] names, int i, boolean typeOnly) {
		if (i>=names.length) {
			return (sinceType instanceof TypeEntity)? (TypeEntity)sinceType:null;
		}
		for (Entity child:sinceType.getChildren()) {
			if (child.getRawName().equals(names[i]) && child instanceof TypeEntity) {
				return findTypesSince(child,names,i+1,typeOnly);
			}
		}
		return null;
	}

	public Entity lookupTypes(Entity fromEntity, String name, boolean typeOnly) {
		if (name.equals("this")||name.equals("class")) {
			Entity entityType = fromEntity.getAncestorOfType(TypeEntity.class);
			return entityType;
		}
		else if (name.equals("super")) {
			Entity parent = fromEntity.getAncestorOfType(TypeEntity.class);
			if (parent!=null) {
				return ((TypeEntity)parent).getInheritedType();
			}
		}
		
		Entity type = findTypeUnderSamePackage(fromEntity,name);
		if (type!=null) return type;
		type = lookupTypeInImported(fromEntity.getAncestorOfType(FileEntity.class),name, typeOnly);
		return type;
	}

	private Entity lookupTypeInImported(Entity entity, String name, boolean typeOnly) {
		if (entity==null) return null;
		if (!(entity instanceof FileEntity)) return null;
		FileEntity fileEntity = (FileEntity)entity;
		Entity type = importLookupStrategy.lookupImportedType(name, fileEntity,this,typeOnly);
		if (type!=null) return type;
		return externalType;
	}


	private TypeEntity findTypeUnderSamePackage(Entity fromEntity,String name) {
		while(true) {
			if (fromEntity.getRawName().equals(name) && fromEntity instanceof TypeEntity)
				return (TypeEntity)fromEntity;
			for (Entity child:fromEntity.getChildren()) {
				if (child.getRawName().equals(name) && child instanceof TypeEntity)
					return (TypeEntity)child;
				if (child instanceof FileEntity) {
					for (Entity classUnderFile:child.getChildren()) {
						if (classUnderFile.getRawName().equals(name) && classUnderFile instanceof TypeEntity)
							return (TypeEntity)classUnderFile;
					}
				}
			}
			fromEntity = fromEntity.getParent();
			if (fromEntity==null) break;
		}
		return null;

	}


	public TypeEntity getTypeEntityByFullName(String rawName) {
		Entity entity = this.getEntity(rawName);
		if(entity ==null) return null;
		if (entity instanceof TypeEntity) {
			return (TypeEntity)entity;
		}
		return null;
	}
	
	
	public void setBuiltInTypeIdentifier(BuiltInTypeIdenfier buildInProcessor) {
		this.buildInProcessor = buildInProcessor;
	}

	@Override
	public Tuple<TypeEntity, String> locateTypeOfQualifiedName(ContainerEntity fromEntity, String qualifiedName) {
		String localName = null;
		while (true) {
			TypeEntity type = inferType(fromEntity, qualifiedName,false);
			if (type != null)
				return new Tuple<TypeEntity, String>(type, localName);
			int lpos = qualifiedName.lastIndexOf(".");
			if (lpos < 0)
				return null;
			localName = localName == null ? qualifiedName.substring(lpos + 1)
					: localName + "." + qualifiedName.substring(lpos + 1);
			qualifiedName = qualifiedName.substring(0, lpos);
			type = inferType(fromEntity, qualifiedName,false);
			return new Tuple<TypeEntity, String>(type,localName);
		}
	}
	
	
	@Override
	public FunctionEntity resolveFunctionBindings(Entity fromEntity, String functionName) {
		while(fromEntity!=null) {
			if (fromEntity instanceof TypeEntity) {
				TypeEntity theType = (TypeEntity)fromEntity;
				FunctionEntity func = findFunctionFromType(theType, functionName);
				if (func!=null) return func;
			}
			fromEntity = fromEntity.getParent();
		}
		return null;
	}

	private FunctionEntity findFunctionFromType(TypeEntity type, String functionName) {
		for (FunctionEntity var:type.getFunctions()) {
			if (var.getRawName().equals(functionName))
				return var;
		}
		FunctionEntity funcType = null; 
		for (TypeEntity inhertedType:type.getInheritedTypes()) {
			funcType= findFunctionFromType(inhertedType,functionName);
			if (funcType==null) break;
		}
		if (funcType!=null) return funcType;
		for (TypeEntity implType:type.getImplementedTypes()) {
			funcType= findFunctionFromType(implType,functionName);
			if (funcType==null) break;
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
	public List<Entity> resolveImportEntity(String importedName) {
		ArrayList<Entity> result = new ArrayList<>();
		Entity imported = this.getEntity(importedName);
		if (imported==null) return result;
		if (imported instanceof PackageEntity) { 
			//expand import of package to all classes under the package due to we dis-courage the behavior
			for (Entity child:imported.getChildren()) {
				result.add(child);
			}
		}else {
			result.add(imported);
		}
		return result;
	}
}
