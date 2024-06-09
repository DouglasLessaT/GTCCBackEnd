package br.gtcc.gtcc.model.neo4j;

import br.gtcc.gtcc.model.UserType;
import java.util.Date;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.lang.NonNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Node
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Users {

    @Id
    @GeneratedValue
    private String id;                                  

    @NonNull
    private String name;                                
    
    @NonNull
    private String email;                               
    
    @NonNull
    private Set<UserType> userType = new HashSet<>();   
    
    @NonNull
    private Date birthdate;                                
    
    @NonNull
    private String cellphone;                          
    
    @Relationship(type = "GERENCIA", direction = Relationship.Direction.OUTGOING)  
    private Set<Tcc> tccsGerenciados = new HashSet<>();

    // @Override
    // public String toString() {
    //     return "Users{" +
    //     "id='" + id + '\'' +
    //     ", name='" + name + '\'' +
    //     ", email='" + email + '\'' +
    //     ", userType=" + userType +
    //     ", birthdate=" + birthdate +
    //     ", cellphone='" + cellphone + '\'' +
    //     ", tccsGerenciados=" + tccsGerenciados +
    //     '}';
    // }

}
