package depends.relations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import depends.entity.FunctionCall;
import depends.entity.FunctionEntity;

public class FunctionMatcher {

	private HashSet<String> fnames;
	public FunctionMatcher(ArrayList<FunctionEntity> functions) {
		fnames = new HashSet<>();
		for (FunctionEntity f:functions) {
			this.fnames.add(f.getRawName());
		}
	}

	public boolean containsAll(List<FunctionCall> functionCalls) {
		for (FunctionCall fCall:functionCalls) {
			if (!fnames.contains(fCall.getRawName()))
				return false;
		}
		return true;
	}

}
