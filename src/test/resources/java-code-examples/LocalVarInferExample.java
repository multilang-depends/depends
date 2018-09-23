
public class LocalVarInferExample {
	String a;
	void setExample(String b) {
		Integer x;
		x=1; //Set x
		b="2"; //Set b
		x=b.length(); //Set x
		x++; //Set x
		++x; //Set x
		x--; //Set x
		--x; //Set x
	}
}