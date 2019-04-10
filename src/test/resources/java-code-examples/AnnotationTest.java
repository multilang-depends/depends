import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationTest {
  String value();
}

@AnnotationTest
class TheClass{

	@AnnotationTest
	TheClass(){}
	
	@AnnotationTest
	int theField;
	

}


class TheFunction{
	@AnnotationTest
	void foo() {}
	void bar() {}
}


@AnnotationTest
enum TheEnum{
	
}

@AnnotationTest
interface TheInterface{
	@AnnotationTest
	void foo() {}
	
	@AnnotationTest
	int theConst = 5;
}

