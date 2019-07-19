package depends.persistent.neo4j.executor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator = JSOGGenerator.class)
abstract class Entity {

    @JsonProperty("id")
    private Long id;

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }
 
}