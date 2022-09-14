package depends.extractor.python;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.relations.ImportLookupStrategy;

import java.util.ArrayList;
import java.util.List;

import static depends.deptypes.DependencyType.*;

public abstract class BasePythonProcessor extends AbstractLangProcessor{
	private PythonImportLookupStrategy importedLookupStrategy;

	@Override
	public String[] fileSuffixes() {
		return new String[] {".py"};
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		importedLookupStrategy = new PythonImportLookupStrategy(entityRepo);
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

	@Override
	public boolean supportCallAsImpl() {
		return true;
	}
}
