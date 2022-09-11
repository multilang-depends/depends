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

import depends.entity.FunctionCall;
import depends.entity.TypeEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BuiltInType {

	public BuiltInType(){
		createBuiltInTypes();
	}
	/**
	 * Init the build in types data
	 */
	private void createBuiltInTypes() {
    	for(String prefix: getBuiltInTypePrefix()) {
    		builtInPrefix.add(prefix);
    	}
    	for (String type: getBuiltInTypeName()) {
    		builtInType.add(type);
    	}		
    	for (String method:getBuiltInMethods()) {
    		builtInMethod.add(method);
    	}
	}
	
	protected String[] getBuiltInMethods(){return new String[]{};}
	protected String[] getBuiltInTypeName(){return new String[]{};}
	protected String[] getBuiltInTypePrefix() {return new String[]{};}

	private Set<String> builtInType = new HashSet<>();
	private Set<String> builtInPrefix = new HashSet<>();
	private Set<String> builtInMethod = new HashSet<>();

	/**
	 * To determine whether a type name is built-in
	 * @param typeName
	 * @return
	 */
	public boolean isBuiltInType(String typeName) {
		return TypeEntity.buildInType.getRawName().uniqName().equals(typeName) ||
				builtInType.contains(typeName)||
				isBuiltInTypePrefix(typeName);
	}

	/**
	 * To determine a typeName is a built-in type based on prefix.
	 * For example, in Java language, name start with java.*, javax.*, com.sun.*
	 * is build-in types
	 * @param typeName
	 * @return
	 */
	private boolean isBuiltInTypePrefix(String typeName) {
		for (String prefix:builtInPrefix) {
			if (typeName.startsWith(prefix)) return true;
		}
		return false;
	}

	/**
	 * In some language, there are common methods, like in ruby,
	 * object_id is a method for all type
	 * @param name
	 * @return
	 */
	public boolean isBuildInMethod(String name) {
		return builtInMethod.contains(name); 
	}

	/**
	 * Used by duck typing deduce feature:
	 *    - if all calls of a type are build in method,
	 *      then no duck typing is deduced
	 * Refer to Python built-in type for example
	 *
	 * @param functionCalls
	 * @return
	 */
	public  boolean isBuildInTypeMethods(List<FunctionCall> functionCalls) {
		return false;
	}

}
