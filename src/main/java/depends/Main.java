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
import java.util.Set;

import org.codehaus.plexus.util.StringUtils;

import depends.addons.DV8MappingFileBuilder;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.LangProcessorRegistration;
import depends.extractor.UnsolvedBindings;
import depends.format.DependencyDumper;
import depends.format.detail.UnsolvedSymbolDumper;
import multilang.depends.util.file.path.DotPathFilenameWritter;
import multilang.depends.util.file.path.EmptyFilenameWritter;
import multilang.depends.util.file.path.FilenameWritter;
import multilang.depends.util.file.path.UnixPathFilenameWritter;
import multilang.depends.util.file.path.WindowsPathFilenameWritter;
import depends.generator.DependencyGenerator;
import depends.generator.FileDependencyGenerator;
import depends.generator.FunctionDependencyGenerator;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.transform.MatrixLevelReducer;
import multilang.depends.util.file.strip.LeadingNameStripper;
import multilang.depends.util.file.FileUtil;
import multilang.depends.util.file.FolderCollector;
import multilang.depends.util.file.TemporaryFile;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.ehcache.CacheManager;
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
			} else if (e instanceof ParameterException){
				System.err.println(e.getMessage());
			}else {
				System.err.println("Exception encountered. If it is a design error, please report issue to us." );
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	@SuppressWarnings("unchecked")
	private static void executeCommand(DependsCommand app) throws ParameterException {
		String lang = app.getLang();
		String inputDir = app.getSrc();
		String[] includeDir = app.getIncludes();
		String outputName = app.getOutputName();
		String outputDir = app.getOutputDir();
		String[] outputFormat = app.getFormat();

		inputDir = FileUtil.uniqFilePath(inputDir);
		boolean supportImplLink = false;
		if (app.getLang().equals("cpp") || app.getLang().equals("python")) supportImplLink = true;
		
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

		if ( app.isDv8map()) {
			DV8MappingFileBuilder dv8MapfileBuilder = new DV8MappingFileBuilder(langProcessor.supportedRelations());
			dv8MapfileBuilder.create(outputDir+File.separator+"depends-dv8map.mapping");
		}
		
		long startTime = System.currentTimeMillis();
		
		FilenameWritter filenameWritter = new EmptyFilenameWritter();
		if (!StringUtils.isEmpty(app.getNamePathPattern())) {
			if (app.getNamePathPattern().equals("dot")||
					app.getNamePathPattern().equals(".")) {
				filenameWritter = new DotPathFilenameWritter();
			}else if (app.getNamePathPattern().equals("unix")||
					app.getNamePathPattern().equals("/")) {
				filenameWritter = new UnixPathFilenameWritter();
			}else if (app.getNamePathPattern().equals("windows")||
					app.getNamePathPattern().equals("\\")) {
				filenameWritter = new WindowsPathFilenameWritter();
			}else{
				throw new ParameterException("Unknown name pattern paremater:" + app.getNamePathPattern());
			}
		}

		
		/* by default use file dependency generator */
		DependencyGenerator dependencyGenerator = new FileDependencyGenerator();
		if (!StringUtils.isEmpty(app.getGranularity())) {
			/* method parameter means use method generator */
			if (app.getGranularity().equals("method"))
					dependencyGenerator = new FunctionDependencyGenerator();
			else if (app.getGranularity().equals("file"))
				/*no action*/;
			else if (app.getGranularity().startsWith("L"))
				/*no action*/;
			else
				throw new ParameterException("Unknown granularity parameter:" + app.getGranularity());
		}
		
		if (app.isStripLeadingPath() ||
				app.getStrippedPaths().length>0) {
			dependencyGenerator.setLeadingStripper(new LeadingNameStripper(app.isStripLeadingPath(),inputDir,app.getStrippedPaths()));
		}
		
		if (app.isDetail()) {
			dependencyGenerator.setGenerateDetail(true);
		}
		
		dependencyGenerator.setFilenameRewritter(filenameWritter);
		langProcessor.setDependencyGenerator(dependencyGenerator);
		
		langProcessor.buildDependencies(inputDir, includeDir,app.getTypeFilter(),supportImplLink,app.isOutputExternalDependencies(),app.isDuckTypingDeduce());
		
		
		DependencyMatrix matrix = langProcessor.getDependencies();

		if (app.getGranularity().startsWith("L")) {
			matrix = new MatrixLevelReducer(matrix,app.getGranularity().substring(1)).shrinkToLevel();
		}
		DependencyDumper output = new DependencyDumper(matrix);
		output.outputResult(outputName,outputDir,outputFormat);
		if (app.isOutputExternalDependencies()) {
			Set<UnsolvedBindings> unsolved = langProcessor.getExternalDependencies();
	    	UnsolvedSymbolDumper unsolvedSymbolDumper = new UnsolvedSymbolDumper(unsolved,app.getOutputName(),app.getOutputDir(),
	    			new LeadingNameStripper(app.isStripLeadingPath(),inputDir,app.getStrippedPaths()));
	    	unsolvedSymbolDumper.output();
		}
		long endTime = System.currentTimeMillis();
		TemporaryFile.getInstance().delete();
		CacheManager.create().shutdown();
		System.out.println("Consumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or "
				+ (float) ((endTime - startTime) / 60000.00) + " min.");
	}

}
