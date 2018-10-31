package x;
import x.Parent.Enum;
class Parent {
	enum Enum{
		a,b
	}
}

class Parent2 {
	enum Enum{
		a,b
	}
}


public class EmbededTest{
	Parent.Enum v; 
}

class EmbededTest2{
	Parent2.Enum v; 
}