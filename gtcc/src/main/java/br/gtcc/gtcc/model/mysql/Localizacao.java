package br.gtcc.gtcc.model.mysql;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_localizacao")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NonNull
    @NotEmpty
    @Column(name = "predio", nullable = false, length = 100)
    private String predio;
    
    @NonNull
    @NotEmpty
    @Column(name = "sala", nullable = false, length = 100)
    private String sala;

    @NonNull
    @NotEmpty
    @Column(name = "andar", nullable = false, length = 100)
    private String andar;

    @NonNull
    @NotEmpty
    @Column(name = "ativo")
    private Integer ativo;

}
