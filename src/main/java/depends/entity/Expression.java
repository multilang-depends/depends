package depends.entity;

import java.util.ArrayList;
import java.util.List;

import depends.util.Tuple;

public class Expression {
	public Integer id;
	public Integer parentId;
	
	public String text; // for debug purpose
	public String returnType; // the type we care
	public String identifier; // the varName, or method name, etc.
	public boolean isSet = false; // is a set relation from right to leftHand
	public boolean isDot = false; // is a dot expression, will decuce variable tfype left to right
	public boolean isCall = false;
	List<Tuple<String, String>> relations = new ArrayList<>();

	public Expression(Integer id, Integer parentId) {
		this.id = id;
		this.parentId = parentId;
		
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[").append(text).append("]").append("\n")
			.append("returnType=").append(returnType).append("\n")
			.append("var=").append(identifier).append("\n");

		for (Tuple<String, String> item : relations) {
			s.append(item.y).append("->").append(item.x).append(",");
		}
		return s.toString();
	}
}