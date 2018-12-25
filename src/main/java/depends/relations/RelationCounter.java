package depends.relations;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import depends.deptypes.DependencyType;
import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.Expression;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;

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
				entity.addRelation(new Relation(DependencyType.CONTAIN,var.getType()));
		}
		for (TypeEntity type:entity.getResolvedAnnotations()) {
			entity.addRelation(new Relation(DependencyType.USE,type));
		}
		for (TypeEntity type:entity.getResolvedTypeParameters()) {
			entity.addRelation(new Relation(DependencyType.USE,type));
		}
		
		HashSet<Entity> usedEntities = new HashSet<>();
		for (Expression expression:entity.expressions().values()){
			Entity referredEntity = expression.getReferredEntity();
			if (referredEntity==null) {
				continue;
			}
			
			if (expression.isCall) {
				entity.addRelation(new Relation(DependencyType.CALL,referredEntity));
			}
			if (expression.isCreate) {
				entity.addRelation(new Relation(DependencyType.CREATE,referredEntity));
			}
			if (expression.isSet) { //SET is merged with USE
				entity.addRelation(new Relation(DependencyType.USE,referredEntity));
			}
			if (expression.isCast) { 
				entity.addRelation(new Relation(DependencyType.CAST,referredEntity));
			}
			if (!expression.isCall && !expression.isCreate && !expression.isCast) {
				usedEntities.add(expression.getReferredEntity());
			}
		}
		
		for (Entity usedEntity:usedEntities) {
			entity.addRelation(new Relation(DependencyType.USE,usedEntity));
		}
	}

	private void computeTypeRelations(TypeEntity type) {
		for (TypeEntity superType:type.getInheritedTypes()) {
			type.addRelation(new Relation(DependencyType.INHERIT,superType));
		}
		for (TypeEntity interfaceType:type.getImplementedTypes()) {
			type.addRelation(new Relation(DependencyType.IMPLEMENT,interfaceType));
		}
	}

	private void computeFunctionRelations(FunctionEntity func) {
		for (TypeEntity returnType:func.getReturnTypes()) {
			func.addRelation(new Relation(DependencyType.RETURN,returnType));
		}
		for (VarEntity parameter:func.getParameters()) {
			if (parameter.getType()!=null) 
				func.addRelation(new Relation(DependencyType.PARAMETER,parameter.getType()));
		}
		for (TypeEntity throwType:func.getThrowTypes()) {
			func.addRelation(new Relation(DependencyType.THROW,throwType));
		}
	}

	private void computeImports(FileEntity file) {
		List<Entity> imports = file.getImportedRelationEntities();
		for (Entity imported:imports) {
			if (imported instanceof FileEntity)
			{
				if (((FileEntity)imported).isInProjectScope())
					file.addRelation(new Relation(DependencyType.IMPORT,imported));
			}else {
				file.addRelation(new Relation(DependencyType.IMPORT,imported));
			}
		}
	}

}
