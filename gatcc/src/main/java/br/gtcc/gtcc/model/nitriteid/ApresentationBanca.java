/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.model.nitriteid;

// import java.time.LocalDateTime;
// import java.util.Date;
import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.index.IndexType;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;
import org.dizitart.no2.repository.annotations.Indices;

/**
 *
 * @author mrbee
 * 
 * Esta classe representa a entidade de Apresentação do Tcc e a formação da banca
 */
@Entity
@Indices({
    @Index(fields = "idTcc", type = IndexType.UNIQUE),
})
public class ApresentationBanca {
    
    @Id
    NitriteId id;               // Id da Apresentação e banca 
    NitriteId idTcc;            // Id do Tcc que vai apresentar 
    NitriteId idMenber1;        // Id do membro 1 da banca que é um professor
    NitriteId idMenber2;        // Id do menbro 2 da banca que é um professor
    NitriteId idDate;           // Id da data da apresentação 

}
