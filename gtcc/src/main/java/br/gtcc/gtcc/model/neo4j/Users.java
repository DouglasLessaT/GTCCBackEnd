package br.gtcc.gtcc.model.neo4j;

import java.util.Date;
import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import br.gtcc.gtcc.model.UserType;
import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Users")
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

    @Relationship(type = "HAS_USER_TYPE", direction = Direction.OUTGOING)
    private List<UserType> userType;

    @NonNull
    private Date birthdate;

    @NonNull
    private int cellphone;

    // Esta fução e para escrever o id- Atribuir. Ele cria uma nova entidade e
    // define o campo de acordo, sem modificar a entidade original, tornando-a
    // imutável.
    // public Users withId(Long id) {
    //     if (this.id.equals(id)) {
    //         return this;
    //     } else {
    //         Users users = new Users(this.id, this.name, this.email, userType, birthdate, cellphone);
    //         users.id = id;
    //         return users;
    //     }
    // }
}
