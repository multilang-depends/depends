package depends.matrix;

import depends.entity.Entity;

public class DependencyValue{
	private int weight;
	private String type;
	private StringBuffer dependencyDetail;
	public DependencyValue(String type) {
		this.type = type;
		this.weight=0;
		dependencyDetail = new StringBuffer();
	}

	public void addDependency(Entity from, Entity to) {
		this.weight++;
		dependencyDetail.append("[").append(type).append("]")
		.append(from.getQualifiedName()).append("->").append(to.getQualifiedName())
		.append("\n");
	}

	public int getWeight() {
		return weight;
	}

	public String getType() {
		return type;
	}

	public String getDetails() {
		return dependencyDetail.toString();
	}
}