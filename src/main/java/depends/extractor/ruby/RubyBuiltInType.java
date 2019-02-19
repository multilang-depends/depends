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

package depends.extractor.ruby;

import depends.entity.repo.BuiltInType;

public class RubyBuiltInType extends BuiltInType {
	public RubyBuiltInType() {
		super.createBuiltInTypes();
	}

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {};
	}

	@Override
	public String[] getBuiltInMethods() {
		return new String[] { "+", "-", "*", "/", "**", "%", "&", "<", "<=", ">", ">=", "==", "!=", "===", "<<", ">>",
				"~", "!", "^", "new", "!~", "<=>", "===", "=~", "[]", "class", "clone", "define_singleton_method", "display",
				"dup", "enum_for", "eql?", "extend", "freeze", "frozen?", "inspect", "instance_of?",
				"instance_variable_defined?", "instance_variable_get", "instance_variable_set", "instance_variables",
				"is_a?", "itself", "kind_of?", "method", "methods", "nil?", "object_id", "private_methods",
				"protected_methods", "public_method", "public_methods", "public_send", "remove_instance_variable",
				"respond_to?", "respond_to_missing?", "send", "singleton_class", "singleton_method",
				"singleton_methods", "taint", "tainted?", "tap", "then", "to_enum", "to_s", "trust", "untaint",
				"untrust", "untrusted?", "yield_self","each","join","empty?","copy","size" };
	}

}
