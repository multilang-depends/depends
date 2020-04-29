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

package depends.relations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import depends.deptypes.DependencyType;
import depends.entity.AliasEntity;
import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.Expression;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.FunctionEntityImpl;
import depends.entity.FunctionEntityProto;
import depends.entity.MultiDeclareEntities;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.AbstractLangProcessor;

public class RelationCounter {

	private Collection<Entity> entities;
	private Inferer inferer;
	private EntityRepo repo;
	private boolean callAsImpl;
	private AbstractLangProcessor langProcessor;

	public RelationCounter(Collection<Entity> iterator, Inferer inferer, EntityRepo repo, boolean callAsImpl, AbstractLangProcessor langProcessor) {
		this.entities = iterator;
		this.inferer = inferer;
		this.repo = repo;
		this.callAsImpl = callAsImpl;
		this.langProcessor = langProcessor;
	}
	
	public void computeRelations() {
		entities.forEach(entity->
		computeRelationOf(entity));
	}

	private void computeRelationOf(Entity entity) {
		if (!entity.inScope())
			return;
		if (entity instanceof FileEntity) {
			computeImports((FileEntity)entity);
		}
		else if (entity instanceof FunctionEntity) {
			computeFunctionRelations((FunctionEntity)entity);
		}
		else if (entity instanceof TypeEntity) {
			computeTypeRelations((TypeEntity)entity);
		}
		if (entity instanceof ContainerEntity) {
			computeContainerRelations((ContainerEntity)entity);
		}
		entity.getChildren().forEach(child->computeRelationOf(child));
	}

	

	private void computeContainerRelations(ContainerEntity entity) {
		for (VarEntity var:entity.getVars()) {
			if (var.getType()!=null)
				entity.addRelation(buildRelation(DependencyType.CONTAIN,var.getType()));
			for (Entity type:var.getResolvedTypeParameters()) {
				var.addRelation(buildRelation(DependencyType.PARAMETER,type));
			}
		}
		for (Entity type:entity.getResolvedAnnotations()) {
			entity.addRelation(buildRelation(DependencyType.ANNOTATION,type));
		}
		for (Entity type:entity.getResolvedTypeParameters()) {
			entity.addRelation(buildRelation(DependencyType.USE,type));
		}
		for (ContainerEntity mixin:entity.getResolvedMixins()) {
			entity.addRelation(buildRelation(DependencyType.MIXIN,mixin));
		}
		
		entity.reloadExpression(repo);
		if (!inferer.isEagerExpressionResolve())
		{
			entity.resolveExpressions(inferer);
		}
		for (Expression expression:entity.expressionList()){
			if (expression.isStatement()) {
				continue;
			}
			Entity referredEntity = expression.getReferredEntity();
			addRelationFromExpression(entity, expression, referredEntity);
		}
		entity.clearExpressions();
	}

	private void addRelationFromExpression(ContainerEntity entity, Expression expression, Entity referredEntity) {
		
		if (referredEntity==null) {
			return;
		}

		if (referredEntity instanceof MultiDeclareEntities) {
			for (Entity e:((MultiDeclareEntities)referredEntity).getEntities()) {
				addRelationFromExpression(entity,expression,e);
			}
			return;
		}
		
		boolean matched = false;
		if (expression.isCall()) {
			/* if it is a FunctionEntityProto, add Relation to all Impl Entities*/
			if (callAsImpl && referredEntity instanceof FunctionEntityProto) {
				Entity multiDeclare = repo.getEntity(referredEntity.getQualifiedName());
				if (multiDeclare instanceof MultiDeclareEntities) {
					MultiDeclareEntities m = (MultiDeclareEntities)multiDeclare;
					List<Entity> entities = m.getEntities().stream().filter(item->(item instanceof FunctionEntityImpl))
					.collect(Collectors.toList());
					for (Entity e:entities) {
						entity.addRelation(buildRelation(DependencyType.IMPLLINK,e));
						matched = true;
					}
				}
			}
			entity.addRelation(buildRelation(DependencyType.CALL,referredEntity));
			matched = true;

		}
		if (expression.isCreate()) {
			entity.addRelation(buildRelation(DependencyType.CREATE,referredEntity));
			matched = true;
		}
		if (expression.isThrow()) {
			entity.addRelation(buildRelation(DependencyType.THROW,referredEntity));
			matched = true;
		}
		if (expression.isCast()) { 
			entity.addRelation(buildRelation(DependencyType.CAST,referredEntity));
			matched = true;
		}
		if (!matched)  {
			if (callAsImpl && repo.getEntity(referredEntity.getQualifiedName()) instanceof MultiDeclareEntities &&
					(referredEntity instanceof VarEntity ||referredEntity instanceof FunctionEntity)) {
				MultiDeclareEntities m =  (MultiDeclareEntities)(repo.getEntity(referredEntity.getQualifiedName()));
				for (Entity e:m.getEntities()) {
					if (e==referredEntity) {
						entity.addRelation(buildRelation(DependencyType.USE,e));
					}else {
						entity.addRelation(buildRelation(DependencyType.IMPLLINK,e));
					}
					matched = true;
				}
			}
			else {
				entity.addRelation(buildRelation(DependencyType.USE,referredEntity));
			}
		}
	}

	private Relation buildRelation(String type, Entity referredEntity) {
		if (referredEntity instanceof AliasEntity) {
			AliasEntity alias = ((AliasEntity) referredEntity);
			if (alias.getReferToEntityTillNoAlias()!=null) {
				referredEntity = alias.getReferToEntityTillNoAlias();
			}
		}
		if (this.langProcessor==null)
			return new Relation(type,referredEntity);
		return new Relation(langProcessor.getRelationMapping(type),referredEntity);
	}

	private void computeTypeRelations(TypeEntity type) {
		for (TypeEntity superType:type.getInheritedTypes()) {
			type.addRelation(buildRelation(DependencyType.INHERIT,superType));
		}
		for (TypeEntity interfaceType:type.getImplementedTypes()) {
			type.addRelation(buildRelation(DependencyType.IMPLEMENT,interfaceType));
		}
	}

	private void computeFunctionRelations(FunctionEntity func) {
		for (Entity returnType:func.getReturnTypes()) {
			func.addRelation(buildRelation(DependencyType.RETURN,returnType.getActualReferTo()));
		}
		for (VarEntity parameter:func.getParameters()) {
			if (parameter.getType()!=null) 
				func.addRelation(buildRelation(DependencyType.PARAMETER,parameter.getActualReferTo()));
		}
		for (Entity throwType:func.getThrowTypes()) {
			func.addRelation(buildRelation(DependencyType.THROW,throwType));
		}
		for (Entity type:func.getResolvedTypeParameters()) {
			func.addRelation(buildRelation(DependencyType.PARAMETER,type));
		}
		if (func instanceof FunctionEntityImpl) {
			FunctionEntityImpl funcImpl = (FunctionEntityImpl)func;
			if(funcImpl.getImplemented()!=null) {
				func.addRelation(buildRelation(DependencyType.IMPLEMENT,funcImpl.getImplemented()));
			}
		}
	}

	private void computeImports(FileEntity file) {
		Collection<Entity> imports = file.getImportedRelationEntities();
		if (imports==null) return;
		for (Entity imported:imports) {
			if (imported instanceof FileEntity)
			{
				if (((FileEntity)imported).isInProjectScope())
					file.addRelation(buildRelation(DependencyType.IMPORT,imported));
			}else {
				file.addRelation(buildRelation(DependencyType.IMPORT,imported));
			}
		}
	}

}
