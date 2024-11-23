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
    @JoinColumn(name = "id_banca")
    private Banca banca; // Relacionamento com a banca

    @ManyToOne
    @JoinColumn(name = "id_usuario") // Supondo que você tenha um usuário que representa o docente
    private Usuario usuario; // O docente que faz parte da banca

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_docente")
    private DocenteEnum tipoDocente; // Tipo de docente (por exemplo, avaliador interno, externo, etc.)

    @Column(name = "ativo")
    private Integer ativo;
}