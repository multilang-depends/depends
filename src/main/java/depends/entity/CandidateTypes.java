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

package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import depends.relations.Inferer;
import depends.relations.Relation;

public class CandidateTypes extends TypeEntity {
	private List<TypeEntity> candidateTypes;

	public CandidateTypes() {
		candidateTypes = new ArrayList<>();
	}
	public CandidateTypes(List<TypeEntity> candidateTypes, Integer id) {
		super(GenericName.build("candidateTypes"), null, id);
		this.candidateTypes = candidateTypes;
	}

	public List<TypeEntity> getCandidateTypes() {
		return candidateTypes;
	}

 	@Override
	public Collection<TypeEntity> getInheritedTypes() {
 		List<TypeEntity> result = new ArrayList<>();
		for (TypeEntity type:candidateTypes) {
			result.addAll(type.getInheritedTypes());
		}
		return result;
	}
	
 	@Override
	public Collection<TypeEntity> getImplementedTypes() {
 		List<TypeEntity> result = new ArrayList<>();
		for (TypeEntity type:candidateTypes) {
			result.addAll(type.getImplementedTypes());
		}
		return result;
	}
 	
	@Override
	public ArrayList<FunctionEntity> getFunctions() {
 		ArrayList<FunctionEntity> result = new ArrayList<>();
		for (TypeEntity type:candidateTypes) {
			result.addAll(type.getFunctions());
		}
		return result;
	}
	
 	@Override
 	public TypeEntity getInheritedType() {
		return inheritedType;
	}
 	@Override
 	public List<Entity> lookupFunctionInVisibleScope(GenericName functionName) {
 		List<Entity> functions = new ArrayList<>();
 		for (TypeEntity type:candidateTypes) {
 			List<Entity> f = type.lookupFunctionInVisibleScope(functionName);
			if (f!=null) {
				functions.addAll(f);
			}
		}
 		if (functions.size()==0)
 			return null;
 		return functions;
	}
 	
	@Override
	public Entity lookupVarInVisibleScope(GenericName varName) {
		for (TypeEntity type:candidateTypes) {
			Entity v = type.lookupVarInVisibleScope(varName);
			if (v!=null) return v;
		}
 		return null;	
	}
	
	
	@Override
	public VarEntity lookupVarLocally(String varName) {
		for (TypeEntity type:candidateTypes) {
			VarEntity v = type.lookupVarLocally(varName);
			if (v!=null) return v;
		}
		return null;
	}
	
 	@Override
 	public TypeEntity getType() {
 		if (candidateTypes.size()>0) return candidateTypes.get(0);
		return null;
	}

	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		System.err.println("error: inferLocalLevelEntities should not been invoked");
		super.inferLocalLevelEntities(inferer);
	}

	@Override
	public void addImplements(GenericName typeName) {
		System.err.println("error: addImplements should not been invoked");
		super.addImplements(typeName);
	}

	@Override
	public void addExtends(GenericName typeName) {
		System.err.println("error: addExtends should not been invoked");
		super.addExtends(typeName);
	}

	@Override
	public void addVar(VarEntity var) {
		System.err.println("error: addVar should not been invoked");
		super.addVar(var);
	}

	@Override
	public ArrayList<VarEntity> getVars() {
		System.err.println("error: getVars should not been invoked");
		return super.getVars();
	}

	@Override
	public void addFunction(FunctionEntity functionEntity) {
		System.err.println("error: addFunction should not been invoked");
		super.addFunction(functionEntity);
	}

	@Override
	public HashMap<Object, Expression> expressions() {
		System.err.println("error: expressions should not been invoked");
		return super.expressions();
	}

	@Override
	public void addExpression(Object key, Expression expression) {
		System.err.println("error: addExpression should not been invoked");
		super.addExpression(key, expression);
	}

	@Override
	public void resolveExpressions(Inferer inferer) {
		System.err.println("error: resolveExpressions should not been invoked");
		super.resolveExpressions(inferer);
	}


	@Override
	public void addMixin(GenericName moduleName) {
		System.err.println("error: addMixin should not been invoked");
		super.addMixin(moduleName);
	}

	@Override
	public Collection<ContainerEntity> getResolvedMixins() {
		System.err.println("error: getResolvedMixins should not been invoked");
		return super.getResolvedMixins();
	}

	@Override
	public void addAnnotation(GenericName name) {
		System.err.println("error: addAnnotation should not been invoked");
		super.addAnnotation(name);
	}


	@Override
	public Collection<Entity> getResolvedTypeParameters() {
		System.err.println("error: getResolvedTypeParameters should not been invoked");
		return super.getResolvedTypeParameters();
	}

	@Override
	public Collection<Entity> getResolvedAnnotations() {
		System.err.println("error: getResolvedAnnotations should not been invoked");
		return super.getResolvedAnnotations();
	}

	@Override
	public boolean isGenericTypeParameter(GenericName rawType) {
		System.err.println("error: isGenericTypeParameter should not been invoked");
		return super.isGenericTypeParameter(rawType);
	}

	@Override
	protected Collection<Entity> identiferToEntities(Inferer inferer, Collection<GenericName> identifiers) {
		System.err.println("error: identiferToTypes should not been invoked");
		return super.identiferToEntities(inferer, identifiers);
	}

	@Override
	public GenericName getRawName() {
		return super.getRawName();
	}

	@Override
	public Integer getId() {
		return super.getId();
	}

	@Override
	public void addRelation(Relation relation) {
		System.err.println("error: addRelation should not been invoked");
		super.addRelation(relation);
	}

	@Override
	public ArrayList<Relation> getRelations() {
		System.err.println("error: getRelations should not been invoked");
		return super.getRelations();
	}

	@Override
	public void addChild(Entity child) {
		System.err.println("error: addChild should not been invoked");
		super.addChild(child);
	}

	@Override
	public Entity getParent() {
		return null;
	}

	@Override
	public void setParent(Entity parent) {
		System.err.println("error: setParent should not been invoked");
		super.setParent(parent);
	}

	@Override
	public Collection<Entity> getChildren() {
		List<Entity> children = new ArrayList<>();
		for (Entity entity:this.candidateTypes) {
			children.addAll(entity.getChildren());
		}
		return children;
	}

	@Override
	public void setQualifiedName(String qualifiedName) {
		System.err.println("error: setQualifiedName should not been invoked");
		super.setQualifiedName(qualifiedName);
	}

	@Override
	public void setRawName(GenericName rawName) {
		System.err.println("error: setRawName should not been invoked");
		super.setRawName(rawName);
	}

	@Override
	public String getQualifiedName(boolean overrideFileWithPackage) {
		System.err.println("error: getQualifiedName should not been invoked");
		return super.getQualifiedName(overrideFileWithPackage);
	}

	
	@Override
	public Entity getAncestorOfType(@SuppressWarnings("rawtypes") Class classType) {
		return null;
	}

	@Override
	public void inferEntities(Inferer inferer) {
		System.err.println("error: inferEntities should not been invoked");
		super.inferEntities(inferer);
	}

	@Override
	public String getDisplayName() {
		System.err.println("error: getDisplayName should not been invoked");
		return super.getDisplayName();
	}
	@Override
	public Entity getByName(String name, HashSet<Entity> searched) {
		Entity entity = super.getByName(name, searched);
		if (entity!=null) return entity;
		for (TypeEntity type:getCandidateTypes()) {
			if (searched.contains(type)) continue;
			Entity e = type.getByName(name, searched);
			if (e !=null) return e;
		}
		return null;
	}
	
	
 	
}
