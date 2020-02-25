package depends.extractor.python.py3;

import depends.extractor.FileParser;
import depends.extractor.python.BasePythonProcessor;
import depends.extractor.ruby.IncludedFileLocator;

public class Python3Processor extends BasePythonProcessor {

	public Python3Processor() {
		/* Because Python is dynamic languange, 
		 * we eagerly resolve expression*/
		super(true);
	}

	@Override
	public String supportedLanguage() {
		return "python3[deprecated]";
	}


	@Override
	public FileParser createFileParser(String fileFullPath) {
		IncludedFileLocator includeFileLocator = new IncludedFileLocator(super.includePaths());
		return new Python3FileParser(fileFullPath,entityRepo,includeFileLocator,inferer,this);
	}

	

}
