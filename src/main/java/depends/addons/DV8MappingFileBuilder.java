package depends.addons;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import depends.deptypes.DependencyType;

class MappingValue{
	int family = 0;
	int vendor = 2;
	int type = 0;
	MappingValue(String orginalName, int typeId){
		this.type = typeId;
	}
	public int getFamily() {
		return family;
	}
	public void setFamily(int family) {
		this.family = family;
	}
	public int getVendor() {
		return vendor;
	}
	public void setVendor(int vendor) {
		this.vendor = vendor;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}

class MappingItem {
	String name;
	MappingValue id;
	public MappingItem(String dv8Name, int typeId) {
		this.name = dv8Name;
		String orginalName = dv8Name;
		this.id = new MappingValue(orginalName, typeId);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MappingValue getId() {
		return id;
	}
	public void setId(MappingValue id) {
		this.id = id;
	}
	
}

public class DV8MappingFileBuilder {

	public void create(String fileName)  {
		Map<String,MappingItem> values = new HashMap<>();
		ArrayList<String> dependencies = DependencyType.allDependencies();
    	for (int i=0;i<dependencies.size();i++) {
    		String dep = dependencies.get(i);
    		values.put(dep,new MappingItem(dep,i));
    	}
		ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), values);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
}
