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
			else if (entity instanceof FunctionEntity) {
				computeFunctionRelations((FunctionEntity)entity);
			}
			else if (entity instanceof TypeEntity) {
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
			for (Entity type:var.getResolvedTypeParameters()) {
				entity.addRelation(new Relation(DependencyType.USE,type));
			}
		}
		for (Entity type:entity.getResolvedAnnotations()) {
			entity.addRelation(new Relation(DependencyType.ANNOTATION,type));
		}
		for (Entity type:entity.getResolvedTypeParameters()) {
			entity.addRelation(new Relation(DependencyType.USE,type));
		}
		for (ContainerEntity mixin:entity.getResolvedMixins()) {
			entity.addRelation(new Relation(DependencyType.MIXIN,mixin));
		}
		
		HashSet<Entity> usedEntities = new HashSet<>();
		for (Expression expression:entity.expressions().values()){
			if (expression.isStatement) {
				continue;
			}
			Entity referredEntity = expression.getReferredEntity();
			if (referredEntity==null) {
				continue;
			}
			if (expression.isCall) {
				entity.addRelation(new Relation(DependencyType.CALL,referredEntity));
			}
			else if (expression.isCreate) {
				entity.addRelation(new Relation(DependencyType.CREATE,referredEntity));
			}
			else if (expression.isThrow) {
				entity.addRelation(new Relation(DependencyType.THROW,referredEntity));
			}
			else if (expression.isCast) { 
				entity.addRelation(new Relation(DependencyType.CAST,referredEntity));
			}
			else  {
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
		for (Entity returnType:func.getReturnTypes()) {
			func.addRelation(new Relation(DependencyType.RETURN,returnType));
		}
		for (VarEntity parameter:func.getParameters()) {
			if (parameter.getType()!=null) 
				func.addRelation(new Relation(DependencyType.PARAMETER,parameter.getType()));
		}
		for (Entity throwType:func.getThrowTypes()) {
			func.addRelation(new Relation(DependencyType.THROW,throwType));
		}
	}

	private void computeImports(FileEntity file) {
		Collection<Entity> imports = file.getImportedRelationEntities();
		if (imports==null) return;
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
