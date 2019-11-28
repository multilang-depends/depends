package depends.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericName implements Serializable{
	private static final long serialVersionUID = 1L;
	String name;
	List<GenericName> arguments = new ArrayList<>();
	public GenericName(String name) {
		this.name = name;
	}
	public GenericName(String name, List<GenericName> arguments) {
		this.name = name;
		this.arguments = arguments;
	}
	public boolean contains(String rawType) {
		if (name.contains(rawType)) return true;
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
		return name + (arguments.size()>0?"(" + arguments + ")":"");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null || arguments.size()==0) ? 0 : arguments.hashCode());
		result = prime * result + ((name == null ) ? 0 : name.hashCode());
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
		if (name==null) return false;
		return name.startsWith(prefix);
	}
	public String uniqName() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (arguments.size()>0) {
			for (GenericName arg:arguments) {
				sb.append("__").append(arg.uniqName()).append("__");
			}		
		}
		return sb.toString();
	}
	public GenericName substring(int start) {
		this.name = this.name.substring(start);
		return this;
	}
	public boolean isNull() {
		return name==null;
	}
	public static GenericName build(String name) {
		if (name==null) return null;
		return new GenericName(name);
	}
	public static GenericName build(String name, List<GenericName> arguments) {
		return new GenericName(name,arguments);
	}
	public boolean find(GenericName rawType) {
		//if (this.equals(rawType)) return true;
		for (GenericName subType:this.getArguments()) {
			if (subType.equals(rawType)) return true;
			boolean found = subType.find(rawType);
			if (found) return true;
		}
		return false;
	}
	public void appendArguments(List<GenericName> parameters) {
		this.arguments.addAll(parameters);
	}
	public void appendArguments(GenericName parameter) {
		this.arguments.add(parameter);
	}	
}
