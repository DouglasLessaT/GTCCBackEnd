package br.gtcc.gtcc.model.mysql;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_tcc")
public class Tcc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotEmpty
    @Column(name = "titulo", nullable = false, length = 300)
    private String titulo;
    
    @NonNull
    @NotEmpty
    @Column(name = "tema", nullable = false, length = 300)
    private String tema;
    
    @NonNull
    @NotEmpty
    @Column(name = "resumo", nullable = false, length = 1000)
    private String resumo;
    
    @NonNull
    @Column(name = "ativo")
    private Integer ativo;
   
    @ManyToOne(targetEntity=Usuario.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_discente")
    private Usuario usuario;
   
    @ManyToOne(targetEntity=Curso.class, fetch=FetchType.EAGER , optional = true)
	@JoinColumn(name="id_curso", nullable = true)
    private Curso curso;
}
