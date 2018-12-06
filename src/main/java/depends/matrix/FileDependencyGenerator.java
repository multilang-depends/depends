package depends.matrix;

import java.util.ArrayList;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.relations.Relation;

public class FileDependencyGenerator {
	/**
	 * Build the dependency matrix (without re-mapping file id)
	 * @param entityRepo which contains entities and relations
	 * @return the generated dependency matrix
	 */
	public DependencyMatrix build(EntityRepo entityRepo) {
		DependencyMatrix dependencyMatrix = new DependencyMatrix();
        ArrayList<String> files = new ArrayList<String>();
		for (Entity entity:entityRepo.getEntities()) {
        	if (entity instanceof FileEntity){
        		files.add( entity.getRawName());
        	}
        	int fileEntityFrom = getFileEntityIdNoException(entityRepo, entity);
        	if (fileEntityFrom==-1) continue;
        	for (Relation relation:entity.getRelations()) {
        		if (relation.getEntity().getId()>=0) {
        			int fileEntityTo = getFileEntityIdNoException(entityRepo,relation.getEntity());
        			if (fileEntityTo==-1) continue;
        			dependencyMatrix.addDependency(relation.getType(), fileEntityFrom,fileEntityTo,entity,relation.getEntity());
        		}
        	}
        }
		dependencyMatrix.setNodes(files);
		return dependencyMatrix;
	}

	/**
	 * Build the dependency matrix (re-mapped all dependencies with 0-based values 
	 * to align with node indexes)
	 * @param entityRepo which contains entities and relations
	 * @return the generated dependency matrix
	 */
	public DependencyMatrix buildWithRemap(EntityRepo repo) {
		DependencyMatrix r = build(repo);
		r.remapIds(repo);
		return r;
	}

	private int getFileEntityIdNoException(EntityRepo entityRepo, Entity entity) {
		Entity ancestor = entity.getAncestorOfType(FileEntity.class);
		if (ancestor==null)
			return -1;
		return ancestor.getId();
	}
}
