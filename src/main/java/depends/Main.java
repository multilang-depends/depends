package depends;

import java.io.File;
import java.nio.file.Files;

import depends.addons.DV8MappingFileBuilder;
import depends.extractor.AbstractLangWorker;
import depends.extractor.LangWorkers;
import depends.extractor.cpp.CppWorker;
import depends.extractor.java.JavaWorker;
import depends.extractor.ruby.RubyWorker;
import depends.format.DependencyDumper;
import depends.util.FileUtil;
import picocli.CommandLine;

public class Main {

	public static void main(String[] args) {
		try {
			DependsCommand app = CommandLine.populateCommand(new DependsCommand(), args);
			if (app.help) {
				CommandLine.usage(new DependsCommand(), System.out);
				System.exit(0);
			}
			executeCommand(app);
		} catch (Exception e) {
			CommandLine.usage(new DependsCommand(), System.out);
			System.exit(0);
		}

	}

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
		LangWorkers.getRegistry().register(new JavaWorker(inputDir, includeDir));
		LangWorkers.getRegistry().register(new CppWorker(inputDir, includeDir));
		LangWorkers.getRegistry().register(new RubyWorker(inputDir, includeDir));
		
		AbstractLangWorker worker = LangWorkers.getRegistry().getWorkerOf(lang);
		if (worker == null) {
			System.out.println("Not support this language: " + lang);
			return;
		}

		long startTime = System.currentTimeMillis();
		worker.work();
		DependencyDumper output = new DependencyDumper(worker.getDependencies(),worker.getErrors());
		output.outputResult(outputName,outputDir,outputFormat);
		long endTime = System.currentTimeMillis();
		System.out.println("Consumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or "
				+ (float) ((endTime - startTime) / 60000.00) + " min.");
	}

}
