package br.gtcc.gtcc.model.neo4j;

import br.gtcc.gtcc.model.UserType;
import java.util.Date;
import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;
//import br.gtcc.gtcc.model.UserType;
import io.micrometer.common.lang.NonNull;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users {

    @Id
    @GeneratedValue
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    //@Relationship(type = "HAS_USER_TYPE", direction = Direction.OUTGOING)
    private Set<UserType> userType = new HashSet<>();

    @NonNull
    private Date birthdate;

    @NonNull
    private int cellphone;

}
