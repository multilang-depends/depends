package depends.entity.repo;

public class EntityNotExistsException extends Exception {

	private static final long serialVersionUID = 8897694627699543193L;

	public EntityNotExistsException(int entityId) {
		super("given id "+ entityId + " not exists.");
	}

}
