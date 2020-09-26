package depends.entity;

public class EntityNameBuilder {
	public static String build(Entity entity) {
		return entity.getQualifiedName();
	}
}
