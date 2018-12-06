package depends.entity;

public class IdGenerator {
	private int nextAvaliableIndex;
	public IdGenerator() {
		nextAvaliableIndex = 0;
	}
	/**
	 * Generate a global unique ID for entity
	 * @return the unique id
	 */
	public Integer generateId() {
		return nextAvaliableIndex++;
	}
}
