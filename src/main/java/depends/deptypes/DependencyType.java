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

package depends.deptypes;

import java.util.ArrayList;

public class DependencyType {
	public static final String IMPORT = "Import";
	public static final String CONTAIN = "Contain";
	public static final String IMPLEMENT = "Implement";
	public static final String INHERIT = "Extend";
	public static final String CALL = "Call";
	public static final String PARAMETER = "Parameter";
	public static final String RETURN = "Return";
	public static final String SET = "Set";
	public static final String USE = "Use";
	public static final String RECEIVE = "Receive";
	public static final String CREATE = "Create";
	public static final String CAST = "Cast";
	public static final String THROW = "Throw";
	public static final String IMPLLINK = "ImplLink";
	public static final String ANNOTATION = "Annotation";
	public static final String MIXIN = "MixIn";
	public static final String PomParent = "Parent";
	public static final String PomPlugin = "Plugin";
	public static final String PomDependency = "Dependency";
	

	public static ArrayList<String> allDependencies() {
		ArrayList<String> depedencyTypes = new ArrayList<String>();
		depedencyTypes.add(IMPORT);
		depedencyTypes.add(CONTAIN);
		depedencyTypes.add(IMPLEMENT);
		depedencyTypes.add(INHERIT);
		depedencyTypes.add(CALL);
		depedencyTypes.add(PARAMETER);
		depedencyTypes.add(RETURN);
		depedencyTypes.add(SET);
		depedencyTypes.add(CREATE);
		depedencyTypes.add(USE);
		depedencyTypes.add(RECEIVE);
		depedencyTypes.add(CAST);
		depedencyTypes.add(THROW);
		depedencyTypes.add(ANNOTATION);
		depedencyTypes.add(MIXIN);
		depedencyTypes.add(IMPLLINK);
		depedencyTypes.add(PomParent);
		depedencyTypes.add(PomPlugin);
		depedencyTypes.add(PomDependency);
		return depedencyTypes;
	}
}
