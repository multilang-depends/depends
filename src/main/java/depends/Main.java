/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

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
import depends.matrix.DependencyMatrix;
import depends.matrix.FileDependencyGenerator;
import depends.matrix.FunctionDependencyGenerator;
import depends.matrix.LeadingNameStripper;
import depends.matrix.MatrixLevelReducer;
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
		/* by default use file dependency generator */
		DependencyGenerator dependencyGenerator = new FileDependencyGenerator();
		/* method parameter means use method generator */
		if (app.getGranularity().equals("method"))
				dependencyGenerator = new FunctionDependencyGenerator();
		
		if (app.isStripLeadingPath()) {
			dependencyGenerator.setLeadingStripper(new LeadingNameStripper(inputDir));
		}
		
		langProcessor.setDependencyGenerator(dependencyGenerator);
		langProcessor.buildDependencies(inputDir, includeDir);
		
		FilenameWritter filenameWritter = new EmptyFilenameWritter();
		if (app.getNamePathPattern().equals("dot")) {
			filenameWritter = new DotPathFilenameWritter();
		}
		
		DependencyMatrix matrix = langProcessor.getDependencies();
		matrix = matrix.reWriteFilenamePattern(filenameWritter );

		if (app.getGranularity().startsWith("L")) {
			matrix = new MatrixLevelReducer(matrix,app.getGranularity().substring(1)).shrinkToLevel();
		}
		DependencyDumper output = new DependencyDumper(matrix);
		output.outputResult(outputName,outputDir,outputFormat);
		long endTime = System.currentTimeMillis();
		System.out.println("Consumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or "
				+ (float) ((endTime - startTime) / 60000.00) + " min.");
	}

}
