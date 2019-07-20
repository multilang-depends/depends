package depends.entity.repo;

public class SimpleIdGenerator implements IdGenerator {

	private int nextAvaliableIndex;
	public SimpleIdGenerator() {
		nextAvaliableIndex = 0;
	}
	/**
	 * Generate a global unique ID for entity
	 * @return the unique id
	 */
	@Override
	public Integer generateId() {
		return nextAvaliableIndex++;
	}

}
