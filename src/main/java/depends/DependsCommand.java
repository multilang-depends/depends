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

import java.util.ArrayList;
import java.util.List;

import depends.deptypes.DependencyType;
import depends.extractor.LangProcessorRegistration;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "depends")
public class DependsCommand {
	public static class SupportedLangs extends ArrayList<String> {
		private static final long serialVersionUID = 1L;
		public SupportedLangs() { super( LangProcessorRegistration.getRegistry().getLangs()); }
	}
	
	public static class SupportedTypes extends ArrayList<String> {
		private static final long serialVersionUID = 1L;
		public SupportedTypes() { super( DependencyType.allDependencies()); }
	}
	
	@Parameters(index = "0", completionCandidates = DependsCommand.SupportedLangs.class, description = "The lanauge of project files: [${COMPLETION-CANDIDATES}]")
    private String lang;
	@Parameters(index = "1", description = "The directory to be analyzed")
    private String src;
	@Parameters(index = "2",  description = "The output file name")
	private String output;
    @Option(names = {"-f", "--format"},split=",",  description = "the output format: [json(default),xml,excel,detail,dot,plantuml]")
    private String[] format=new String[]{"json"};
	@Option(names = {"-d", "--dir"},  description = "The output directory")
	private String dir;
	@Option(names = {"-m", "--map"},  description = "Output DV8 dependency map file.")
    private boolean dv8map = true;
	@Option(names = {"-s", "--strip-leading-path"},  description = "Strip the leading path.")
    private boolean stripLeadingPath = false;
	@Option(names = {"--strip-paths"}, split=",", description =  "The path(s) to be stripped. if -s enabled, the path(s) start after <src>. "
			+ "Otherwise, the path(s) should be valid.")
	private String[] strippedPaths = new String[]{};
	@Option(names = {"-g", "--granularity"},  description = "Granularity of dependency.[file(default),method,L#(the level of folder. e.g. L1=1st level folder)]")
    private String granularity="file";
	@Option(names = {"-p", "--namepattern"},  description = "The name path pattern.[dot(.), unix(/) or windows(\\)")
    private String namePathPattern="";
	@Option(names = {"-i","--includes"},split=",", description = "The files of searching path")
    private String[] includes = new String[] {};
	@Option(names = {"--auto-include"},split=",", description = "auto include all paths under the source path (please notice the potential side effect)")
	private boolean autoInclude = false;
	@Option(names = {"--detail"},split=",", description = "add detail dependency information to output (only applicable for JSON output format)")
	private boolean detail = false;	
	@Option(names = {"--auto-stub"},split=",", description = "create stub files for unsolved symbols (exprimental feature, only for java)")
	private boolean autoStub = false;	
	@Option(names = {"--type-filter"},split=",",  completionCandidates = DependsCommand.SupportedTypes.class, description = "only filter the listed dependency types[${COMPLETION-CANDIDATES}]")
    private String[] typeFilter=new String[]{};
	@Option(names = {"--external-deps"}, description = "Output external dependencies")
	private boolean outputExternalDependencies = false;	
	@Option(names = {"--duck-typing-deduce"}, description = "Deduce implicit variable types")
	private boolean duckTypingDeduce = true;	
	@Option(names = {"-h","--help"}, usageHelp = true, description = "display this help and exit")
    boolean help;
	public DependsCommand() {
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getOutputName() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String[] getFormat() {
		return format;
	}
	public String getOutputDir() {
		if (dir==null) {
			dir = System.getProperty("user.dir");
		}
		return dir;
	}
	public boolean isDv8map() {
		return dv8map;
	}
	public String[] getIncludes() {
		return includes;
	}
	public boolean isHelp() {
		return help;
	}
    public String getGranularity() {
		return granularity;
	}
	public String getNamePathPattern() {
		return namePathPattern;
	}
	public boolean isStripLeadingPath() {
		return stripLeadingPath;
	}
	
	public boolean isAutoInclude () {
		return autoInclude;
	}
	public boolean isDetail () {
		return detail;
	}
	public String[] getStrippedPaths() {
		return strippedPaths;
	}
	public void setStrippedPaths(String[] strippedPaths) {
		this.strippedPaths = strippedPaths;
	}
	public boolean isAutoStub() {
		return autoStub;
	}
	public List<String> getTypeFilter() {
		if (typeFilter.length==0) {
			return DependencyType.allDependencies();
		}
		return java.util.Arrays.asList(typeFilter);
	}
	public boolean isOutputExternalDependencies() {
		return outputExternalDependencies;
	}
	
	public boolean isDuckTypingDeduce() {
		return this.duckTypingDeduce;
	}
}
