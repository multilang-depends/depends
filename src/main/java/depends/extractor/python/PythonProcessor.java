package depends.extractor.python;

import static depends.deptypes.DependencyType.*;

import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.ImportLookupStrategy;

public class PythonProcessor extends AbstractLangProcessor {
	private PythonImportLookupStrategy importedLookupStrategy;

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
		importedLookupStrategy = new PythonImportLookupStrategy();
		return this.importedLookupStrategy;
	}


	@Override
	public BuiltInType getBuiltInType() {
		return new PythonBuiltInType();
	}
	
	@Override
	protected FileParser createFileParser(String fileFullPath) {
		IncludedFileLocator includeFileLocator = new IncludedFileLocator(super.includePaths());
		return new PythonFileParser(fileFullPath,entityRepo,includeFileLocator,inferer,this);
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
		return depedencyTypes;
	}

}
