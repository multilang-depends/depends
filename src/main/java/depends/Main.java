package depends;

import java.io.File;
import java.util.List;

import depends.addons.DV8MappingFileBuilder;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.LangProcessorRegistration;
import depends.format.DependencyDumper;
import depends.format.path.DotPathFilenameWritter;
import depends.format.path.EmptyFilenameWritter;
import depends.format.path.FilenameWritter;
import depends.matrix.DependencyGenerator;
import depends.matrix.FileDependencyGenerator;
import depends.matrix.FunctionDependencyGenerator;
import depends.util.FileUtil;
import depends.util.FolderCollector;
import edu.emory.mathcs.backport.java.util.Arrays;
import picocli.CommandLine;
import picocli.CommandLine.PicocliException;

public class Main {

	public static void main(String[] args) {
		try {
			LangRegister langRegister = new LangRegister();
			langRegister.register();
			DependsCommand app = CommandLine.populateCommand(new DependsCommand(), args);
			if (app.help) {
				CommandLine.usage(new DependsCommand(), System.out);
				System.exit(0);
			}
			executeCommand(app);
		} catch (Exception e) {
			if (e instanceof PicocliException) {
				CommandLine.usage(new DependsCommand(), System.out);
			}else {
				System.err.println("Exception encountered. If it is a design error, please report issue to us." );
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	@SuppressWarnings("unchecked")
	private static void executeCommand(DependsCommand app) {
		String lang = app.getLang();
		String inputDir = app.getSrc();
		String[] includeDir = app.getIncludes();
		String outputName = app.getOutputName();
		String outputDir = app.getOutputDir();
		String[] outputFormat = app.getFormat();
		if ( app.isDv8map()) {
			DV8MappingFileBuilder dv8MapfileBuilder = new DV8MappingFileBuilder();
			dv8MapfileBuilder.create(outputDir+File.separator+"depends-dv8map.json");
		}
		inputDir = FileUtil.uniqFilePath(inputDir);

		if (app.isAutoInclude()) {
			FolderCollector includePathCollector = new FolderCollector();
			List<String> additionalIncludePaths = includePathCollector.getFolders(inputDir);
			additionalIncludePaths.addAll(Arrays.asList(includeDir));
			includeDir = additionalIncludePaths.toArray(new String[] {});
		}
			
		AbstractLangProcessor langProcessor = LangProcessorRegistration.getRegistry().getProcessorOf(lang);
		if (langProcessor == null) {
			System.err.println("Not support this language: " + lang);
			return;
		}

		long startTime = System.currentTimeMillis();
		DependencyGenerator dependencyGenerator = app.getGranularity().equals("file")?
				(new FileDependencyGenerator()):(new FunctionDependencyGenerator());
		langProcessor.setDependencyGenerator(dependencyGenerator);
		langProcessor.buildDependencies(inputDir, includeDir);
		if (app.isStripLeadingPath()) {
			langProcessor.getDependencies().stripFilenames(inputDir);
		}
		
		FilenameWritter filenameWritter = new EmptyFilenameWritter();
		if (app.getNamePathPattern().equals("dot")) {
			filenameWritter = new DotPathFilenameWritter();
		}
		langProcessor.getDependencies().reWriteFilenamePattern(filenameWritter );
		DependencyDumper output = new DependencyDumper(langProcessor.getDependencies());
		output.outputResult(outputName,outputDir,outputFormat);
		long endTime = System.currentTimeMillis();
		System.out.println("Consumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or "
				+ (float) ((endTime - startTime) / 60000.00) + " min.");
	}

}
