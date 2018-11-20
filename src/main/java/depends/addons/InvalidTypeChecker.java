package depends.addons;

import java.util.HashSet;

public class InvalidTypeChecker {
	private static InvalidTypeChecker _inst = new InvalidTypeChecker();
	HashSet<String> invTypes = new HashSet();
	public static InvalidTypeChecker getInst() {
		return _inst;
	}
	public InvalidTypeChecker() {
		for (String item:invalidTypes) {
			invTypes.add(item);
		}
	}

	public boolean isInvalid(String type) {
		return invTypes.contains(type);
	}

	public String[] invalidTypes = {
			"Mutex" };
}
