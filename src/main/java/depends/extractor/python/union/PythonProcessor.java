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
	public FileParser createFileParser() {
		IncludedFileLocator includeFileLocator = new IncludedFileLocator(super.includePaths());
		return new PythonFileParser(entityRepo,includeFileLocator, bindingResolver,this);
	}

	

}
