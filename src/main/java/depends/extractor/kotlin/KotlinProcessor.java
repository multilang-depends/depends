package depends.extractor.kotlin;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.java.JavaBuiltInType;
import depends.extractor.java.JavaImportLookupStrategy;
import depends.relations.ImportLookupStrategy;

import java.util.ArrayList;
import java.util.List;

import static depends.deptypes.DependencyType.*;

public class KotlinProcessor extends AbstractLangProcessor {

	public KotlinProcessor() {
    	super();
	}

	@Override
	public String supportedLanguage() {
		return "kotlin[on-going]";
	}

	@Override
	public String[] fileSuffixes() {
		return new String[] {".kt"};
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new JavaImportLookupStrategy(entityRepo);
	}

	@Override
	public BuiltInType getBuiltInType() {
		return new JavaBuiltInType();
	}

	@Override
	public FileParser createFileParser() {
		return new KotlinFileParser(entityRepo, bindingResolver);
	}

	@Override
	public boolean isEagerExpressionResolve(){
		return true;
	}

	@Override
	public List<String> supportedRelations() {
		ArrayList<String> depedencyTypes = new ArrayList<>();
		depedencyTypes.add(IMPORT);
		depedencyTypes.add(CONTAIN);
		depedencyTypes.add(IMPLEMENT);
		depedencyTypes.add(INHERIT);
		depedencyTypes.add(CALL);
		depedencyTypes.add(PARAMETER);
		depedencyTypes.add(RETURN);
		depedencyTypes.add(SET);
		depedencyTypes.add(CREATE);
		depedencyTypes.add(USE);
		depedencyTypes.add(CAST);
		depedencyTypes.add(THROW);
		depedencyTypes.add(ANNOTATION);
		return depedencyTypes;
	}	

}
