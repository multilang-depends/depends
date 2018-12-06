package depends.format.matrix;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DependencyPair {
	private Integer from;
	private Integer to;
	HashMap<String, DependencyValue> dependencies;
	public DependencyPair(Integer from, Integer to) {
		this.from = from;
		this.to= to;
		dependencies = new HashMap<>();
	}
	public static String key(Integer from, Integer to) {
		return ""+from+"-->"+to;
	}
	public void addDependency(String depType) {
		if (dependencies.get(depType)==null)
			dependencies.put(depType, new DependencyValue(depType));
		DependencyValue value = dependencies.get(depType);
		value.addDependency();
	}
	public Integer getFrom() {
		return from;
	}
	public Integer getTo() {
		return from;
	}
	public Collection<DependencyValue> getDependencies() {
		return dependencies.values();
	}
}
