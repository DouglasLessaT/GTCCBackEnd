package br.gtcc.gtcc.model.mysql;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tb_convites")
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_origem", nullable = false)
    private Usuario origem;

    @Enumerated(EnumType.STRING)
    private StatusConvite status;

    private LocalDateTime criacaoConvite;

    private LocalDateTime validateDate;

    private LocalDateTime acceptedDate;
}
