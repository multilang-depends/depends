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
	public static final String ANNOTATION = "Annotation";
	public static final String MIXIN = "MixIn";

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
		return depedencyTypes;
	}
}
