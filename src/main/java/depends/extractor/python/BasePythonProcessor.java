package depends.extractor.python;

import static depends.deptypes.DependencyType.ANNOTATION;
import static depends.deptypes.DependencyType.CALL;
import static depends.deptypes.DependencyType.CONTAIN;
import static depends.deptypes.DependencyType.CREATE;
import static depends.deptypes.DependencyType.IMPLLINK;
import static depends.deptypes.DependencyType.IMPORT;
import static depends.deptypes.DependencyType.INHERIT;
import static depends.deptypes.DependencyType.PARAMETER;
import static depends.deptypes.DependencyType.RETURN;
import static depends.deptypes.DependencyType.SET;
import static depends.deptypes.DependencyType.THROW;
import static depends.deptypes.DependencyType.USE;

import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.relations.ImportLookupStrategy;

public abstract class BasePythonProcessor extends AbstractLangProcessor{
	private PythonImportLookupStrategy importedLookupStrategy;

	public BasePythonProcessor(boolean eagerExpressionResolve) {
		super(eagerExpressionResolve);
	}

	@Override
	public String[] fileSuffixes() {
		return new String[] {".py"};
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		importedLookupStrategy = new PythonImportLookupStrategy();
		return this.importedLookupStrategy;
	}


	@Override
	public BuiltInType getBuiltInType() {
		return new PythonBuiltInType();
	}

	@Override
	public List<String> supportedRelations() {
		/* To be check: is python support implemenent? 
		 * should no cast supported.
		 * */
//		depedencyTypes.add(IMPLEMENT);
//		depedencyTypes.add(CAST);

		ArrayList<String> depedencyTypes = new ArrayList<>();
		depedencyTypes.add(ANNOTATION);
		depedencyTypes.add(IMPORT);
		depedencyTypes.add(CONTAIN);
		depedencyTypes.add(INHERIT);
		depedencyTypes.add(CALL);
		depedencyTypes.add(PARAMETER);
		depedencyTypes.add(RETURN);
		depedencyTypes.add(SET);
		depedencyTypes.add(CREATE);
		depedencyTypes.add(USE);
		depedencyTypes.add(THROW);
		depedencyTypes.add(IMPLLINK);
		return depedencyTypes;
	}
}
