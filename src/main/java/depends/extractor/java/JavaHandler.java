package depends.extractor.java;

import java.util.Collection;
import java.util.List;

import depends.entity.repo.EntityRepo;
import depends.extractor.GenericHandler;

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
		addImportRelation(importedTypeOrPackage);
	}
	
	public void foundExtends(String superClassName) {
		superClassName = context().resolveTypeNameRef(superClassName);
		addInheritRelation(superClassName);
	}

	public void foundReturn(String returnTypeName) {
		if (returnTypeName.equals("void")) return;
		if (returnTypeName==null) return;
		returnTypeName = context().resolveTypeNameRef(returnTypeName);
		addReturnRelation(returnTypeName);
	}

	public void foundImplements(String interfaceName) {
		interfaceName = context().resolveTypeNameRef(interfaceName);
		addImplementRelation(interfaceName);
	}
	
	
	public void foundMethodDeclarator(String methodName, Collection<String> parameterTypes) {
		addEntity(context().newFunctionEntity(methodName));
		for (String parameterType:parameterTypes) {
			addParameterRelation(context().resolveTypeNameRef(parameterType));
		}
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
			addSetRelation(type);
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
		addSetRelation(type);

		//e.g. type.x.y.z = 1; x,y,z are also have relation SET
		for (int i=1;i<varNamePath.size();i++) {
			type = context().inferType(type,varNamePath.get(i));
			if (type==null) {
				break;
			}
			addSetRelation(type);
		}
	}
	public void foundVariableSetOfTypes(String type, String var) {
		type = context().resolveTypeNameRef(type);
		addSetRelation(type);
		if (var.isEmpty()) return;
		type = context().inferType(type,var);
		if (type==null) return;
		addSetRelation(type);
	}
	/**
	 * Provide a default version of var definition (always add define relation)
	 * @param calculateType
	 * @param extractVarList
	 */
	public void foundVarDefintion(String type, List<String> vars) {
		this.foundVarDefintion(type, vars, true);
	}
	
	
}

