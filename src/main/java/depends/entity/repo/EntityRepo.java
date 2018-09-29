package depends.entity.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.deptypes.DependencyType;
import depends.entity.BindingResolver;
import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.IdGenerator;
import depends.entity.Relation;
import depends.entity.TypeInfer;
import depends.entity.types.FileEntity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.PackageEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.extractor.FileParser;

public class EntityRepo implements IdGenerator,BindingResolver,TypeInfer{
	public HashMap<String, Entity> allEntieisByName = new HashMap<>();
	public HashMap<Integer, Entity> allEntitiesById = new HashMap<>();
	private int nextAvaliableIndex;
	private FileParser buildInProcessor = new NullBuildInProcessor();
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
		while (!e.getClass().equals(classType)) {
			if (e.getParent()==null) {
				throw new NoRequestedTypeOfAncestorExistsException(entityId,classType);
			}
			e = this.getEntity(e.getParent().getId());
			if (e==null) break;
		}
		if (e==null) throw new NoRequestedTypeOfAncestorExistsException(entityId,classType);
		return e.getId();
	}
	
	public Set<String> resolveAllBindings() {
		inferTypes();
		computeRelations();
    	HashSet<String> unsolved = new HashSet<>();
        for (Entity entity:allEntitiesById.values()) {
        	Set<String> u = entity.resolveBinding(this);
			unsolved.addAll(u);
        }
		return unsolved;		

	}

	private void inferTypes() {
		for (Entity entity:allEntitiesById.values()) {
			if (!(entity instanceof FileEntity)) continue;
			entity.inferTypes(this);
		}
	}
	private void computeRelations() {
		for (Entity entity:allEntitiesById.values()) {
			if (entity instanceof FileEntity) {
				computeImports((FileEntity)entity);
			}
			if (entity instanceof FunctionEntity) {
				computeFunctionRelations((FunctionEntity)entity);
			}
			if (entity instanceof TypeEntity) {
				computeTypeRelations((TypeEntity)entity);
			}
			if (entity instanceof ContainerEntity) {
				computeContainerRelations((ContainerEntity)entity);
			}
		}
	}

	
	private void computeContainerRelations(ContainerEntity entity) {
		for (VarEntity var:entity.getVars()) {
			if (var.getType()!=null)
				entity.addRelation(new Relation(DependencyType.RELATION_DEFINE,var.getType().getId(),var.getType().getQualifiedName()));
			else
				System.out.println("cannot resove type of "+var.getQualifiedName());
		}
	}

	private void computeTypeRelations(TypeEntity type) {
		for (TypeEntity superType:type.getInheritedTypes()) {
			type.addRelation(new Relation(DependencyType.RELATION_INHERIT,superType.getId(),superType.getQualifiedName()));
		}
		for (TypeEntity interfaceType:type.getImplementedTypes()) {
			type.addRelation(new Relation(DependencyType.RELATION_IMPLEMENT,interfaceType.getId(),interfaceType.getQualifiedName()));
		}
	}

	private void computeFunctionRelations(FunctionEntity func) {
		for (TypeEntity returnType:func.getReturnTypes()) {
			func.addRelation(new Relation(DependencyType.RELATION_RETURN,returnType.getId(),returnType.getQualifiedName()));
		}
		for (VarEntity parameter:func.getParameters()) {
			if (parameter.getType()!=null) {
				func.addRelation(new Relation(DependencyType.RELATION_PARAMETER,parameter.getType().getId(),parameter.getType().getQualifiedName()));
			}else {
				System.out.println("unsolved param: "+parameter);
			}
		}
		for (TypeEntity throwType:func.getThrowTypes()) {
			func.addRelation(new Relation(DependencyType.RELATION_USE,throwType.getId(),throwType.getQualifiedName()));
		}
	}

	private void computeImports(FileEntity file) {
		Collection<String> imports = file.imports();
		List<Integer> importedIds = new ArrayList<>();
		for (String item:imports) {
			if (this.buildInProcessor.isBuiltInTypePrefix(item)) continue;
			Entity imported = this.getEntity(item);
			if (imported==null) {
				System.out.println("imported cannot be resolved: " + file.getQualifiedName() + "->" + item);
				continue;
			}
			if (imported instanceof PackageEntity) { 
				//expand import of package to all classes under the package due to we dis-courage the behavior
				for (Entity child:imported.getChildren()) {
					importedIds.add(child.getId());
				}
			}else {
				importedIds.add(imported.getId());
			}
		}
		for (Integer id:importedIds) {
			file.addRelation(new Relation(DependencyType.RELATION_IMPORT,id));
		}
	}
	
	public void addRelation(int theEntityId, String entityFullName, String relationType) {
	        getEntity(theEntityId).addRelation(new Relation(relationType,entityFullName));
	}
	
	@Override
	public String inferQualifiedName(Entity theEntity, String rawTypeName) {
		if (rawTypeName.isEmpty()) return "";
		String prefix = "";
		while (theEntity!=null) {
			if (theEntity instanceof FileEntity) continue; //file name should be bypass. use package name instead 
			if(theEntity.getQualifiedName()!=null &&
					theEntity.getQualifiedName().length()>0 &&
					!(theEntity.getQualifiedName().startsWith("<Anony>"))) {
				prefix = theEntity.getQualifiedName();
				break;
			}
			theEntity = theEntity.getParent();
		}
		if (prefix.isEmpty()) {
			return rawTypeName;
		}else {
			return  prefix + "." + rawTypeName;
		}
	}
	
	
	@Override
	public VarEntity resolveVarBindings(Entity theContainer, String varName) {
		while(theContainer!=null) {
			if (theContainer instanceof ContainerEntity) {
				for (VarEntity var:((ContainerEntity)theContainer).getVars()) {
					if (var.getRawName().equals(varName))
						return var;
				}
			}
			theContainer = theContainer.getParent();
		}
		return null;
	}

	@Override
	public FunctionEntity resolveFunctionBindings(Entity theContainer, String varName) {
		while(theContainer!=null) {
			if (theContainer instanceof TypeEntity) {
				for (FunctionEntity var:((TypeEntity)theContainer).getFunctions()) {
					if (var.getRawName().equals(varName))
						return var;
				}
			}
			theContainer = theContainer.getParent();
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
		while(true) {
			if (fromEntity instanceof TypeEntity) {
				if (fromEntity.getRawName().equals(rawName))
					return (TypeEntity)fromEntity;
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
			fromEntity = fromEntity.getParent();
			if (fromEntity==null) break;
		}
		if (type==null) {
			System.out.println("cannot infer type of " + rawName);
		}
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
	
	@Override
	public void addBuiltIn(FileParser fileParser) {
		this.buildInProcessor = fileParser;
	}

}
