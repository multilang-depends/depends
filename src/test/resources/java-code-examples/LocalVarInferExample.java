class MyString{
	
}

class MyInteger{
	
}

@interface MyAnnotation{
	
}

@MyAnnotation public class LocalVarInferExample {
	MyString a;
	void setExample(MyString b) { //param
		MyInteger x; //define
		x=1; //Set x
		b="2"; //Set b
		x=b.length(); //Set x
		(x=b)+=1; //Set x
		++x; //Set x
		x--; //Set x
		--x; //Set x
	}
}