package depends.extractor.cpp.cdt;

import java.io.IOException;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import depends.entity.Entity;
import depends.entity.Inferer;
import depends.entity.repo.EntityRepo;
import depends.entity.types.FileEntity;
import depends.extractor.cpp.CppFileParser;
import depends.util.FileUtil;

public class CdtCppFileParser extends CppFileParser {

	private PreprocessorHandler preprocessorHandler ;
	private Inferer inferer;

	public CdtCppFileParser(String fileFullPath, EntityRepo entityRepo,PreprocessorHandler preprocessorHandler, Inferer inferer) {
		super(fileFullPath, entityRepo,inferer);
		this.preprocessorHandler = preprocessorHandler;
		this.fileFullPath = FileUtil.uniqFilePath(fileFullPath);
		this.inferer = inferer;

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
		/** If file already exist, skip it */
		Entity fileEntity = entityRepo.getEntity(fileFullPath);
		if (fileEntity!=null && fileEntity instanceof FileEntity) {
			FileEntity t = ((FileEntity)fileEntity);
			if (!t.isInProjectScope() && isInScope)
				t.setInProjectScope(true);
			return;
		}
		
		CdtCppEntitiesListener bridge = new CdtCppEntitiesListener(fileFullPath, entityRepo, preprocessorHandler,inferer);
		IASTTranslationUnit translationUnit = (new CDTParser(preprocessorHandler.getIncludePaths())).parse(fileFullPath);
		translationUnit.accept(bridge);
	}
	
}
