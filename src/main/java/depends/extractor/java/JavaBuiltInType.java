package depends.extractor.java;

import depends.entity.repo.BuiltInType;

public class JavaBuiltInType extends BuiltInType{
	
	public JavaBuiltInType() {
        super.createBuiltInTypes();
	}
	@Override
	public String[] getBuiltInTypeStr() {
		return new String[]{
				"void","int","double","char","byte","boolean","long","short","float",
				"BigDecimal","Integer","Double","Char","Byte","Boolean","Long","Short","Float",
				"String","Object","Class","Exception","StringBuilder",
				"Appendable","AutoCloseable","Cloneable","Comparable","Iterable","Readable",
				"Runnable","Thread.UncaughtExceptionHandler","Boolean","Byte","Character","Character.Subset",
				"Character.UnicodeBlock","ClassLoader","ClassValue","Compiler","Double","Enum",
				"InheritableThreadLocal","Math","Number","Package","Process",
				"ProcessBuilder","ProcessBuilder.Redirect","Runtime","RuntimePermission",
				"SecurityManager","StackTraceElement","StrictMath","StringBuffer",
				"System","Thread","ThreadGroup","ThreadLocal","Throwable","Void","ProcessBuilder.Redirect.Type",
				"Thread.State","ArithmeticException","ArrayIndexOutOfBoundsException",
				"ArrayStoreException","ClassCastException","ClassNotFoundException","CloneNotSupportedException",
				"EnumConstantNotPresentException","Exception","IllegalAccessException","IllegalArgumentException",
				"IllegalMonitorStateException","IllegalStateException","IllegalThreadStateException",
				"IndexOutOfBoundsException","InstantiationException","InterruptedException",
				"NegativeArraySizeException","NoSuchFieldException","NoSuchMethodException","NullPointerException",
				"NumberFormatException","ReflectiveOperationException","RuntimeException","SecurityException",
				"StringIndexOutOfBoundsException","TypeNotPresentException","UnsupportedOperationException","AbstractMethodError",
				"AssertionError","BootstrapMethodError","ClassCircularityError","ClassFormatError","Error","ExceptionInInitializerError",
				"IllegalAccessError","IncompatibleClassChangeError","InstantiationError","InternalError","LinkageError","NoClassDefFoundError"
				,"NoSuchFieldError","NoSuchMethodError","OutOfMemoryError","StackOverflowError","ThreadDeath","UnknownError",
				"UnsatisfiedLinkError","UnsupportedClassVersionError","VerifyError","VirtualMachineError","Deprecated","Override",
				"SafeVarargs","SuppressWarnings",
				"Collection","Comparator","Deque","Enumeration","EventListener","Formattable","Iterator","List",
				"ListIterator","Map","Map.Entry","NavigableMap","NavigableSet","Observer","Queue","RandomAccess",
				"Set","SortedMap","SortedSet","AbstractCollection","AbstractList","AbstractMap","AbstractMap.SimpleEntry",
				"AbstractMap.SimpleImmutableEntry","AbstractQueue","AbstractSequentialList","AbstractSet","ArrayDeque",
				"ArrayList","Arrays","BitSet","Calendar","Collections","Currency","Date","Dictionary","EnumMap","EnumSet",
				"EventListenerProxy","EventObject","FormattableFlags","Formatter","GregorianCalendar","HashMap","HashSet",
				"Hashtable","IdentityHashMap","LinkedHashMap","LinkedHashSet","LinkedList","ListResourceBundle","Locale",
				"Locale.Builder","Objects","Observable","PriorityQueue","Properties","PropertyPermission",
				"PropertyResourceBundle","Random","ResourceBundle","ResourceBundle.Control","Scanner",
				"ServiceLoader","SimpleTimeZone","Stack","StringTokenizer","Timer","TimerTask","TimeZone",
				"TreeMap","TreeSet","UUID","Vector","WeakHashMap","Formatter.BigDecimalLayoutForm",
				"Locale.Category","ConcurrentModificationException","DuplicateFormatFlagsException",
				"EmptyStackException","FormatFlagsConversionMismatchException","FormatterClosedException",
				"IllegalFormatCodePointException","IllegalFormatConversionException","IllegalFormatException",
				"IllegalFormatFlagsException","IllegalFormatPrecisionException","IllegalFormatWidthException",
				"IllformedLocaleException","InputMismatchException","InvalidPropertiesFormatException","MissingFormatArgumentException",
				"MissingFormatWidthException","MissingResourceException","NoSuchElementException","TooManyListenersException",
				"UnknownFormatConversionException","UnknownFormatFlagsException","ServiceConfigurationError",
				"<Built-in>"
		};
	}
	@Override
	public String[] getBuiltInPrefixStr() {
		return new String[]{
				"java.","javax.","com.sun."
		};
	}
	@Override
	public String[] getBuiltInMethods() {
		return new String[]{};
	}
	
}
