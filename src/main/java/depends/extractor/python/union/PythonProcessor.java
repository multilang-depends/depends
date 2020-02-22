package depends.extractor.python.union;

import static depends.deptypes.DependencyType.*;

import java.util.ArrayList;
import java.util.List;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.python.BasePythonProcessor;
import depends.extractor.python.PythonBuiltInType;
import depends.extractor.python.PythonImportLookupStrategy;
import depends.extractor.ruby.IncludedFileLocator;
import depends.relations.ImportLookupStrategy;

public class PythonProcessor extends BasePythonProcessor {

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
	public FileParser createFileParser(String fileFullPath) {
		IncludedFileLocator includeFileLocator = new IncludedFileLocator(super.includePaths());
		return new PythonFileParser(fileFullPath,entityRepo,includeFileLocator,inferer,this);
	}

	

}
