package depends.extractor.python.py3;

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

public class Python3Processor extends BasePythonProcessor {

	public Python3Processor() {
		/* Because Python is dynamic languange, 
		 * we eagerly resolve expression*/
		super(true);
	}

	@Override
	public String supportedLanguage() {
		return "python3";
	}


	@Override
	public FileParser createFileParser(String fileFullPath) {
		IncludedFileLocator includeFileLocator = new IncludedFileLocator(super.includePaths());
		return new Python3FileParser(fileFullPath,entityRepo,includeFileLocator,inferer,this);
	}

	

}
