package br.gtcc.gtcc.model.mysql;

import java.time.LocalDateTime;

import br.gtcc.gtcc.model.mysql.StatusConvite;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_convites")
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destino", nullable = false)
    private Usuario destino;

    @ManyToOne
    @JoinColumn(name = "id_usuario_origem", nullable = false)
    private Usuario origem;

    @ManyToOne
    @JoinColumn(name = "id_docente_destino", nullable = false)
    private DocenteBanca destinoDocente;

    @Enumerated(EnumType.STRING)
    private StatusConvite status;

    @NotNull
    @PastOrPresent
    private LocalDateTime criacaoConvite;

    @FutureOrPresent
    private LocalDateTime validateDate;

    @FutureOrPresent
    private LocalDateTime acceptedDate;
}