package depends.extractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.codehaus.plexus.util.FileUtils;

import depends.deptypes.DependencyType;
import depends.entity.repo.EntityRepo;
import depends.format.FileAttributes;
import depends.format.detail.DetailDataBuilder;
import depends.format.dot.DotDataBuilder;
import depends.format.excel.ExcelDataBuilder;
import depends.format.json.JDataBuilder;
import depends.format.json.JDepObject;
import depends.format.json.JsonFormatter;
import depends.format.xml.XDataBuilder;
import depends.format.xml.XDepObject;
import depends.format.xml.XmlFormatter;
import depends.matrix.DependencyMatrix;
import depends.matrix.FileDependencyGenerator;
import depends.relations.Inferer;
import depends.util.FileTraversal;
abstract public class AbstractLangWorker {
	public abstract String supportedLanguage();
	public abstract String[] fileSuffixes();
	protected Inferer inferer;
	protected EntityRepo entityRepo;
	DependencyMatrix dependencyMatrix;
	private String inputSrcPath;
	private String includeDirs;

	public AbstractLangWorker(String inputDir, String includeDir) {
		entityRepo = new EntityRepo();
		this.inputSrcPath = inputDir;
		this.includeDirs = includeDir;
	}
	
    
	public void work() {
        parseAllFiles();
        resolveBindings();
        identifyDependencies();
	}

	private void outputErrors() {
		List<String> errors = getErrors();
		for (String e:errors) {
			System.err.println(e);
		}
	}
	
	/**
	 * Errors during all execution steps. could be extend as several methods in future
	 * @return
	 */
	public abstract List<String> getErrors();
	/**
	 * 
	 * @return unsolved bindings
 	 */
    protected void resolveBindings() {
		System.out.println("Resolve types and bindings of variables, methods and expressions....");
        Set<String> unsolved = inferer.resolveAllBindings();
        if (unsolved.size()>0)
        	System.err.println("The following items are unsolved." + unsolved);
        System.out.println("types and bindings resolved successfully...");
    }
    
    private void identifyDependencies(){
		System.out.println("dependencie data generating...");	
        dependencyMatrix  = (new FileDependencyGenerator()).build(entityRepo);
        dependencyMatrix.remapIds(entityRepo);
        dependencyMatrix.stripFilenames(inputSrcPath);
        System.out.println("dependencie data generating done successfully...");	 	
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
		fileTransversal.travers(this.inputSrcPath);
        System.out.println("all files procceed successfully...");		

	}
    
    protected abstract FileParser getFileParser(String fileFullPath);
    

	private final void outputDeps(ArrayList<String> depTypes, String projectName) {
		DetailDataBuilder detailBuilder = new DetailDataBuilder(dependencyMatrix);
		detailBuilder.output(projectName  + "_dep.txt");
		
		JDataBuilder jBuilder = new JDataBuilder();
        JDepObject jDepObject = jBuilder.build(dependencyMatrix,new FileAttributes(projectName));
        JsonFormatter jasonFormatter = new JsonFormatter();
        jasonFormatter.toJson(jDepObject,projectName  + "_dep.json");
        System.out.println("Export " + projectName  + "_dep.json" + " successfully...");

        XDataBuilder xBuilder = new XDataBuilder();
        XDepObject xDepObject = xBuilder.build(dependencyMatrix,new FileAttributes(projectName));
        XmlFormatter xmlFormatter = new XmlFormatter();
        xmlFormatter.toXml(xDepObject,projectName  + "_dep.xml");
        System.out.println("Export " + projectName  + "_dep.xml" + " successfully...");
        
		ExcelDataBuilder builder = new ExcelDataBuilder(dependencyMatrix);
		if (builder.output(projectName  + "_dep.xls")) {
			System.out.println("Export " + projectName  + "_dep.xls" + " successfully...");
		}	
		
		DotDataBuilder dotBuilder = new DotDataBuilder(dependencyMatrix);
		if (dotBuilder.output(projectName  + "_dep.dot")) {
			System.out.println("Export " + projectName + "_dep.dot" + " successfully...");
		}

    }
	public List<String> includePaths() {
		String[] paths = this.includeDirs.split(";");
		ArrayList<String> r = new ArrayList<String>();
		for (String path:paths) {
			if (FileUtils.fileExists(path)) {
				if (!r.contains(path))
					r.add(path);
			}
			path = this.inputSrcPath +File.separator+path;
			if (FileUtils.fileExists(path)) {
				if (!r.contains(path))
					r.add(path);
			}
		}
		return r;
	}
	public void outputResult(String projectName) {
        outputErrors();
        outputDeps(DependencyType.allDependencies(), projectName);
	}
	public DependencyMatrix getDependencies() {
		return dependencyMatrix;
	}
	public EntityRepo getEntityRepo() {
		return this.entityRepo;
	}
}
