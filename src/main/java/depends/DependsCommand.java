package depends;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "depends")
public class DependsCommand {
	@Parameters(index = "0", description = "The lanauge of project files: [java, cpp]")
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
    @Option(names = {"-i","--includes"},split=",", description = "The files of searching path")
    private String[] includes = new String[] {};
    @Option(names = {"-h","--help"}, usageHelp = true, description = "display this help and exit")
    boolean help;
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

}
