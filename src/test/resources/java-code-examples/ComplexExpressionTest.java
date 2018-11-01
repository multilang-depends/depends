class B{
	static String c;
}

class ComplexExpressionTest{
	String foo() {
		return B.class.getName();
	}
	
	String bar() {
		B b;
		return b.getClass().getName();
	}
	String other() {
		return B.c;
	}
}