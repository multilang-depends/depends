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

import depends.entity.CandidateTypes;
import depends.entity.Entity;
import depends.entity.EntityNameBuilder;
import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import depends.matrix.core.DependencyDetail;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.LocationInfo;
import depends.matrix.transform.OrderedMatrixGenerator;
import depends.relations.Relation;
import multilang.depends.util.file.path.EmptyFilenameWritter;
import multilang.depends.util.file.path.FilenameWritter;
import multilang.depends.util.file.strip.EmptyLeadingNameStripper;
import multilang.depends.util.file.strip.ILeadingNameStrippper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static depends.deptypes.DependencyType.POSSIBLE_DEP;

public abstract class DependencyGenerator {

	private static Logger logger = LoggerFactory.getLogger(DependencyGenerator.class);

	public DependencyMatrix identifyDependencies(EntityRepo entityRepo, List<String> typeFilter) {
		System.out.println("dependencie data generating...");
		DependencyMatrix dependencyMatrix = build(entityRepo, typeFilter);
		entityRepo.clear();
		System.out.println("reorder dependency matrix...");
		dependencyMatrix = new OrderedMatrixGenerator(dependencyMatrix).build();
		System.out.println("Dependencies data generating done successfully...");
		logger.info("Dependencies data generating done successfully...");
		return dependencyMatrix;
	}

	/**
	 * Build the dependency matrix (without re-mapping file id)
	 * @param entityRepo which contains entities and relations
	 * @return the generated dependency matrix
	 */
	public DependencyMatrix build(EntityRepo entityRepo,List<String> typeFilter) {
		DependencyMatrix dependencyMatrix = new DependencyMatrix(typeFilter);
		Iterator<Entity> iterator = entityRepo.entityIterator();
		System.out.println("Start create dependencies matrix....");
		while(iterator.hasNext()) {
			Entity entity = iterator.next();
			if (!entity.inScope()) continue;
			if (outputLevelMatch(entity)){
				dependencyMatrix.addNode(nameOf(entity),entity.getId());
			}
			int entityFrom = upToOutputLevelEntityId(entityRepo, entity);
			if (entityFrom==-1) continue;
			for (Relation relation:entity.getRelations()) {
				Entity relatedEntity = relation.getEntity();
				if (relatedEntity==null) continue;
				List<Entity> relatedEntities = expandEntity(relatedEntity);
				String possibleDependencyFlag = relation.possible()? POSSIBLE_DEP :"";
				relatedEntities.forEach(theEntity->{
					if (theEntity.getId()>=0) {
						int entityTo = upToOutputLevelEntityId(entityRepo,theEntity);
						if (entityTo!=-1) {
							DependencyDetail detail = buildDescription(entity, theEntity, relation.getFromLine());
							detail = rewriteDetail(detail);
							dependencyMatrix.addDependency(relation.getType()+possibleDependencyFlag, entityFrom,entityTo,1,detail);
						}
					}
				});
			}
		}
		System.out.println("Finish create dependencies matrix....");
		return dependencyMatrix;
	}

	private List<Entity> expandEntity(Entity relatedEntity) {
		List<Entity> entities = new ArrayList<>();
		if (relatedEntity instanceof CandidateTypes) {
			entities = Collections.unmodifiableList((List) ((CandidateTypes) relatedEntity).getCandidateTypes());
		}else {
			entities.add(relatedEntity);
		}
		return entities;
	}

	private DependencyDetail rewriteDetail(DependencyDetail detail) {
		if (detail==null) return null;
		String srcFile = filenameWritter.reWrite(
				stripper.stripFilename(detail.getSrc().getFile())
		);
		String dstFile = filenameWritter.reWrite(
				stripper.stripFilename(detail.getDest().getFile()));
		return new DependencyDetail(
				new LocationInfo(detail.getSrc().getObject(),
						srcFile, detail.getSrc().getLineNumber())
				,
				new LocationInfo(detail.getDest().getObject(),
						dstFile, detail.getDest().getLineNumber()));
	}

	protected abstract int upToOutputLevelEntityId(EntityRepo entityRepo, Entity entity);

	protected abstract String nameOf(Entity entity);

	protected abstract boolean outputLevelMatch(Entity entity);

	protected ILeadingNameStrippper stripper = new EmptyLeadingNameStripper();
	protected FilenameWritter filenameWritter = new EmptyFilenameWritter();
	private boolean generateDetail = false;
	
	public void setLeadingStripper(ILeadingNameStrippper stripper) {
		this.stripper = stripper;
	}
	protected DependencyDetail buildDescription(Entity fromEntity, Entity toEntity, Integer fromLineNumber) {
		if (!generateDetail) return null;
		String fromObject = EntityNameBuilder.build(fromEntity);
		String toObject = EntityNameBuilder.build(toEntity);

		Entity fromFile = fromEntity.getAncestorOfType(FileEntity.class);
		Entity toFile = toEntity.getAncestorOfType(FileEntity.class);

		return new DependencyDetail(
				new LocationInfo(stripper.stripFilename(fromObject),stripper.stripFilename(fromFile.getQualifiedName()),fromLineNumber),
				new LocationInfo(stripper.stripFilename(toObject),stripper.stripFilename(toFile.getQualifiedName()),toEntity.getLine()));
	}
	public void setFilenameRewritter(FilenameWritter filenameWritter) {
		this.filenameWritter = filenameWritter;
	}
	public void setGenerateDetail(boolean generateDetail) {
		this.generateDetail = generateDetail;
	}
}
