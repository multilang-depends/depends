package depends.matrix;

import java.util.ArrayList;
import java.util.List;

import depends.entity.CandidateTypes;
import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.TypeEntity;
import depends.entity.repo.EntityRepo;
import depends.relations.Relation;

public class FileDependencyGenerator implements DependencyGenerator{
	/**
	 * Build the dependency matrix (without re-mapping file id)
	 * @param entityRepo which contains entities and relations
	 * @return the generated dependency matrix
	 */
	@Override
	public DependencyMatrix build(EntityRepo entityRepo) {
		DependencyMatrix dependencyMatrix = new DependencyMatrix();
        ArrayList<String> files = new ArrayList<String>();
		for (Entity entity:entityRepo.getEntities()) {
        	if (entity instanceof FileEntity){
        		files.add( entity.getDisplayName());
        	}
        	int fileEntityFrom = getFileEntityIdNoException(entityRepo, entity);
        	if (fileEntityFrom==-1) continue;
        	for (Relation relation:entity.getRelations()) {
        		Entity relatedEntity = relation.getEntity();
        		if (relatedEntity instanceof CandidateTypes) {
        			List<TypeEntity> candidateTypes = ((CandidateTypes)relatedEntity).getCandidateTypes();
        			for (TypeEntity candidateType:candidateTypes) {
    	        		if (candidateType.getId()>=0) {
    	        			int fileEntityTo = getFileEntityIdNoException(entityRepo,candidateType);
    	        			if (fileEntityTo!=-1) {
    	        				dependencyMatrix.addDependency(relation.getType(), fileEntityFrom,fileEntityTo,entity,candidateType);
    	        			}
    	        		}
        			}
        		}else {
	        		if (relatedEntity.getId()>=0) {
	        			int fileEntityTo = getFileEntityIdNoException(entityRepo,relatedEntity);
	        			if (fileEntityTo!=-1) {
	        				dependencyMatrix.addDependency(relation.getType(), fileEntityFrom,fileEntityTo,entity,relatedEntity);
	        			}
	        		}
        		}
        	}
        }
		dependencyMatrix.setNodes(files);
		return dependencyMatrix;
	}

	
	private int getFileEntityIdNoException(EntityRepo entityRepo, Entity entity) {
		Entity ancestor = entity.getAncestorOfType(FileEntity.class);
		if (ancestor==null)
			return -1;
		return ancestor.getId();
	}
}
