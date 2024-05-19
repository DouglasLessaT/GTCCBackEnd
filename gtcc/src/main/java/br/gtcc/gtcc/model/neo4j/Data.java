package br.gtcc.gtcc.model.neo4j;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
public class Data {

 @Id
 @GeneratedValue
 private String id;                 

 @NonNull
 private LocalDateTime date;        
   
 private Set<ApresentationBanca> listApresentacoes = new HashSet<>();

}
