package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.parser.cpp.GPPParserExtensionConfiguration;

class GPPParserExtensionConfigurationExtension extends GPPParserExtensionConfiguration {

	@Override
	public boolean supportKnRC() {
		return true;
	}

	@Override
	public boolean supportParameterInfoBlock() {
		return true;
	}

}
