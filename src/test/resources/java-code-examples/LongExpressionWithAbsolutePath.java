package x;
class MyString{
	static void foo() {}
}

public class LongExpressionWithAbsolutePath {
	public void setExample(Object m) {
		x.MyString.foo();
	}
}