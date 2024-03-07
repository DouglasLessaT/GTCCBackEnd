/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.model;

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
 * 
 * Entidade que representa o TCC e a Banca 
 * Essa esntidade tem relação com a entidae usuário por meio de Id's
 */
@Entity
@Indices({
    @Index( fields = "idOrientador" , type = IndexType.UNIQUE)
})
public class Tcc {
    
    @Id
    NitriteId id;                               // Id do Tcc
    NitriteId idAluno;                          // Id do aluno  que relacionado a este tcc 
    NitriteId idOrientador;                     // Id do professor orientador deste tcc 
    private String title;                       // String que representa o titulo do tcc
    private String theme;                       // String que representa o tema do tcc
    private String curse;                       // String que representa curso do tcc
    private LocalDateTime dateOfApresentation;  // Date que representa a data de apresentação do tcc

    public Tcc(){
    }

    public Tcc(NitriteId id, NitriteId idAluno, 
    NitriteId idOrientador, String title, String theme, String curse, 
    LocalDateTime dateOfApresentation){
        this.id = id;
        this.idAluno = idAluno;
        this.idOrientador = idOrientador;
        this.title = title;
        this.theme = theme;
        this.curse = curse;
        this.dateOfApresentation = dateOfApresentation;


    }

    public NitriteId getId(){
        return id;
    }
    public void setId(NitriteId id){
        this.id = id;
    }
    public NitriteId getIdAluno(){
        return idAluno;
    }
    public void setIdAluno(NitriteId idAluno){
        this.idAluno = idAluno;
    }

    public NitriteId getIdOrientador() {
        return idOrientador;
    }

    public void setIdOrientador(NitriteId idOrientador) {
        this.idOrientador = idOrientador;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCurse() {
        return curse;
    }

    public void setCurse(String curse) {
        this.curse = curse;
    }

    public LocalDateTime getDateOfApresentation() {
        return dateOfApresentation;
    }

    public void setDateOfApresentation(LocalDateTime dateOfApresentation) {
        this.dateOfApresentation = dateOfApresentation;
    }

}
