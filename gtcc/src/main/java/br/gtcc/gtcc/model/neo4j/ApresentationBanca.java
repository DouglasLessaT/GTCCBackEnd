package br.gtcc.gtcc.model.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApresentationBanca {

 @Id
 @GeneratedValue
 private String id;                                                     // elementid da Apresentação e formação da Banca                                 
 
 @NonNull
 private String idTcc;                                                  // elementId  do TCC na Apresentação da banca
 
 @Relationship(type = "MEMBER_OF", direction = Direction.OUTGOING)      //Relacionamento entre o menbro 1 e a apresentação
 private Users member1;
 
 @Relationship(type = "MEMBER_OF", direction = Direction.OUTGOING)      // Relacionamento da apresentação com o menbro 2
 private Users member2;
 
 @Relationship(type = "ON_DATE", direction = Direction.OUTGOING)        // Relacionamento com a data em que a apresentação esta sendo marcada 
 private br.gtcc.gtcc.model.neo4j.Data date;
}
