package br.gtcc.gtcc.model.mysql;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_banca")
public class Banca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Integer ativo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tcc")
    private Tcc tcc;

    @OneToMany(mappedBy = "banca", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocenteBanca> docentes = new ArrayList<>(); // Lista de docentes que fazem parte da banca

    public void adicionarDocente(DocenteBanca docente) {
        docentes.add(docente);
        docente.setBanca(this); // Define a banca para o docente
    }

    public boolean isBancaCompleta() {
        return docentes.size() >= 3; // Por exemplo, a banca precisa de 3 docentes
    }
}