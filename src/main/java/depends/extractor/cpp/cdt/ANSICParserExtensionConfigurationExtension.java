package depends.extractor.cpp.cdt;

import org.eclipse.cdt.core.dom.parser.c.ANSICParserExtensionConfiguration;

class ANSICParserExtensionConfigurationExtension extends ANSICParserExtensionConfiguration {

	@Override
	public boolean supportDeclspecSpecifiers() {
		return true;
	}

	@Override
	public boolean supportKnRC() {
		return true;
	}

}
