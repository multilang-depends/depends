package depends.extractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import depends.deptypes.DependencyType;
import depends.entity.repo.EntityRepo;
import depends.format.dot.DotDataBuilder;
import depends.format.excel.ExcelDataBuilder;
import depends.format.json.JDataBuilder;
import depends.format.json.JDepObject;
import depends.format.json.JsonFormatter;
import depends.format.matrix.DependencyMatrix;
import depends.format.matrix.FileDependencyGenerator;
import depends.format.xml.XDataBuilder;
import depends.format.xml.XDepObject;
import depends.format.xml.XmlFormatter;
import depends.util.Configure;
import depends.util.FileTraversal;
abstract public class AbstractLangWorker {
	public void register() {
		LangWorkers.getRegistry().register(this);
	}
	public abstract String supportedLanguage();
	
	public abstract String[] fileSuffixes();
	
    private Configure configure = new Configure();

    DependencyMatrix dependencyMatrix;

    protected EntityRepo entityRepo = new EntityRepo();
    
	public void work(String lang, String inputDir, String usageDir, String projectName) {
        config(lang, inputDir, usageDir, projectName,this);
        parseAllFiles();
        resolveBindings();
        identifyDependencies();
        outputDeps(DependencyType.allDependencies());
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
        configure.setIncludePath(usageDir);
        configure.setProjectName(projectName);
        configure.setWorker(worker);
    }

    private final void parseAllFiles() {
        System.out.println("start parsing files...");		
    	FileTraversal fileTransversal = new FileTraversal(new FileTraversal.IFileVisitor(){
			@Override
			public void visit(File file) {
	            FileParser fileParser = getFileParser(file.getAbsolutePath());
	            try {
	                System.out.println("parsing " + file.getAbsolutePath() 
	                		+ "...");		
	                fileParser.parse();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }	
			}
    		
    	});
    	fileTransversal.extensionFilter(this.fileSuffixes());
		fileTransversal.travers(configure.getInputSrcPath());
        System.out.println("all files procceed successfully...");		

	}
    
    protected abstract FileParser getFileParser(String fileFullPath);
    

	private final void outputDeps(ArrayList<String> depTypes) {
        JDataBuilder jBuilder = new JDataBuilder();
        JDepObject jDepObject = jBuilder.build(dependencyMatrix,configure);
        JsonFormatter jasonFormatter = new JsonFormatter();
        jasonFormatter.toJson(jDepObject,configure.getOutputJsonFile());
        System.out.println("Export " + configure.getOutputJsonFile() + " successfully...");

        XDataBuilder xBuilder = new XDataBuilder();
        XDepObject xDepObject = xBuilder.build(dependencyMatrix,configure);
        XmlFormatter xmlFormatter = new XmlFormatter();
        xmlFormatter.toXml(xDepObject,configure.getOutputXmlFile());
        System.out.println("Export " + configure.getOutputXmlFile() + " successfully...");
        
		ExcelDataBuilder builder = new ExcelDataBuilder(dependencyMatrix);
		if (builder.output(configure.getOutputExcelFile())) {
			System.out.println("Export " + configure.getOutputExcelFile() + " successfully...");
		}	
		
		DotDataBuilder dotBuilder = new DotDataBuilder(dependencyMatrix);
		if (dotBuilder.output(configure.getOutputDotFile())) {
			System.out.println("Export " + configure.getOutputDotFile() + " successfully...");
		}
    }
	public List<String> includePaths() {
		String[] paths = configure.getIncludePath().split("@");
		ArrayList<String> r = new ArrayList<String>();
		for (String path:paths) {
			r.add(path);
		}
		return r;
	}
}
