/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.addons;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

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

	private List<String> supportedRelations;

	public DV8MappingFileBuilder(List<String> supportedRelations) {
		this.supportedRelations = supportedRelations;
	}

	public void create(String fileName)  {
		Map<String,MappingItem> values = new HashMap<>();
    	for (int i=0;i<supportedRelations.size();i++) {
    		String dep = supportedRelations.get(i);
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
