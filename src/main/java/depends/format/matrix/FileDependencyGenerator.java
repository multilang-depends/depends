package depends.format.matrix;

import java.util.ArrayList;

import depends.entity.Entity;
import depends.entity.Relation;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.util.Tuple;

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
        		files.add( entity.getFullName());
        	}
        	int fileEntityFrom = getFileEntityIdNoException(entityRepo, entity.getId());
        	if (fileEntityFrom==-1) continue;
        	for (Relation relation:entity.getRelations()) {
        		if (relation.getToId()>=0) {
        			int fileEntityTo = getFileEntityIdNoException(entityRepo,relation.getToId());
        			if (fileEntityTo==-1) continue;
        			dependencyMatrix.addDependency(relation.getType(), new Tuple<Integer, Integer>(fileEntityFrom,fileEntityTo));
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

	private int getFileEntityIdNoException(EntityRepo entityRepo, Integer entityId){
		try {
			return entityRepo.getAncestorOfType(entityId, FileEntity.class);
		} catch (Exception e) {
			return -1;
		} 
	}
	}
