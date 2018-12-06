package depends.entity.types;

import java.util.UUID;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.Inferer;

public class AnonymousBlock extends ContainerEntity{
    public AnonymousBlock(Entity parent, Integer id) {
    	super(UUID.randomUUID().toString(),  parent, id);
	}
}
