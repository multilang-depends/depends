package depends.entity;

import java.util.ArrayList;
import java.util.List;

public class GenericName {
	String name;
	List<GenericName> arguments = new ArrayList<>();
	public GenericName(String name) {
		this.name = name;
	}
	public GenericName(String name, List<GenericName> subTypes) {
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
	public List<GenericName> getArguments() {
		return arguments;
	}
	
	@Override
	public String toString() {
		return name + "(" + arguments + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericName other = (GenericName) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public GenericName replace(String from, String to) {
		name = name.replace(from, to);
		for (GenericName arg:arguments) {
			arg.replace(from, to);
		}
		return this;
	}
	public boolean startsWith(String prefix) {
		return name.startsWith(prefix);
	}
	public String getUniqueName() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		for (GenericName arg:arguments) {
			sb.append("__").append(arg.getUniqueName()).append("__");
		}		
		return sb.toString();
	}
	public GenericName substring(int start) {
		this.name = this.name.substring(start);
		return this;
	}
}
