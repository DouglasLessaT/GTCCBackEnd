package br.gtcc.gtcc.model.neo4j;

import java.time.LocalDateTime;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Node
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Data {

 @Id
 @GeneratedValue
 private String id;

 @NonNull
 private LocalDateTime date;

}
