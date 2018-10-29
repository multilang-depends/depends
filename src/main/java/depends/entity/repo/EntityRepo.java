package depends.entity.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.deptypes.DependencyType;
import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.Expression;
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
import depends.util.Tuple;

public class EntityRepo implements IdGenerator,TypeInfer{
	public HashMap<String, Entity> allEntieisByName = new HashMap<>();
	public HashMap<Integer, Entity> allEntitiesById = new HashMap<>();
	private int nextAvaliableIndex;
	private BuiltInTypeIdenfier buildInProcessor = new NullParser();
	private TypeEntity buildInType;
	
	public EntityRepo() {
		nextAvaliableIndex = 0;
		buildInType = new TypeEntity("built-in", null, -1);
	}
	
	public Entity getEntity(String entityName) {
		return allEntieisByName.get(entityName);
	}
	public Entity getEntity(Integer entityId) {
		return allEntitiesById.get(entityId);
	}
	
	public void add(Entity entity) {
		allEntitiesById.put(entity.getId(), entity);
		if (entity instanceof FileEntity) {
			System.out.println("**FOUND " + entity.getQualifiedName());
		}
		if (entity.getQualifiedName()!=null)
			allEntieisByName.put(entity.getQualifiedName(), entity);
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
	}
	

	
	@Override
	public VarEntity resolveVarBindings(Entity fromEntity, String varName) {
		while(fromEntity!=null) {
			if (fromEntity instanceof ContainerEntity) {
				for (VarEntity var:((ContainerEntity)fromEntity).getVars()) {
					if (var.getRawName().equals(varName))
						return var;
				}
			}
			fromEntity = fromEntity.getParent();
		}
		return null;
	}

	
	
	public void setParent(Entity child, Entity parent) {
		child.setParent(parent);
		parent.addChild(child);
	}
	
	@Override
	public TypeEntity inferType(Entity fromEntity, String rawName) {
		if (buildInProcessor.isBuiltInType(rawName)) return buildInType();
		if (buildInProcessor.isBuiltInTypePrefix(rawName)) return buildInType();
		if (fromEntity==null) return null;
		TypeEntity type = null;
		if (rawName.contains(".")) {
			return getTypeEntityByFullName(rawName);
		}
		if (rawName.equals("this")) {
			Entity entityType = fromEntity.getAncestorOfType(TypeEntity.class);
			if (entityType!=null) {
				return (TypeEntity) entityType;
			}
		}
		else if (rawName.equals("super")) {
			Entity entityType = fromEntity.getAncestorOfType(TypeEntity.class);
			if (entityType!=null) {
				return ((TypeEntity)entityType).getInheritedType();
			}
		}
		
		while(true) {
			if (fromEntity instanceof TypeEntity) {
				if (fromEntity.getRawName().equals(rawName))
					return (TypeEntity)fromEntity;
				FunctionEntity var = resolveFunctionBindings(fromEntity,rawName);	
				if (var!=null)
					return var.getReturnType(); 				
			}
			if (fromEntity instanceof FileEntity) {
				String importedFullName = ((FileEntity)fromEntity).getImport(rawName);
				if (importedFullName!=null)
					return getTypeEntityByFullName(importedFullName);
				type = this.getTypeEntityUnder(rawName,fromEntity);
				if(type!=null) return type;
			}
			if (fromEntity instanceof PackageEntity){
				type = this.getTypeEntityUnder(rawName,fromEntity);
				if(type!=null) return type;
			}
			if (fromEntity instanceof ContainerEntity) {
				VarEntity var = resolveVarBindings(fromEntity,rawName);	
				if (var!=null)
					return var.getType(); 
			}
			fromEntity = fromEntity.getParent();
			if (fromEntity==null) break;
		}
		
		type = getTypeEntityByFullName(rawName);
		return type;
	}
	


	private TypeEntity buildInType() {
		return this.buildInType;
	}
	private TypeEntity getTypeEntityByFullName(String rawName) {
		if (buildInProcessor.isBuiltInType(rawName)) return buildInType();
		if (buildInProcessor.isBuiltInTypePrefix(rawName)) return buildInType();
		Entity entity = this.getEntity(rawName);
		if (entity instanceof TypeEntity) {
			return (TypeEntity)entity;
		}
		if (entity instanceof PackageEntity) {
			return getTypeEntityUnder(rawName, entity);
		}
		return null;
	}
	private TypeEntity getTypeEntityUnder(String rawName, Entity entity) {
		for (Entity level_1:entity.getChildren()) {
			if (level_1 instanceof TypeEntity) {
				if (level_1.getRawName().equals(rawName)) {
					return (TypeEntity)level_1;
				}
			}
			for (Entity level_2:level_1.getChildren()) {
				if (level_2 instanceof TypeEntity) {
					if (level_2.getRawName().equals(rawName)) {
						return (TypeEntity)level_2;
					}
				}
			}
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
			TypeEntity type = inferType(fromEntity, qualifiedName);
			if (type != null)
				return new Tuple<TypeEntity, String>(type, localName);
			int lpos = qualifiedName.lastIndexOf(".");
			if (lpos < 0)
				return null;
			localName = localName == null ? qualifiedName.substring(lpos + 1)
					: localName + "." + qualifiedName.substring(lpos + 1);
			qualifiedName = qualifiedName.substring(0, lpos);
			type = inferType(fromEntity, qualifiedName);
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
