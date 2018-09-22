package depends.entity.types;

import java.util.UUID;
import depends.entity.Entity;

public class AnonymousBlock extends Entity{
    public AnonymousBlock( int parentId, Integer id) {
    	super(UUID.randomUUID().toString(),  parentId, id);
	}
}
