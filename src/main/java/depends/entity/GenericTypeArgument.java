package depends.entity;

import java.util.ArrayList;
import java.util.List;

public class GenericTypeArgument {
	String name;
	List<GenericTypeArgument> arguments = new ArrayList<>();
	public GenericTypeArgument(String name) {
		this.name = name;
	}
	public boolean contains(String rawType) {
		if (name.equals(rawType)) return true;
		return false;
	}
	public String getName() {
		return name;
	}
}
