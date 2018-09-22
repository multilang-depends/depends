package depends.entity.repo;

public class NoRequestedTypeOfAncestorExistsException extends Exception {

	private static final long serialVersionUID = -5951549776366770294L;

	public NoRequestedTypeOfAncestorExistsException(int entityId, Class classType) {
		super("the given type of " + classType.getCanonicalName() + " for "
		+ entityId + " does not exists.");
	}

}
