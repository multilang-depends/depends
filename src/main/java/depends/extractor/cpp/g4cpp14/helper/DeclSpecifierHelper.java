package depends.extractor.cpp.g4cpp14.helper;

import depends.javaextractor.CPP14Parser.DeclspecifierContext;
import depends.javaextractor.CPP14Parser.DeclspecifierseqContext;

public class DeclSpecifierHelper {

	private DeclspecifierseqContext seq;

	public DeclSpecifierHelper(DeclspecifierseqContext seq) {
		this.seq = seq;
		analyse();
	}

	private void analyse() {
		if (seq.declspecifier()!=null) {
			for (DeclspecifierContext spec:seq.declspecifier()) {
				System.out.println(spec.getText());
			}
		}
		if (seq.attributespecifierseq()!=null) {
			System.out.println(seq.attributespecifierseq().getText());
		}
	}

}
