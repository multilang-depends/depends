package depends.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import depends.relations.Inferer;
import depends.relations.Relation;

public class CandidateTypes extends TypeEntity {
	private List<TypeEntity> candidateTypes;

	public CandidateTypes(List<TypeEntity> candidateTypes) {
		super("candidateTypes", null, -1);
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
 	public FunctionEntity lookupFunctionInVisibleScope(String functionName) {
 		for (TypeEntity type:candidateTypes) {
			FunctionEntity f = type.lookupFunctionInVisibleScope(functionName);
			if (f!=null) return f;
		}
 		return null;
	}
 	
	@Override
	public VarEntity lookupVarInVisibleScope(String varName) {
		for (TypeEntity type:candidateTypes) {
			VarEntity v = type.lookupVarInVisibleScope(varName);
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
	public void addImplements(String typeName) {
		System.err.println("error: addImplements should not been invoked");
		super.addImplements(typeName);
	}

	@Override
	public void addExtends(String typeName) {
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
	public TypeEntity getLastExpressionType() {
		System.err.println("error: getLastExpressionType should not been invoked");
		return super.getLastExpressionType();
	}

	@Override
	public void addMixin(String moduleName) {
		System.err.println("error: addMixin should not been invoked");
		super.addMixin(moduleName);
	}

	@Override
	public Collection<ContainerEntity> getResolvedMixins() {
		System.err.println("error: getResolvedMixins should not been invoked");
		return super.getResolvedMixins();
	}

	@Override
	public void addTypeParameter(List<String> typeArguments) {
		System.err.println("error: addTypeParameter should not been invoked");
		super.addTypeParameter(typeArguments);
	}

	@Override
	public void addAnnotation(String name) {
		System.err.println("error: addAnnotation should not been invoked");
		super.addAnnotation(name);
	}

	@Override
	public void addTypeParameter(String typeName) {
		System.err.println("error: addTypeParameter should not been invoked");
		super.addTypeParameter(typeName);
	}

	@Override
	public Collection<TypeEntity> getResolvedTypeParameters() {
		System.err.println("error: getResolvedTypeParameters should not been invoked");
		return super.getResolvedTypeParameters();
	}

	@Override
	public Collection<TypeEntity> getResolvedAnnotations() {
		System.err.println("error: getResolvedAnnotations should not been invoked");
		return super.getResolvedAnnotations();
	}

	@Override
	public boolean isGenericTypeParameter(String rawType) {
		System.err.println("error: isGenericTypeParameter should not been invoked");
		return super.isGenericTypeParameter(rawType);
	}

	@Override
	protected Collection<TypeEntity> identiferToTypes(Inferer inferer, Collection<String> identifiers) {
		System.err.println("error: identiferToTypes should not been invoked");
		return super.identiferToTypes(inferer, identifiers);
	}

	@Override
	public String getRawName() {
		System.err.println("error: getRawName should not been invoked");
		return super.getRawName();
	}

	@Override
	public int getId() {
		System.err.println("error: getId should not been invoked");
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
		System.err.println("error: getParent should not been invoked");
		return super.getParent();
	}

	@Override
	public void setParent(Entity parent) {
		System.err.println("error: setParent should not been invoked");
		super.setParent(parent);
	}

	@Override
	public Collection<Entity> getChildren() {
		System.err.println("error: getChildren should not been invoked");
		return super.getChildren();
	}

	@Override
	public void setQualifiedName(String qualifiedName) {
		System.err.println("error: setQualifiedName should not been invoked");
		super.setQualifiedName(qualifiedName);
	}

	@Override
	public void setRawName(String rawName) {
		System.err.println("error: setRawName should not been invoked");
		super.setRawName(rawName);
	}

	@Override
	public String getQualifiedName(boolean overrideFileWithPackage) {
		System.err.println("error: getQualifiedName should not been invoked");
		return super.getQualifiedName(overrideFileWithPackage);
	}

	
	@Override
	public Entity getAncestorOfType(Class classType) {
		System.err.println("error: getAncestorOfType should not been invoked");
		return super.getAncestorOfType(classType);
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
 	
}
