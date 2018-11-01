package x;
import x.Parent.Enum;
import java.io;

import java.util.List;
class Parent2 {
	enum Enum{
		a,b
	}
}

 public class GenericTypeTest{
	List<Parent2.Enum> v;
	
	 public <D> File write(D... data) throws IOException {
	 }

}