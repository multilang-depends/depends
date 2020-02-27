package depends.extractor.python;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import depends.entity.FileEntity;
import depends.entity.FunctionCall;
import depends.entity.FunctionEntity;
import depends.entity.GenericName;
import depends.entity.TypeEntity;
import depends.entity.repo.BuiltInType;
import depends.relations.FunctionMatcher;

public class PythonBuiltInType extends BuiltInType {

	public static String[] BUILT_IN_FUNCTIONS = { "abs", "delattr", "hash", "memoryview", "set", "all", "dict", "help",
			"min", "setattr", "any", "dir", "hex", "next", "slice", "exit", "ascii", "divmod", "id", "object", "sorted",
			"bin", "enumerate", "input", "oct", "staticmethod", "bool", "eval", "int", "open", "str", "breakpoint",
			"exec", "isinstance", "ord", "sum", "bytearray", "filter", "issubclass", "pow", "super", "bytes", "float",
			"iter", "print", "tuple", "callable", "format", "len", "property", "type", "chr", "frozenset", "list",
			"range", "vars", "classmethod", "getattr", "locals", "repr", "zip", "compile", "globals", "map", "reversed",
			"__import__", "complex", "hasattr", "max", "round" };

	/**
	 * methods of built-in String
	 */
	public static String[] BUILT_IN_STRING_METHODS = { "capitalize", "center", "casefold", "count", "endswith",
			"expandtabs", "encode", "find", "format", "index", "isalnum", "isalpha", "isdecimal", "isdigit",
			"isidentifier", "islower", "isnumeric", "isprintable", "isspace", "istitle", "isupper", "join", "ljust",
			"rjust", "lower", "upper", "swapcase", "lstrip", "rstrip", "strip", "partition", "maketrans", "rpartition",
			"translate", "replace", "rfind", "rindex", "split", "rsplit", "splitlines", "startswith", "title", "zfill",
			"format_map" };

	/**
	 * methods of built-in List
	 */
	public static String[] BUILT_IN_LIST_METHODS = { "index", "append", "extend", "insert", "remove", "count", "pop",
			"reverse", "sort", "copy", "clear" };

	/**
	 * methods of built-in Tuple
	 */
	public static String[] BUILT_IN_TUPLE_METHODS = { "index", "count" };

	/**
	 * methods of built-in Dict
	 */
	public static String[] BUILT_IN_DICT_METHODS = { "clear", "copy", "fromkeys", "get", "items", "keys", "popitem",
			"setdefault", "pop", "values", "update", };

	/**
	 * methods of built-in Set
	 */
	public static String[] BUILT_IN_SET_METHODS = { "remove", "add", "copy", "clear", "difference", "difference_update",
			"discard", "intersection", "intersection_update", "isdisjoint", "issubset", "pop", "symmetric_difference",
			"symmetric_difference_update", "union", "update" };

	/**
	 * methods of built-in File
	 */
	public static String[] BUILT_IN_FILE_METHOD = { "close", "flush", "fileno", "isatty", "next", "read", "readline",
			"readlines", "seek", "tell", "truncate", "write", "writelines" };
	
	List<TypeEntity> buildInTypes = new ArrayList<>();
	

	
	public PythonBuiltInType() {
		addBuildInType(BUILT_IN_FILE_METHOD);
		addBuildInType(BUILT_IN_SET_METHODS);
		addBuildInType(BUILT_IN_DICT_METHODS);
		addBuildInType(BUILT_IN_TUPLE_METHODS);
		addBuildInType(BUILT_IN_LIST_METHODS);
		addBuildInType(BUILT_IN_STRING_METHODS);
	}
	
	private void addBuildInType(String[] methods) {
		TypeEntity type  = new TypeEntity();
		for (String method:methods) {
			FunctionEntity func = new FunctionEntity(GenericName.build(method),type,-1,GenericName.build(""));
			type.addFunction(func);
		}
		buildInTypes.add(type);
	}

	@Override
	public String[] getBuiltInMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getBuiltInTypeStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getBuiltInPrefixStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public  boolean isBuildInTypeMethods(List<FunctionCall> functionCalls) {
		for (TypeEntity type:buildInTypes) {
			FunctionMatcher functionMatcher = new FunctionMatcher(type.getFunctions());
			if (functionMatcher.containsAll(functionCalls)) {
				return true;
			}
		}
		return false;
	}

}
