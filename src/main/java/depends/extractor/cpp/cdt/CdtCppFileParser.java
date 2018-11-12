package depends.extractor.cpp.cdt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.extractor.cpp.CppFileParser;
import depends.util.FileUtil;

public class CdtCppFileParser extends CppFileParser {

	private PreprocessorHandler preprocessorHandler ;

	public CdtCppFileParser(String fileFullPath, EntityRepo entityRepo,PreprocessorHandler preprocessorHandler) {
		super(fileFullPath, entityRepo);
		this.preprocessorHandler = preprocessorHandler;

	}
	@Override
	public void parse() throws IOException {
		parse(true);
	}
	
	/**
	 * 
	 * @param isInScope whether the parse is invoked by project file or an 'indirect' included file
	 */
	public void parse(boolean isInScope) {
		String uniqPath = FileUtil.uniqFilePath(fileFullPath);
		
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(uniqPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			FileEntity t = ((FileEntity)fileEntity);
			if (!t.isInProjectScope() && isInScope)
				t.setInProjectScope(true);
			return;
		}
		
		CdtCppEntitiesListener bridge = new CdtCppEntitiesListener(FileUtil.uniqFilePath(fileFullPath), entityRepo, preprocessorHandler );
		IASTTranslationUnit translationUnit = (new CDTParser(preprocessorHandler.getIncludePaths())).parse(this.fileFullPath);
		translationUnit.accept(bridge);
	}
	
}
