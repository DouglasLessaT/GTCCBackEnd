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
@Table(name="tb_docente_banca")
public class DocenteBanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NonNull
    @NotEmpty
    @Column(name = "ativo")
    private Integer ativo;
    
    @ManyToOne(targetEntity=Banca.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_banca")
    private Banca banca;
    
    @ManyToOne(targetEntity=Usuario.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_docente")
    private Usuario docente;
    
    @ManyToOne(targetEntity=TipoDocente.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_tipo_docente")
    private TipoDocente tipoDocente;
}
