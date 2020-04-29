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

import java.util.Iterator;
import java.util.List;

import depends.entity.CandidateTypes;
import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.PackageEntity;
import depends.entity.TypeEntity;
import depends.entity.repo.EntityRepo;
import depends.matrix.core.DependencyMatrix;
import depends.relations.Relation;

public class FileDependencyGenerator extends DependencyGenerator{
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
			if (entity instanceof FileEntity){
				String name = stripper.stripFilename(entity.getDisplayName());
				name = filenameWritter.reWrite(name);
        		dependencyMatrix.addNode(name,entity.getId());
        	}
        	int fileEntityFrom = getFileEntityIdNoException(entityRepo, entity);
        	if (fileEntityFrom==-1) continue;
        	for (Relation relation:entity.getRelations()) {
        		Entity relatedEntity = relation.getEntity();
        		if (relatedEntity==null) continue;
        		if (relatedEntity instanceof CandidateTypes) {
        			List<TypeEntity> candidateTypes = ((CandidateTypes)relatedEntity).getCandidateTypes();
        			for (TypeEntity candidateType:candidateTypes) {
    	        		if (candidateType.getId()>=0) {
    	        			int fileEntityTo = getFileEntityIdNoException(entityRepo,candidateType);
    	        			if (fileEntityTo!=-1) {
    	        				dependencyMatrix.addDependency(relation.getType(), fileEntityFrom,fileEntityTo,1,buildDescription(entity,candidateType));
    	        			}
    	        		}
        			}
        		}else {
	        		if (relatedEntity.getId()>=0) {
	        			int fileEntityTo = getFileEntityIdNoException(entityRepo,relatedEntity);
	        			if (fileEntityTo!=-1) {
	        				dependencyMatrix.addDependency(relation.getType(), fileEntityFrom,fileEntityTo,1,buildDescription(entity,relatedEntity));
	        			}
	        		}
        		}
        	}
        }
		System.out.println("Finish create dependencies matrix....");

		return dependencyMatrix;
	}

	private int getFileEntityIdNoException(EntityRepo entityRepo, Entity entity) {
		Entity ancestor = entity.getAncestorOfType(FileEntity.class);
		if (ancestor==null) {
			return -1;
		}
		if (!ancestor.inScope()) return -1;
		return ancestor.getId();
	}

}
