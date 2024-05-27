package br.gtcc.gtcc.model.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
 private String id;                                                                                   
 
 @NonNull
 private String idTcc;         
 
 @NonNull
 private String idData;
 
 @JsonIgnore
 @NonNull
 @Relationship(type = "TCC_APRESENTA_EM", direction = Direction.INCOMING)     
 private Tcc tcc;
 
 @Relationship(type = "MEMBER_ONE_OF", direction = Direction.OUTGOING)     
 private Users member1;
 
 @Relationship(type = "MEMBER_TWO_OF", direction = Direction.OUTGOING)      
 private Users member2;

}
