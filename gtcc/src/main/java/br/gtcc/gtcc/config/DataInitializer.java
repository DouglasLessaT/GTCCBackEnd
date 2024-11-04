package br.gtcc.gtcc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.gtcc.gtcc.model.mysql.Grupo;
import br.gtcc.gtcc.model.mysql.TipoDocente;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.GrupoRepository;
import br.gtcc.gtcc.services.impl.mysql.TipoDocenteService;
import br.gtcc.gtcc.services.impl.mysql.UsuarioServices;
import br.gtcc.gtcc.util.services.UsuarioUtil;

import java.util.Optional;

@Configuration
@EnableWebMvc
public class DataInitializer implements CommandLineRunner {


    @Autowired
    private UsuarioUtil userUtil;

    @Autowired
    private UsuarioServices userServices;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final GrupoRepository grupoRepository;
    private final TipoDocenteService tipoDocenteService;

    public DataInitializer(GrupoRepository grupoRepository, TipoDocenteService tipoDocenteService) {
        this.grupoRepository = grupoRepository;
        this.tipoDocenteService = tipoDocenteService;
    }

    @Bean
    public CommandLineRunner runGrupo() {
        return args -> {
            createGrupoIfNotExists("Admin Group");
            createGrupoIfNotExists("Aluno Group");
            createGrupoIfNotExists("Coordenador Group");
            createGrupoIfNotExists("Professor Group");
        };
    }

    private void createGrupoIfNotExists(String groupNome) {
        Optional<Grupo> existingGroup = grupoRepository.findByNome(groupNome);
        if (existingGroup.isEmpty()) {
            Grupo newGroup = new Grupo(null, groupNome, null);
            grupoRepository.save(newGroup);
            System.out.println("Grupo '" + groupNome + "' criado com sucesso.");
        } else {
            System.out.println("Grupo '" + groupNome + "' já existe.");
        }
    }

    @Bean
    public CommandLineRunner runTipoDocentes() {
        return args -> {
            createTipoDocenteIfNotExists("Membro", 1);
            createTipoDocenteIfNotExists("Orientador", 1);
        };
    }

    private void createTipoDocenteIfNotExists(String titulo, int ativo) {
        if (tipoDocenteService.findByTitulo(titulo).isEmpty()) {
            TipoDocente tipoDocente = new TipoDocente(null, titulo, ativo);
            tipoDocenteService.save(tipoDocente);
            System.out.println("TipoDocente '" + titulo + "' criado com sucesso.");
        } else {
            System.out.println("TipoDocente '" + titulo + "' já existe.");
        }
    }
    


    private void addUsers() {
        try {
            // Verifica se o usuário Admin já existe no banco de dados
            Optional<Usuario> existingAdmin = Optional.ofNullable(userUtil.findByLogin("admin"));
            if (existingAdmin.isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Admin");
                admin.setMatricula("0001");
                admin.setLogin("admin");
                admin.setEmail("admin@gmail.com");
                admin.setSenha(passwordEncoder().encode("1234"));
                admin.setTelefone("(11) 00000-0000");
                admin.setDataNascimento("1980-01-01");
                admin.setAtivo(1);
                admin.getPermissoes().add("ROLE_USER");
                admin.getPermissoes().add("ROLE_ADMIN");
                admin.getPermissoes().add("ROLE_PROFESSOR");
                admin.getPermissoes().add("ROLE_COORDENADOR");
                admin.getPermissoes().add("ROLE_ALUNO");
                userUtil.salvarUser(admin);
            }

            // Verifica se o usuário Professor já existe no banco de dados
            Optional<Usuario> existingProfessor = Optional.ofNullable(userUtil.findByLogin("professor"));
            if (existingProfessor.isEmpty()) {
                Usuario professor = new Usuario();
                professor.setNome("Professor");
                professor.setMatricula("0002");
                professor.setLogin("professor");
                professor.setEmail("professor@gmail.com");
                professor.setSenha(passwordEncoder().encode("1234"));
                professor.setTelefone("(11) 11111-1111");
                professor.setDataNascimento("1985-01-01");
                professor.setAtivo(1);
                professor.getPermissoes().add("ROLE_USER");
                professor.getPermissoes().add("ROLE_PROFESSOR");
                userUtil.salvarUser(professor);
            }

            // Verifica se o usuário Coordenador já existe no banco de dados
            Optional<Usuario> existingCoordinator = Optional.ofNullable(userUtil.findByLogin("coordenador"));
            if (existingCoordinator.isEmpty()) {
                Usuario coordenador = new Usuario();
                coordenador.setNome("Coordenador");
                coordenador.setMatricula("0003");
                coordenador.setLogin("coordenador");
                coordenador.setEmail("coordenador@gmail.com");
                coordenador.setSenha(passwordEncoder().encode("1234"));
                coordenador.setTelefone("(11) 22222-2222");
                coordenador.setDataNascimento("1990-01-01");
                coordenador.setAtivo(1);
                coordenador.getPermissoes().add("ROLE_USER");
                coordenador.getPermissoes().add("ROLE_COORDENADOR");
                userUtil.salvarUser(coordenador);
            }

            // Verifica se o usuário Aluno já existe no banco de dados
            Optional<Usuario> existingAluno = Optional.ofNullable(userUtil.findByEmail("aluno@gmail.com"));
            if (existingAluno.isEmpty()) {
                Usuario aluno = new Usuario();
                aluno.setNome("Aluno");
                aluno.setMatricula("0004");
                aluno.setLogin("aluno");
                aluno.setEmail("aluno@gmail.com");
                aluno.setSenha(passwordEncoder().encode("1234"));
                aluno.setTelefone("(11) 33333-3333");
                aluno.setDataNascimento("1995-01-01");
                aluno.setAtivo(1);
                aluno.getPermissoes().add("ROLE_USER");
                aluno.getPermissoes().add("ROLE_ALUNO");
                userUtil.salvarUser(aluno);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if (userUtil == null || userServices == null) {
            System.err.println("Erro: userUtil ou userServices não foram injetados.");
        } else {
            addUsers();
        }
    }
}
