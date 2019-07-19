package depends.persistent.neo4j.executor;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.Relationship;

public class Person extends Entity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
   @Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
   private Set<Person> friends = new HashSet<>();
   public void addFriend(Person p) {
	   this.friends.add(p);
   }
	
}
