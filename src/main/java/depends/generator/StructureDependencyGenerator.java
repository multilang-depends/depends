/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.generator;

import depends.entity.*;
import depends.entity.repo.EntityRepo;
import depends.matrix.core.DependencyDetail;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.LocationInfo;
import depends.relations.Relation;

import java.util.Iterator;
import java.util.List;

public class StructureDependencyGenerator extends DependencyGenerator{
	/**
	 * Build the dependency matrix (without re-mapping file id)
	 * @param entityRepo which contains entities and relations
	 * @return the generated dependency matrix
	 */
	@Override
	public DependencyMatrix build(EntityRepo entityRepo,List<String> typeFilter) {
		DependencyMatrix dependencyMatrix = new DependencyMatrix(typeFilter);
		Iterator<Entity> iterator = entityRepo.entityIterator();
		System.out.println("Start create dependencies matrix....");
		while(iterator.hasNext()) {
			Entity entity = iterator.next();
			if (!entity.inScope()) continue;
			if (isStructureEntityType(entity)){
				String name = entity.getQualifiedName() + "|" + entity.getClass().getSimpleName().replace("Entity","");
        		dependencyMatrix.addNode(name,entity.getId());
        	}
			
        	int fileEntityFrom = getStructureEntityIdNoException(entity);
        	if (fileEntityFrom==-1) continue;
        	for (Relation relation:entity.getRelations()) {
        		Entity relatedEntity = relation.getEntity();
        		if (relatedEntity==null) continue;
        		if (relatedEntity instanceof CandidateTypes) {
        			List<TypeEntity> candidateTypes = ((CandidateTypes)relatedEntity).getCandidateTypes();
        			for (TypeEntity candidateType:candidateTypes) {
    	        		if (candidateType.getId()>=0) {
    	        			int fileEntityTo = getStructureEntityIdNoException(candidateType);
    	        			if (fileEntityTo!=-1) {
								DependencyDetail detail = buildDescription(entity,candidateType,relation.getFromLine());
								dependencyMatrix.addDependency(relation.getType(), fileEntityFrom,fileEntityTo,1,detail);
    	        			}
    	        		}
        			}
        		}else {
	        		if (relatedEntity.getId()>=0) {
	        			int fileEntityTo = getStructureEntityIdNoException(relatedEntity);
	        			if (fileEntityTo!=-1) {
							DependencyDetail detail = buildDescription(entity, relatedEntity, relation.getFromLine());
							dependencyMatrix.addDependency(relation.getType(), fileEntityFrom,fileEntityTo,1,detail);
	        			}
	        		}
        		}
        	}
        }
		System.out.println("Finish create dependencies matrix....");

		return dependencyMatrix;
	}



	private boolean isStructureEntityType(Entity entity) {
		if (entity instanceof FileEntity) return false;
		if (entity instanceof TypeEntity) return true; //package included
		if (entity instanceof VarEntity && entity.getParent() instanceof TypeEntity) return true;
		if (entity instanceof FunctionEntity) return true;
		return false;
	}

	private int getStructureEntityIdNoException(Entity entity) {
		Entity ancestor = getAncestorOfType(entity);
		if (ancestor==null) {
			return -1;
		}
		if (!ancestor.inScope()) return -1;
		return ancestor.getId();
	}

	public Entity getAncestorOfType(Entity fromEntity) {
		while(fromEntity!=null) {
			if (isStructureEntityType(fromEntity))
				return fromEntity;
			if (fromEntity.getParent()==null) return null;
			fromEntity = fromEntity.getParent();
		}
		return null;
	}
}
