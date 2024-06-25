package br.gtcc.gtcc.model.neo4j;

import java.time.LocalDateTime;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

/**
 *
 * @author mrbee
 * 
 *         Entidade que representa o TCC e a Banca
 *         Essa esntidade tem relação com a entidae usuário por meio de Id's
 */

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tcc {

    @Id
    @GeneratedValue
    private String Id;                               
   
    @NonNull
    private String idAluno;                          
    
    @NonNull
    private String idOrientador;                     

    @NonNull
    private String title;                       
   
    @NonNull
    private String theme;                          

    @NonNull
    private String curse;                        

    @JsonIgnore
    private LocalDateTime dateOfApresentation;  
    
    @JsonIgnore
    @NonNull
    @Relationship(type = "REALIZA", direction = Relationship.Direction.INCOMING)  
    private Users aluno;
    
    @JsonIgnore
    @NonNull
    @Relationship(type = "ORIENTA", direction = Relationship.Direction.INCOMING)
    private Users orientador;

}
