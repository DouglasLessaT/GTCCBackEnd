package br.gtcc.gtcc.model.mysql;

import br.gtcc.gtcc.model.mysql.DocenteEnum;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @NonNull
    @Column(name = "ativo")
    private Integer ativo;

    @ManyToOne(targetEntity = Banca.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_banca")
    private Banca banca;

    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_docente")
    private DocenteEnum tipoDocente;
}