package depends.matrix;

import java.util.ArrayList;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.repo.EntityRepo;
import depends.relations.Relation;

public class FunctionDependencyGenerator implements DependencyGenerator {
	@Override
	public DependencyMatrix build(EntityRepo entityRepo) {
		DependencyMatrix dependencyMatrix = new DependencyMatrix();
		ArrayList<String> elements = new ArrayList<String>();
		for (Entity entity : entityRepo.getEntities()) {
			if (entity instanceof FunctionEntity) {
				elements.add(entity.getDisplayName());
			}
			int fileEntityFrom = getFunctionEntityIdNoException(entity);
			if (fileEntityFrom == -1)
				continue;
			for (Relation relation : entity.getRelations()) {
				if (relation.getEntity().getId() >= 0) {
					int fileEntityTo = getFunctionEntityIdNoException(relation.getEntity());
					if (fileEntityTo == -1)
						continue;
					dependencyMatrix.addDependency(relation.getType(), fileEntityFrom, fileEntityTo, entity,
							relation.getEntity());
				}
			}
		}
		dependencyMatrix.setNodes(elements);
		return dependencyMatrix;
	}

	private String getFileNameNoException(Entity entity) {
		Entity ancestor = entity.getAncestorOfType(FileEntity.class);
		if (ancestor == null)
			return "";
		return ancestor.getRawName();
	}

	private int getFunctionEntityIdNoException(Entity entity) {
		Entity ancestor = entity.getAncestorOfType(FunctionEntity.class);
		if (ancestor == null)
			return -1;
		return ancestor.getId();
	}
}
