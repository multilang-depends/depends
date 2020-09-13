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

package depends.extractor.golang;

import depends.entity.repo.BuiltInType;

public class GoBuiltInType extends BuiltInType {

    public GoBuiltInType() {
        super.createBuiltInTypes();
    }

    @Override
    public String[] getBuiltInTypeStr() {
        return new String[]{
                "<Built-in>",
                "break", "default", "func", "interface",
				"select", "case", "defer", "go", "map", "struct", "chan",
				"else", "goto", "package", "switch", "const", "fallthrough",
				"if", "range", "type", "continue", "for", "import", "return",
				"var", "append", "bool", "byte", "cap", "close", "complex",
				"complex64", "complex128", "uint16", "copy", "false", "float32",
				"float64", "imag", "int", "int8", "int16", "uint32", "int32",
				"int64", "iota", "len", "make", "new", "nil", "panic", "uint64",
				"print", "println", "real", "recover", "string", "true", "uint",
				"uint8", "uintptr",
				"_"
        };
    }

    @Override
    public String[] getBuiltInPrefixStr() {
        return new String[]{
        };
    }

    @Override
    public String[] getBuiltInMethods() {
        return new String[]{};
    }

}
