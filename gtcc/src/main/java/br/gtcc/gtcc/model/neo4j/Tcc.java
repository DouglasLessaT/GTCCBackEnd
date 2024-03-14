/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.model.neo4j;

import java.time.LocalDateTime;
import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.index.IndexType;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;
import org.dizitart.no2.repository.annotations.Indices;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author mrbee
 * 
 * Entidade que representa o TCC e a Banca 
 * Essa esntidade tem relação com a entidae usuário por meio de Id's
 */
@Data
@NoArgsConstructor
@Node("TCC")
 public class Tcc {
    
    @Id
    private String Id;                               // Id do Tcc
   
    @NonNull
    private String idAluno;                          // Id do aluno  que relacionado a este tcc 
   
    @NonNull
    private String idOrientador;                     // Id do professor orientador deste tcc 
   
    @NonNull
    private String title;                       // String que representa o titulo do tcc
   
    @NonNull
    private String theme;                       // String que representa o tema do tcc
   
    @NonNull
    private String curse;                       // String que representa curso do tcc
   
    @NonNull
    private LocalDateTime dateOfApresentation;  // Date que representa a data de apresentação do tcc
    
}
