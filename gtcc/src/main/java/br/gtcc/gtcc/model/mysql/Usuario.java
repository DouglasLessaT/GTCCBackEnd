package br.gtcc.gtcc.model.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idUsuario;

    @NonNull
    @NotEmpty
    @Column(name = "nome", nullable = false, length = 150)
    private String nome;
    
    @NonNull
    @NotEmpty
    @Column(name = "matricula", nullable = false, length = 20)
    private String matricula;


    @NonNull
    @NotEmpty
    @Column(name = "login", nullable = false, length = 20)
    private String login;
    
    @NonNull
    @NotEmpty
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    
    @NonNull
    @NotEmpty
    @Column(name = "dataNascimento", nullable = false, length = 10)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    private String dataNascimento;

    @NonNull
    @NotEmpty
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;
    
    @NonNull
    @NotEmpty
    @Column(name = "telefone", nullable = false, length = 15)
    @Pattern(regexp = "^\\(\\d{2}\\) 00000-0000$", message = "O número de telefone deve seguir o padrão brasileiro.")
    private String telefone;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_usuario_permissoes", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "permissao")
    private List<String> permissoes = new ArrayList<>();

    @ManyToOne(targetEntity=Grupo.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_grupo")
    private Grupo grupo;
    
    @NonNull
    @Column(name = "ativo")
    private Integer ativo;

}
