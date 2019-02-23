package depends.matrix;

public class EmptyLeadingNameStripper implements ILeadingNameStrippper {
	@Override
	public String stripFilename(String path) {
		return path;
	}	
}
