package depends.entity.repo;

import java.util.HashSet;
import java.util.Set;

import depends.relations.Inferer;

public abstract class BuiltInType {

	
	public void createBuiltInTypes() {
    	for(String prefix:getBuiltInPrefixStr()) {
    		builtInPrefix.add(prefix);
    	}
    	for (String type:getBuiltInTypeStr()) {
    		builtInType.add(type);
    	}		
    	for (String method:getBuiltInMethods()) {
    		builtInMethod.add(method);
    	}
	}
	
	public abstract String[] getBuiltInMethods();
	public abstract String[] getBuiltInTypeStr();
	public abstract String[] getBuiltInPrefixStr() ;

	private Set<String> builtInType = new HashSet<>();
	private Set<String> builtInPrefix = new HashSet<>();
	private Set<String> builtInMethod = new HashSet<>();

	public boolean isBuiltInType(String type) {
		if (Inferer.buildInType.getRawName().equals(type)) return true;
		return builtInType.contains(type); 
	}

	public boolean isBuiltInTypePrefix(String type) {
		for (String prefix:builtInPrefix) {
			if (type.startsWith(prefix)) return true;
		}
		return false;
	}
	

	public boolean isBuildInMethod(String name) {
		return builtInMethod.contains(name); 
	}

}
