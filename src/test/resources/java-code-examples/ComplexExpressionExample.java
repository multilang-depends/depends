package test;
class ClassX{
	Integer m;
}
class ClassA{
	Integer t;
	ClassX x;
	int foo();
}

public class ComplexExpressionExample {
	void setExample(ClassA a) { 	      //parameter a(1)
		(new ClassA()).x.m = 3;      //set (newA) x, m (2)
		a.x.m = 1;                        //set m (1), use a, x? or set a,x?
		ClassA a2 = new ClassA();             //def a2 (1), set a2
		(ClassA)a.foo();
		a2.x.m = 1;             //set a2, set x, set m (3)
		ClassA a3[] = new ClassA[10];          //def a3(1)
		a3[1].x.m = 3; //still lack 1          //set a3,t(2)
	}
}

