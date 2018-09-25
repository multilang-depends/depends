package test;
class ClassX{
	Integer m;
}
class ClassA{
	Integer t;
	ClassX x;
}

public class FieldAccessInferExample {
	void setExample(ClassA a) { 	      //parameter a(1)
//		a.x.m = 1;               //set a, set x, set m (3)
//		ClassA a2 = new ClassA();             //def a2 (1)
//		a2.x.m = 1;             //set a2, set x, set m (3)
//		(new ClassA()).x.m = 3;      //set (newA) x, m (2)
		ClassA a3[] = new ClassA[10];          //def a3(1)
		a3[1].x.m = 3; //still lack 1          //set a3,t(2)
	}
}