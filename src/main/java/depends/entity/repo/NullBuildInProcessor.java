package depends.entity.repo;

import java.io.IOException;

import depends.extractor.FileParser;

public class NullBuildInProcessor extends FileParser {

	@Override
	public void parse() throws IOException {
	}

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}

}
