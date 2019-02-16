package depends;

import java.util.ArrayList;
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
	
	@Parameters(index = "0", completionCandidates = DependsCommand.SupportedLangs.class, description = "The lanauge of project files: [${COMPLETION-CANDIDATES}]")
    private String lang;
	@Parameters(index = "1", description = "The directory to be analyzed")
    private String src;
	@Parameters(index = "2",  description = "The output file name")
	private String output;
    @Option(names = {"-f", "--format"},split=",",  description = "the output format: [json(default),xml,excel,detail(text format),dot]")
    private String[] format=new String[]{"json"};
	@Option(names = {"-d", "--dir"},  description = "The output directory")
	private String dir;
	@Option(names = {"-m", "--map"},  description = "Output DV8 dependency map file.")
    private boolean dv8map = true;
	@Option(names = {"-s", "--strip-leading-path"},  description = "Strip the leading path.")
    private boolean stripLeadingPath = false;
	@Option(names = {"-g", "--granularity"},  description = "Granularity of dependency.[file(default),method]")
    private String granularity="file";
	@Option(names = {"-p", "--namepattern"},  description = "The name path pattern.[default(/),dot(.)")
    private String namePathPattern="default";
	@Option(names = {"-i","--includes"},split=",", description = "The files of searching path")
    private String[] includes = new String[] {};
	@Option(names = {"--auto-include"},split=",", description = "auto include all paths under the source path (please notice the potential side effect)")
	private boolean autoInclude = false;
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
}
