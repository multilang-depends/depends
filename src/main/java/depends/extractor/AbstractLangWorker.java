package depends.extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import depends.deptypes.DependencyType;
import depends.entity.repo.EntityRepo;
import depends.format.json.JDataBuilder;
import depends.format.json.JDepObject;
import depends.format.json.JsonFormatter;
import depends.format.matrix.DependencyMatrix;
import depends.format.matrix.FileDependencyGenerator;
import depends.format.xml.XDataBuilder;
import depends.format.xml.XDepObject;
import depends.format.xml.XmlFormatter;
import depends.util.Configure;
import depends.util.FileUtil;
abstract public class AbstractLangWorker {
	public void register() {
		LangWorkers.getRegistry().register(this);
	}
	public abstract String supportedLanguage();
	
	public abstract String fileSuffix();
	
    protected static Configure configure = Configure.getConfigureInstance();

    DependencyMatrix dependencyMatrix;

    protected EntityRepo entityRepo = new EntityRepo();
    
	public void work(String lang, String inputDir, String usageDir, String projectName, String depMask) {
        config(lang, inputDir, usageDir, projectName,this);
        parseAllFiles();
        resolveBindings();
        identifyDependencies();
        outputDeps(DependencyType.resolveMask(depMask));
	}

	/**
	 * 
	 * @return unsolved bindings
 	 */
    protected void resolveBindings() {
        System.out.println("start resolve bindings...");
        Set<String> unsolved = entityRepo.resolveAllBindings();
        if (unsolved.size()>0)
        	System.err.println("The following items are unsolved." + unsolved);
        System.out.println("bindings resolved successfully...");
    }
    
    private void identifyDependencies(){
        System.out.println("dependencie data generating...");	
        FileDependencyGenerator dependencyGenerator= new FileDependencyGenerator();
        dependencyMatrix  = dependencyGenerator.buildWithRemap(entityRepo);
        System.out.println("dependencie data generating done successfully...");	 	
    }
	/**
     * parse the input parameter, save into configure
     * @param inputDir
     * @param usageDir
     * @param projectName
     * @param templateWork 
     */
    private final void config(String lang, String inputDir, String usageDir, String projectName, AbstractLangWorker worker) {
        configure.setInputSrcPath(inputDir);
        configure.setUsageSrcPath(usageDir);
        configure.setAnalyzedProjectName(projectName);
        configure.setWorker(worker);
    }

    private final void parseAllFiles() {
        FileUtil fileUtil = new FileUtil(configure.getInputSrcPath());
        List<String> files = fileUtil.getFileNameList(this.fileSuffix());
        System.out.println("start parsing files...");		
        for (String fileFullPath : files) {
        	System.out.println("processing " + fileFullPath + "...");		
            FileParser fileParser = getFileParser(fileFullPath);
            try {
                fileParser.parse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("all files procceed successfully...");		

	}
    
    protected abstract FileParser getFileParser(String fileFullPath);
    

	private final void outputDeps(ArrayList<String> depTypes) {
        JDataBuilder jBuilder = new JDataBuilder();
        JDepObject jDepObject = jBuilder.build(dependencyMatrix);
        JsonFormatter jasonFormatter = new JsonFormatter();
        jasonFormatter.toJson(jDepObject,configure.getOutputJsonFile());
        System.out.println("Export " + configure.getOutputJsonFile() + " successfully...");

        XDataBuilder xBuilder = new XDataBuilder();
        XDepObject xDepObject = xBuilder.build(dependencyMatrix);
        XmlFormatter xmlFormatter = new XmlFormatter();
        xmlFormatter.toXml(xDepObject,configure.getOutputXmlFile());
        System.out.println("Export " + configure.getOutputXmlFile() + " successfully...");
    }
}
