/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.model.nitriteid;

import java.time.LocalDateTime;
import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.index.IndexType;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;
import org.dizitart.no2.repository.annotations.Indices;

/**
 *
 * @author mrbee
 * Esta classe representa a data que vai ficar as apresentações 
 */
@Entity
@Indices({
    @Index(fields = "idApresentation", type = IndexType.UNIQUE)
})
public class Data {
    
    @Id             
    NitriteId id;                   // Id da data do calendário         
    private LocalDateTime date;     // Date data do calendário que vai esta livre ou não
    public Data(){
        
    }
    public Data(NitriteId id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }
    public NitriteId getId() {
        return id;
    }
    public void setId(NitriteId id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
}
