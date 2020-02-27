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

package depends.entity.repo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.entity.FunctionCall;
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
		if (Inferer.buildInType.getRawName().uniqName().equals(type)) return true;
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

	public  boolean isBuildInTypeMethods(List<FunctionCall> functionCalls) {
		return false;
	}

}
