package depends.entity;

import java.util.UUID;

public class AnonymousBlock extends ContainerEntity{
    public AnonymousBlock(Entity parent, Integer id) {
    	super(UUID.randomUUID().toString(),  parent, id);
	}
}
