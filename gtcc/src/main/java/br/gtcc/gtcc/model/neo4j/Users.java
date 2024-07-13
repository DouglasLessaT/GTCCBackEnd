package br.gtcc.gtcc.model.neo4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import br.gtcc.gtcc.model.UserType;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import org.springframework.data.neo4j.core.schema.Relationship.Direction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.lang.NonNull;
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

    // @NonNull
    // @NotEmpty
    // private String matricula;

    @NonNull
    @NotEmpty
    private String name;                                
    
    @Email
    @NonNull
    @NotEmpty
    private String email;                               
    
    @NonNull
    @NotEmpty
    private Set<UserType> userType = new HashSet<>();   
    
    @NonNull
    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    private Date birthdate;                                
    
    @NonNull
    @NotEmpty
    @Pattern(regexp = "^\\(\\d{2}\\) 00000-0000$", message = "O número de telefone deve seguir o padrão brasileiro.")
    private String cellphone;                          
    
    @NonNull
    @NotEmpty
    @Pattern(regexp = "\\d{10}", message = "A matrícula deve ter exatamente 10 dígitos.")
    private String login;

    @NonNull
    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "A senha deve conter pelo menos 8 caracteres, incluindo letras maiúsculas e minúsculas, números e caracteres especiais.")
    private String senha;

    @NonNull
    @NotEmpty
    private List<String> permissoes = new ArrayList<>();

    @Relationship(type = "GERENCIA", direction = Relationship.Direction.OUTGOING)  
    private Set<Tcc> tccsGerenciados = new HashSet<>();

}