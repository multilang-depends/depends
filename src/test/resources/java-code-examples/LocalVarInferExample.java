
@Autowired public class LocalVarInferExample {
	String a;
	void setExample(String b) { //param
		Integer x; //define
		x=1; //Set x
		b="2"; //Set b
		x=b.length(); //Set x
		(x=b)+=1; //Set x
		++x; //Set x
		x--; //Set x
		--x; //Set x
	}
}