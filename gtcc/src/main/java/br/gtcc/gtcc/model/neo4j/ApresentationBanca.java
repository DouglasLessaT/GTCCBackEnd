package br.gtcc.gtcc.model.neo4j;

import java.lang.reflect.Member;
import java.util.Date;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node
public class ApresentationBanca {

 @Id
 @GeneratedValue
 private Long id;
 private String idTcc;
 @Relationship(type = "MEMBER_OF", direction = Direction.OUTGOING)
 private Member member1;
 @Relationship(type = "MEMBER_OF", direction = Direction.OUTGOING)
 private Member member2;
 @Relationship(type = "ON_DATE", direction = Direction.OUTGOING)
 private Date date;
}
