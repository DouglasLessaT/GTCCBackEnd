package br.gtcc.gtcc.model.neo4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gtcc.gtcc.model.UserType;
import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
    @NonNull
    @Relationship(type = "GERENCIA", direction = Relationship.Direction.OUTGOING)  
    private Set<Tcc> tccsGerenciados = new HashSet<>();
    
    @NonNull
    private String login;

    @JsonIgnore
    private String senha;

    private List<String> permissoes = new ArrayList<>();

}
