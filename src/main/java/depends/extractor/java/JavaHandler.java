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
	public void foundVarDefintion(String type, List<String> vars) {
		for (String var:vars) {
			foundVarDefintion(type,var);
		}
	}
	public void foundVarDefintion(String type, String var) {
		context().addVar(type,var);
		addVars(context().resolveTypeNameRef(type),var);		
	}
	
}

