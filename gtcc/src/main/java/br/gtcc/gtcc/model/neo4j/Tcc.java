package br.gtcc.gtcc.model.neo4j;

import java.time.LocalDateTime;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tcc {

    @Id
    @GeneratedValue
    private String Id;                               
   
    @NotEmpty
    private String idAluno;                          
    
    @NotEmpty
    private String idOrientador;                     

    @NonNull
    private String title;                       
   
    @NonNull
    private String theme;                          

    @NonNull
    private String curse;                        

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    private LocalDateTime dateOfApresentation;  
    
    @JsonIgnore
    @Relationship(type = "REALIZA", direction = Relationship.Direction.INCOMING)  
    private Users aluno;
    
    @JsonIgnore
    @Relationship(type = "ORIENTA", direction = Relationship.Direction.INCOMING)
    private Users orientador;

}
