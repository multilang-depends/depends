package depends.matrix;

import depends.entity.repo.EntityRepo;

public interface DependencyGenerator {
	DependencyMatrix build(EntityRepo entityRepo);
}
