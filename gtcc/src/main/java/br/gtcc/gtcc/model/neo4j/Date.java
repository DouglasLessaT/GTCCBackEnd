package br.gtcc.gtcc.model.neo4j;

import java.time.LocalDateTime;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Node
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Date {

 @Id
 @GeneratedValue
 private String id;                 //elementId  de la clase en Neo4J para a data da apresentação

 @NonNull
 private LocalDateTime date;        // Propriedade date que  guarda a data e hora em que foi armazenado o apresentação

}
