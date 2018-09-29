package depends.entity.types;

import java.util.UUID;

import depends.entity.ContainerEntity;

public class AnonymousBlock extends ContainerEntity{
    public AnonymousBlock( int parentId, Integer id) {
    	super(UUID.randomUUID().toString(),  parentId, id);
	}
}
