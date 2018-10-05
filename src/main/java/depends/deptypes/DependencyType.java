package depends.deptypes;

import java.util.ArrayList;

public class DependencyType {
    public static final String RELATION_RECEIVE = "Receive";
    public static final String RELATION_IMPLEMENT = "Implement";
    public static final String RELATION_IMPORT = "Import";
    public static final String RELATION_CALL = "Call";
    public static final String RELATION_PARAMETER = "Parameter";
    public static final String RELATION_RETURN = "Return";
    public static final String RELATION_SET = "Set";
    public static final String RELATION_USE = "Use";
    public static final String RELATION_INHERIT = "Extend";
	public static final String RELATION_DEFINE = "Define";
	public static final String RELATION_CREATE = "Create";

    /**
     * Mask of dependencies.
     * 0 - IMPORT
     * 1 - INHERIT
     * 2 - IMPLEMENT
     * 3 - RECEIVE
     * 4 - CALL
     * 5 - SET
     * 6 - USE
     * 7 - PARAMETER
     * 8 - RETURN 
     * @param depMask
     * @return
     */
	public static final ArrayList<String> resolveMask(String depMask) {
        ArrayList<String> depedencyTypes = new ArrayList<String>();
        for(int i = 0; i < depMask.toCharArray().length; i++) {
            if(depMask.toCharArray()[i] == '1') {
                if(i == 0) {
                    depedencyTypes.add(RELATION_IMPORT);
                }
                else if (i == 1) {
                    depedencyTypes.add(RELATION_INHERIT);
                }
                else if (i == 2) {
                    depedencyTypes.add(RELATION_IMPLEMENT);
                }
                else if (i == 3) {
                    depedencyTypes.add(RELATION_RECEIVE);
                }
                else if (i == 4) {
                    depedencyTypes.add(RELATION_CALL);
                }
                else if (i == 5) {
                    depedencyTypes.add(RELATION_SET);
                }
                else if (i == 6) {
                    depedencyTypes.add(RELATION_USE);
                }
                else if (i == 7) {
                    depedencyTypes.add(RELATION_PARAMETER);
                }
                else if (i == 8) {
                    depedencyTypes.add(RELATION_RETURN);
                }
            }
        }
        return depedencyTypes;
    }

}
