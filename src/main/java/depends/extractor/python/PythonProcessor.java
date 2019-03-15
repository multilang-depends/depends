package depends.extractor.python;

import static depends.deptypes.DependencyType.CALL;
import static depends.deptypes.DependencyType.CONTAIN;
import static depends.deptypes.DependencyType.CREATE;
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
import depends.extractor.FileParser;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.ImportLookupStrategy;

public class PythonProcessor extends AbstractLangProcessor {
	public PythonProcessor() {
		/* Because Python is dynamic languange, 
		 * we eagerly resolve expression*/
		super(true);
	}

	@Override
	public String supportedLanguage() {
		return "python";
	}

	@Override
	public String[] fileSuffixes() {
		return new String[] {".py"};
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new PythonImportLookupStrategy();
	}


	@Override
	public BuiltInType getBuiltInType() {
		return new PythonBuiltInType();
	}
	
	@Override
	protected FileParser createFileParser(String fileFullPath) {
		return new PythonFileParser(fileFullPath,entityRepo,new IncludedFileLocator(super.includePaths()),inferer,this);
	}

	@Override
	public List<String> supportedRelations() {
		/* To be check: is python support implemenent? 
		 * should no cast supported.
		 * */
//		depedencyTypes.add(IMPLEMENT);
//		depedencyTypes.add(CAST);
//		depedencyTypes.add(ANNOTATION);

		ArrayList<String> depedencyTypes = new ArrayList<>();
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
		return depedencyTypes;
	}

}
