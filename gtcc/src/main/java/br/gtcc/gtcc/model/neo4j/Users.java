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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private String id;                                  // id elementId do neo4j

    @NonNull
    private String name;                                // nome do usuário

    @NonNull
    private String email;                               // email do usuário    

    //@Relationship(type = "HAS_USER_TYPE", direction = Direction.OUTGOING)
    @NonNull
    private Set<UserType> userType = new HashSet<>();  // Tipo de usuário 

    @NonNull
    private Date birthdate;                            // Data de aniversário

    @NonNull
    private String cellphone;                          // Telefone do usuário 

}
