package br.gtcc.gtcc.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_docente_banca")
public class DocenteBanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario") // Supondo que você tenha um usuário que representa o docente
    private Usuario usuario; // O docente que faz parte da banca

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

    @Column(name="status")
    private String status;

}
