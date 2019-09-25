package depends.extractor.cpp.cdt;

import static org.junit.Assert.*;

import org.junit.Test;

public class ASTStringUtilExtTest {

	@Test
	public void test() {
		String name = "std::tuple<T...>";
		assertEquals("std::tuple",ASTStringUtilExt.removeTemplateParameter(name));
	}

	@Test
	public void test2() {
		String name = "AbstractDomain<DisjointUnionAbstractDomain<FirstDomain, Domains...>>";
		assertEquals("AbstractDomain",ASTStringUtilExt.removeTemplateParameter(name));
	}

	
}
