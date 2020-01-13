package depends.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericName implements Serializable{
	private static final long serialVersionUID = 1L;
	private char[] name;
	List<GenericName> arguments;
	public GenericName(String name) {
		this.name = name.toCharArray();
	}
	public GenericName(String name, List<GenericName> arguments) {
		this.name = name.toCharArray();
		this.arguments = arguments;
	}
	public boolean contains(String rawType) {
		if (new String(name).contains(rawType)) return true;
		return false;
	}
	public String getName() {
		return new String(name);
	}
	public List<GenericName> getArguments() {
		if (arguments==null) return new ArrayList<>();
		return arguments;
	}
	
	@Override
	public String toString() {
		return new String(name) + (getArguments().size()>0?"(" + arguments + ")":"");
	}
	
	public GenericName replace(String from, String to) {
		name = new String(name).replace(from, to).toCharArray();
		for (GenericName arg:getArguments()) {
			arg.replace(from, to);
		}
		return this;
	}
	
	public boolean startsWith(String prefix) {
		if (name==null) return false;
		return new String(name).startsWith(prefix);
	}
	public String uniqName() {
		if (getArguments().size()==0) return new String(name);
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (getArguments().size()>0) {
			for (GenericName arg:getArguments()) {
				sb.append("__").append(arg.uniqName()).append("__");
			}		
		}
		return sb.toString();
	}
	public GenericName substring(int start) {
		return new GenericName(new String(this.name).substring(start));
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
		if (this.arguments==null) this.arguments = new ArrayList<>();
		this.arguments.addAll(parameters);
	}
	public void appendArguments(GenericName parameter) {
		if (this.arguments==null) this.arguments = new ArrayList<>();
		this.arguments.add(parameter);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + Arrays.hashCode(name);
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
		if (this.getArguments() == null) {
			if (other.getArguments() != null)
				return false;
		} else if (!getArguments().equals(other.getArguments()))
			return false;
		if (!Arrays.equals(name, other.name))
			return false;
		return true;
	}	
	
	
}
