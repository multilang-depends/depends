package depends.entity;

import java.util.ArrayList;
import java.util.List;

import depends.relations.Inferer;

public class GenericTypeArgument {
	String name;
	List<GenericTypeArgument> arguments = new ArrayList<>();
	public GenericTypeArgument(String name) {
		this.name = name;
	}
	public GenericTypeArgument(String name, List<GenericTypeArgument> subTypes) {
		this.name = name;
		this.arguments = subTypes;
	}
	public boolean contains(String rawType) {
		if (name.equals(rawType)) return true;
		return false;
	}
	public String getName() {
		return name;
	}
	public List<GenericTypeArgument> getArguments() {
		return arguments;
	}
	
	@Override
	public String toString() {
		return name + "(" + arguments + ")";
	}

}
