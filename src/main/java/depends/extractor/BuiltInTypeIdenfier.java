package depends.extractor;

import java.util.HashSet;
import java.util.Set;

import depends.entity.TypeInfer;

public abstract class BuiltInTypeIdenfier {

	
	public void createBuiltInTypes() {
    	for(String prefix:getBuiltInPrefixStr()) {
    		builtInPrefix.add(prefix);
    	}
    	for (String type:getBuiltInTypeStr()) {
    		builtInType.add(type);
    	}		
	}
	
	public abstract String[] getBuiltInTypeStr();
	public abstract String[] getBuiltInPrefixStr() ;

	private Set<String> builtInType = new HashSet<>();
	private Set<String> builtInPrefix = new HashSet<>();

	public boolean isBuiltInType(String type) {
		if (TypeInfer.buildInType.getRawName().equals(type)) return true;
		return builtInType.contains(type); 
	}

	public boolean isBuiltInTypePrefix(String type) {
		for (String prefix:builtInPrefix) {
			if (type.startsWith(prefix)) return true;
		}
		return false;
	}

}
