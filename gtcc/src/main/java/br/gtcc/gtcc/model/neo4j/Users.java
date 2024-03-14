package br.gtcc.gtcc.model.neo4j;

import java.util.Date;
import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import br.gtcc.gtcc.model.nitriteid.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Node
public class Users {

    @Id 
    @GeneratedValue 
    private String Id;
    
    @Property("tagline")
    private String name;
    
    @Property("tagline")
    private String email;
    
    @Relationship(type = "ACTED_IN", direction = Direction.INCOMING) 
    private List<UserType> userType;
    
    @Property("tagline") 
    private Date birthdate;

    @Property("tagline") 
    private int cellphone;

}
