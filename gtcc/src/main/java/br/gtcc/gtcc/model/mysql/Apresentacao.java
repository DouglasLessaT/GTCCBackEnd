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
@Table(name="tb_apresentacao")
public class Apresentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NonNull
    @NotEmpty
    @Column(name = "data", nullable = false, length = 10)
    private String data;
    
    @NonNull
    @NotEmpty
    @Column(name = "hora_inicio", nullable = false, length = 5)
    private String horaInicio;
    
    @NonNull
    @NotEmpty
    @Column(name = "hora_fim", nullable = false, length = 5)
    private String horaFim;

    @NonNull
    @NotEmpty
    @Column(name = "ativo")
    private Integer ativo;

    @ManyToOne(targetEntity=Tcc.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_tcc")
    private Tcc tcc;
    
    @ManyToOne(targetEntity=Localizacao.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_localizacao")
    private Localizacao localizacao;
}
