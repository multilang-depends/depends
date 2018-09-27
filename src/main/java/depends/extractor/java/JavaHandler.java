package depends.extractor.java;

import java.util.Collection;
import java.util.List;

import depends.deptypes.DependencyType;
import depends.entity.repo.EntityRepo;
import depends.extractor.GenericHandler;
import depends.util.Tuple;

public class JavaHandler extends GenericHandler {
	public JavaHandler(EntityRepo entityRepo){
		super(entityRepo);
	}
    ///Entities 
	public void startFile(String fileName) {
        addEntity(context().newFileEntity(fileName));
	}

	public void foundPackageDeclaration(String packageName) {
		addEntity(context().newPackageEntity(packageName));
	}

	public void foundClassOrInterface(String classOrInterfaceName) {
		addEntity(context().newClassInterface(classOrInterfaceName));
	}
	public void exitLastedEntity() {
		context().popEntity();
	}
    ///Relations 
	public void foundImport(String importedTypeOrPackage) {
		context().newImport(importedTypeOrPackage);
		addRelation(importedTypeOrPackage,DependencyType.RELATION_IMPORT);
	}
	
	public void foundExtends(String superClassName) {
		superClassName = context().resolveTypeNameRef(superClassName);
		addRelation(superClassName,DependencyType.RELATION_INHERIT);
	}

	public void foundTypeParametes(String className) {
		className = context().resolveTypeNameRef(className);
		addRelation(className,DependencyType.RELATION_USE);
	}
	public void foundThrows(String className) {
		className = context().resolveTypeNameRef(className);
		addRelation(className,DependencyType.RELATION_USE);
	}
	
	private void foundReturn(String returnTypeName) {
		if (returnTypeName==null) return;
		if (returnTypeName.equals("void")) return;
		returnTypeName = context().resolveTypeNameRef(returnTypeName);
		addRelation(returnTypeName,DependencyType.RELATION_RETURN);
	}

	public void foundImplements(String interfaceName) {
		interfaceName = context().resolveTypeNameRef(interfaceName);
		addRelation(interfaceName,DependencyType.RELATION_IMPLEMENT);
	}
	
	
	public void foundMethodDeclarator(String methodName, Collection<String> parameterTypes, String resultType) {
		addEntity(context().newFunctionEntity(methodName,resultType));
		if (parameterTypes==null) return;
		for (String parameterType:parameterTypes) {
			addRelation(context().resolveTypeNameRef(parameterType),DependencyType.RELATION_PARAMETER);
		}
		foundReturn(resultType);
	}
	public void foundVarDefintion(String type, List<String> vars, boolean isNewRelation) {
		for (String var:vars) {
			foundVarDefintion(type,var,isNewRelation);
		}
	}
	public void foundVarDefintion(String type, String var, boolean isNewRelation) {
		context().addVar(context().resolveTypeNameRef(type),var);
		if (isNewRelation)
			addVars(context().resolveTypeNameRef(type),var);		
	}
	
	public void foundVariableSet(String varName) {
		String type = context().inferType(varName);
		if (type!=null) {
			addRelation(type,DependencyType.RELATION_SET);
		}
	}
	
	public void foundAnnotationInUse(String name) {
		String type = context().inferType(name);
		if (type!=null) {
			addRelation(type,DependencyType.RELATION_USE);
		}
	}
	
    /**
     * Handle the variable paths like a.x
     * @param varNamePath
     */
	public void foundVariableSet(List<String> varNamePath) {
		if (varNamePath.size()==0) return;
		if (varNamePath.size()==1) {
			foundVariableSet(varNamePath.get(0));
			return;
		}
		String type = context().inferType(varNamePath.get(0));
		if (type==null) return;
		addRelation(type,DependencyType.RELATION_SET);

		//e.g. type.x.y.z = 1; x,y,z are also have relation SET
		for (int i=1;i<varNamePath.size();i++) {
			type = context().inferVarType(type,varNamePath.get(i));
			if (type==null) {
				break;
			}
			addRelation(type,DependencyType.RELATION_SET);
		}
	}

	public void foundVariableSetOfTypes(String type, String var) {
		type = context().resolveTypeNameRef(type);
		addRelation(type,DependencyType.RELATION_SET);
		if (var.isEmpty()) return;
		type = context().inferVarType(type,var);
		if (type==null) return;
		addRelation(type,DependencyType.RELATION_SET);
	}
	/**
	 * Provide a default version of var definition (always add define relation)
	 * @param calculateType
	 * @param extractVarList
	 */
	public void foundVarDefintion(String type, List<String> vars) {
		this.foundVarDefintion(type, vars, true);
	}
	public void foundVarDefintion(Collection<Tuple<String, String>> varList, boolean isNewRelation) {
		if (varList==null) return;
		for (Tuple<String, String> var:varList) {
			foundVarDefintion(var.x,var.y,isNewRelation);
		}
	}
	
}

