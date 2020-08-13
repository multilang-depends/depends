package depends.extractor.cpp;

import depends.entity.repo.EntityRepo;
import multilang.depends.util.file.TemporaryFile;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IMacroBinding;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MacroFileRepo extends MacroRepo{
	private EntityRepo entityRepo;

	public MacroFileRepo(EntityRepo entityRepo) {
		this.entityRepo = entityRepo;
	}

	@Override
	public Map<String, String> get(String incl) {
		Integer fileId = entityRepo.getEntity(incl).getId();

		try
	      {
	         FileInputStream fileIn = new FileInputStream(TemporaryFile.getInstance().macroPath(fileId));
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         @SuppressWarnings("unchecked")
			Map<String, String> macros = (Map<String, String>) in.readObject();
	         if (macros==null) macros = new HashMap<>();
	         in.close();
	         fileIn.close();
	         return macros;
	      }catch(IOException | ClassNotFoundException i)
	      {
	         return new HashMap<>();
	      }	
	}

	@Override
	public void putMacros(String fileFullPath, Map<String, String> macroMap,
			IASTPreprocessorMacroDefinition[] macroDefinitions) {
		if (macroDefinitions.length==0 && macroMap.size()==0) return;
		Integer fileId = entityRepo.getEntity(fileFullPath).getId();
		
		Map<String, String> macros = get(fileFullPath);
		macros.putAll(macroMap);
		for (IASTPreprocessorMacroDefinition def:macroDefinitions) {
			macros.put(((IMacroBinding)def.getName().resolveBinding()).toString(), new String(def.getExpansion()));
		}
		
		try {
			FileOutputStream fileOut = new FileOutputStream(TemporaryFile.getInstance().macroPath(fileId));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(macros);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
