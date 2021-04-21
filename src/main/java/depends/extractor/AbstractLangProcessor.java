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

package depends.extractor;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.repo.BuiltInType;
import depends.entity.repo.EntityRepo;
import depends.entity.repo.InMemoryEntityRepo;
import depends.relations.ImportLookupStrategy;
import depends.relations.Inferer;
import multilang.depends.util.file.FileTraversal;
import multilang.depends.util.file.FileUtil;
import org.codehaus.plexus.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class AbstractLangProcessor {

	/**
	 * The name of the lang
	 * 
	 * @return
	 */
	public abstract String supportedLanguage();

	/**
	 * The file suffixes in the lang
	 * 
	 * @return
	 */
	public abstract String[] fileSuffixes();

	/**
	 * Strategy of how to lookup types and entities in the lang.
	 * 
	 * @return
	 */
	public abstract ImportLookupStrategy getImportLookupStrategy();

	/**
	 * The builtInType of the lang.
	 * 
	 * @return
	 */
	public abstract BuiltInType getBuiltInType();

	/**
	 * The language specific file parser
	 * 
	 * @param fileFullPath
	 * @return
	 */
	protected abstract FileParser createFileParser(String fileFullPath);

	public Inferer inferer;
	protected EntityRepo entityRepo;
	protected String inputSrcPath;
	public String[] includeDirs;
	private Set<UnsolvedBindings> potentialExternalDependencies;
	private List<String> includePaths;
	private static Logger logger = LoggerFactory.getLogger(AbstractLangProcessor.class);
	
	public AbstractLangProcessor() {
		entityRepo = new InMemoryEntityRepo();
		inferer = new Inferer(entityRepo,  getImportLookupStrategy(), getBuiltInType());
	}

	/**
	 * The process steps of build dependencies. Step 1: parse all files, add
	 * entities and expression into repositories Step 2: resolve bindings of files
	 * (if not resolved yet) Step 3: identify dependencies
	 *
	 * @param inputDir
	 * @param includeDir
	 * @return
	 */
	public EntityRepo buildDependencies(String inputDir, String[] includeDir, boolean callAsImpl, boolean isCollectUnsolvedBindings, boolean isDuckTypingDeduce) {
		this.inputSrcPath = inputDir;
		this.includeDirs = includeDir;
		this.inferer.setCollectUnsolvedBindings(isCollectUnsolvedBindings);
		this.inferer.setDuckTypingDeduce(isDuckTypingDeduce);
		logger.info("Start parsing files...");
		parseAllFiles();
		markAllEntitiesScope();
		if (logger.isInfoEnabled()) {
			logger.info("Resolve types and bindings of variables, methods and expressions.... " + this.inputSrcPath);
			logger.info("Heap Information: " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
		}
		resolveBindings(callAsImpl);
		if (logger.isInfoEnabled()) {
			System.gc();
			logger.info("Heap Information: " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
		}
		return entityRepo;
	}

	private void markAllEntitiesScope() {
		entityRepo.getFileEntities().stream().forEach(entity -> {
			Entity file = entity.getAncestorOfType(FileEntity.class);
			try {
				if (!file.getQualifiedName().startsWith(this.inputSrcPath)) {
					entity.setInScope(false);
				}
			} catch (Exception e) {

			}
		});
	}

	/**
	 * 
	 * @param callAsImpl
	 * @return unsolved bindings
	 */
	public void resolveBindings(boolean callAsImpl) {
		System.out.println("Resolve types and bindings of variables, methods and expressions....");
		this.potentialExternalDependencies = inferer.resolveAllBindings(callAsImpl,this);
		if (getExternalDependencies().size() > 0) {
			System.out.println("There are " + getExternalDependencies().size() + " items are potential external dependencies.");
		}
		System.out.println("types and bindings resolved successfully...");
	}



	private final void parseAllFiles() {
		System.out.println("Start parsing files...");
		Set<String> phase2Files = new HashSet<>();
		FileTraversal fileTransversal = new FileTraversal(new FileTraversal.IFileVisitor() {
			@Override
			public void visit(File file) {
				String fileFullPath = file.getAbsolutePath();
				fileFullPath = FileUtil.uniqFilePath(fileFullPath);
				if (!fileFullPath.startsWith(inputSrcPath)) {
					return;
				}
				if (isPhase2Files(fileFullPath)) {

				} else {
					parseFile(fileFullPath);
				}
			}

		});
		fileTransversal.extensionFilter(this.fileSuffixes());
		fileTransversal.travers(this.inputSrcPath);
		for (String f : phase2Files) {
			parseFile(f);
		}
		System.out.println("all files procceed successfully...");

	}

	protected void parseFile(String fileFullPath) {
		FileParser fileParser = createFileParser(fileFullPath);
		try {
			System.out.println("parsing " + fileFullPath + "...");
			fileParser.parse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("error occoured during parse file " + fileFullPath);
			e.printStackTrace();
		}
	}

	protected boolean isPhase2Files(String fileFullPath) {
		return false;
	}

	public List<String> includePaths() {
		if (this.includePaths ==null) {
			this.includePaths = buildIncludePath();
		}
		return includePaths;
	}

	private List<String> buildIncludePath() {
		includePaths = new ArrayList<String>();
		for (String path : includeDirs) {
			if (FileUtils.fileExists(path)) {
				path = FileUtil.uniqFilePath(path);
				if (!includePaths.contains(path))
					includePaths.add(path);
			}
			path = this.inputSrcPath + File.separator + path;
			if (FileUtils.fileExists(path)) {
				path = FileUtil.uniqFilePath(path);
				if (!includePaths.contains(path))
					includePaths.add(path);
			}
		}
		return includePaths;
	}


	public EntityRepo getEntityRepo() {
		return this.entityRepo;
	}


	public abstract List<String> supportedRelations();

	public Set<UnsolvedBindings> getExternalDependencies() {
		return potentialExternalDependencies;
	}

	public String getRelationMapping(String relation) {
		return relation;
	}

	public boolean isEagerExpressionResolve(){
		return false;
	}
}
