package depends.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import depends.deptypes.DependencyType;
import depends.entity.types.FileEntity;
import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;

public class RelationCounter {

	private Collection<Entity> entities;

	public RelationCounter(Collection<Entity> entities) {
		this.entities = entities;
	}

	
	public void computeRelations() {
		for (Entity entity:entities) {
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
		for (TypeEntity type:entity.getResolvedAnnotations()) {
			entity.addRelation(new Relation(DependencyType.RELATION_USE,type.getId(),type.getQualifiedName()));
		}
		for (TypeEntity type:entity.getResolvedTypeParameters()) {
			entity.addRelation(new Relation(DependencyType.RELATION_USE,type.getId(),type.getQualifiedName()));
		}
		
		HashSet<TypeEntity> usedEntities = new HashSet<>();
		for (Expression expression:entity.expressions().values()){
			if (expression.type==null) {
				System.out.println("not resolved expression:" + expression.text + " in " + entity.getQualifiedName());
				continue;
			}
			if (expression.isCall) {
				entity.addRelation(new Relation(DependencyType.RELATION_CALL,expression.type.getId(),expression.type.getQualifiedName()));
			}
			if (expression.isCreate) {
				entity.addRelation(new Relation(DependencyType.RELATION_CREATE,expression.type.getId(),expression.type.getQualifiedName()));
			}
			else if (expression.isSet) {
				entity.addRelation(new Relation(DependencyType.RELATION_SET,expression.type.getId(),expression.type.getQualifiedName()));
			}else {
				usedEntities.add(expression.type);
			}
		}
		
		for (TypeEntity usedEntity:usedEntities) {
			entity.addRelation(new Relation(DependencyType.RELATION_USE,usedEntity.getId(),usedEntity.getQualifiedName()));
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
		List<Entity> imports = file.getResolvedImportedEntities();
		for (Entity imported:imports) {
			file.addRelation(new Relation(DependencyType.RELATION_IMPORT,imported.getId()));
		}
	}

}
