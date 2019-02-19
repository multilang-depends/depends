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

package depends.extractor.cpp;

import depends.entity.repo.BuiltInType;

public class CppBuiltInType extends BuiltInType {

	public CppBuiltInType() {
		super.createBuiltInTypes();
	}

	@Override
	public String[] getBuiltInTypeStr() {
		return new String[] { "alignas", "alignof", "asm", "auto", "bool", "break", "case", "catch", "char",
				"char16_t", "char32_t", "class", "const", "constexpr", "const_cast", "continue", "decltype",
				"default", "delete", "do", "double", "dynamic_cast", "else", "enum", "explicit", "export", "extern",
				"false", "final", "float", "for", "friend", "goto", "if", "inline", "int", "long", "mutable",
				"namespace", "new", "noexcept", "nullptr", "operator", "override", "private", "protected", "public",
				"register", "reinterpret_cast", "return", "short", "signed", "sizeof", "static", "static_assert",
				"static_cast", "struct", "switch", "template", "this", "thread_local", "throw", "true", "try",
				"typedef", "typeid", "typename", "union", "unsigned", "using", "virtual", "void", "volatile",
				"wchar_t", "while", "<Built-in>",
				"__cplusplus","_cpp_aggregate_bases","__cpp_aggregate_nsdmi","__cpp_alias_templates","__cpp_aligned_new",
				"__cpp_attributes","__cpp_binary_literals","__cpp_capture_star_this","__cpp_constexpr","__cpp_decltype",
				"__cpp_decltype_auto","__cpp_deduction_guides","__cpp_delegating_constructors",
				"__cpp_enumerator_attributes","__cpp_explicit_bool","__cpp_fold_expressions","__cpp_generic_lambdas",
				"__cpp_guaranteed_copy_elision","__cpp_hex_float","__cpp_if_constexpr","__cpp_inheriting_constructors",
				"__cpp_init_captures","__cpp_initializer_lists","__cpp_inline_variables","__cpp_lambdas",
				"__cpp_namespace_attributes","__cpp_noexcept_function_type","__cpp_nontype_template_args",
				"__cpp_nontype_template_parameter_auto","__cpp_nontype_template_parameter_class","__cpp_nsdmi"
						+ "","__cpp_range_based_for","__cpp_raw_strings","__cpp_ref_qualifiers","__cpp_return_type_deduction"
						,"__cpp_rvalue_references","__cpp_sized_deallocation","__cpp_static_assert","__cpp_structured_bindings",
						"__cpp_template_template_args","__cpp_threadsafe_static_init","__cpp_unicode_characters","__cpp_unicode_literals",
						"__cpp_user_defined_literals","__cpp_variable_templates","__cpp_variadic_templates","__cpp_variadic_using",
						"__DATE__","__FILE__","__LINE__","__STDC__","__STDC_ANALYZABLE__","__STDC_HOSTED__","__STDC_IEC_559__",
						"__STDC_IEC_559_COMPLEX__","__STDC_ISO_10646__","__STDC_LIB_EXT1__","__STDC_MB_MIGHT_NEQ_WC__",
						"__STDC_NO_ATOMICS__","__STDC_NO_COMPLEX__","__STDC_NO_THREADS__","__STDC_NO_VLA__",
						"__STDCPP_DEFAULT_NEW_ALIGNMENT__","__STDCPP_STRICT_POINTER_SAFETY__","__STDCPP_THREADS__",
						"__STDC_UTF_16__","__STDC_UTF_32__","__STDC_VERSION__","__TIME__"
				};
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[] {"__"};
	}
	@Override
	public String[] getBuiltInMethods() {
		return new String[]{};
	}

}