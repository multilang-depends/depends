package test;
class ClassX{
	Integer m;
}
class ClassA{
	Integer t;
	ClassX x;
}

public class FieldAccessInferExample {
	void setExample(ClassA a) {
		a.x.m = 1;
		ClassA a2 = new ClassA();
		a2.x.m = 1;
		(new ClassA()).x = 3;
	}
}