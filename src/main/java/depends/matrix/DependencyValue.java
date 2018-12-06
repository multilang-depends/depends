package depends.matrix;

public class DependencyValue{
	private int weight;
	private String type;

	public DependencyValue(String type) {
		this.type = type;
		this.weight=0;
	}

	public void addDependency() {
		this.weight++;
	}

	public int getWeight() {
		return weight;
	}

	public String getType() {
		return type;
	}
}