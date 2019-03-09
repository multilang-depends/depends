package depends.extractor.kotlin;

import static depends.deptypes.DependencyType.ANNOTATION;
import static depends.deptypes.DependencyType.CALL;
import static depends.deptypes.DependencyType.CAST;
import static depends.deptypes.DependencyType.CONTAIN;
import static depends.deptypes.DependencyType.CREATE;
import static depends.deptypes.DependencyType.IMPLEMENT;
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
import depends.extractor.java.JavaBuiltInType;
import depends.extractor.java.JavaImportLookupStrategy;
import depends.relations.ImportLookupStrategy;

public class KotlinProcessor extends AbstractLangProcessor {

	public KotlinProcessor() {
    	super(true);
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
		return new JavaImportLookupStrategy();
	}

	@Override
	public BuiltInType getBuiltInType() {
		return new JavaBuiltInType();
	}

	@Override
	protected FileParser createFileParser(String fileFullPath) {
		return new KotlinFileParser(fileFullPath,entityRepo, inferer);
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
