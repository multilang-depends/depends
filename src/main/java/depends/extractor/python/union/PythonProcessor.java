package depends.extractor.python.union;

import depends.extractor.FileParser;
import depends.extractor.python.BasePythonProcessor;
import depends.extractor.IncludedFileLocator;

public class PythonProcessor extends BasePythonProcessor {

	@Override
	public boolean isEagerExpressionResolve(){
		return true;
	}

	@Override
	public String supportedLanguage() {
		return "python";
	}


	@Override
	public FileParser createFileParser(String fileFullPath) {
		IncludedFileLocator includeFileLocator = new IncludedFileLocator(super.includePaths());
		return new PythonFileParser(fileFullPath,entityRepo,includeFileLocator, bindingResolver,this);
	}

	

}
