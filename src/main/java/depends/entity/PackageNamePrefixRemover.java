package depends.entity;

public class PackageNamePrefixRemover {
	public static String remove(Entity entity) {
		PackageEntity pkg = (PackageEntity) entity.getAncestorOfType(PackageEntity.class);
		String name = entity.getQualifiedName();
		if (pkg != null) {
			try {
				name = name.substring(pkg.getQualifiedName().length() + 1);
			} catch (Exception e) {
				return name;
			}
		}
		return name;
	}

}
