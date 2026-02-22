package br.gtcc.gtcc.config;

import br.gtcc.gtcc.controller.BancaController;
import br.gtcc.gtcc.model.mysql.*;
import br.gtcc.gtcc.model.mysql.DTO.BancaRequest;
import br.gtcc.gtcc.model.mysql.repository.*;
import br.gtcc.gtcc.services.exception.DocenteBancaNaoExisteException;
import br.gtcc.gtcc.services.exception.LocalizacaoNaoExisteException;
import br.gtcc.gtcc.services.impl.mysql.BancaService;
import br.gtcc.gtcc.services.impl.mysql.CursoService;
import br.gtcc.gtcc.util.exceptions.tcc.TccNaoExisteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.gtcc.gtcc.services.impl.mysql.TipoDocenteService;
import br.gtcc.gtcc.services.impl.mysql.UsuarioServices;
import br.gtcc.gtcc.util.services.UsuarioUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

@Configuration
@EnableWebMvc
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioUtil userUtil;

    @Autowired
    private UsuarioServices userServices;
    @Autowired
    private BancaController bancaController;
    @Autowired
    private ApresentacaoRepository apresentacaoRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final DocenteBancaRepository docenteBancaRepository;
    private final GrupoRepository grupoRepository;
    private final TccRepository tccRepository;
    private final TipoDocenteService tipoDocenteService;
    private final BancaService bancaService;
    private final CursoService cursoService;
    private final LocalizacaoRepository localizacaoRepository;

    public DataInitializer(
            GrupoRepository grupoRepository,
            TipoDocenteService tipoDocenteService,
            TccRepository tccRepository,
            DocenteBancaRepository docenteBancaRepository,
            BancaService bancaService,
            CursoService cursoService,
            LocalizacaoRepository localizacaoRepository
    ) {
        this.localizacaoRepository = localizacaoRepository;
        this.cursoService = cursoService;
        this.bancaService = bancaService;
        this.docenteBancaRepository = docenteBancaRepository;
        this.tccRepository = tccRepository;
        this.grupoRepository = grupoRepository;
        this.tipoDocenteService = tipoDocenteService;
    }

    public synchronized CommandLineRunner runGrupo() {
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
            Grupo newGroup = new Grupo(null, groupNome, 1);
            grupoRepository.save(newGroup);
            System.out.println("Grupo '" + groupNome + "' criado com sucesso.");
        } else {
            System.out.println("Grupo '" + groupNome + "' já existe.");
        }
    }

    @Bean
    @Order()
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

    public synchronized CommandLineRunner runCurso(){
        return args -> {
            criarCurso();
        };
    }

    private void criarCurso() {
        var curso = new Curso();
        curso.setAtivo(1);
        curso.setTitulo("SI (Sistema de Informação)");
        cursoService.criarCurso(curso);
    }

    public CommandLineRunner runTcc(){
        return args -> {
            criarTcc();
        };
    }

    private void criarTcc() {

        var usuario = userUtil.buscaUsersById(4L);
        var curso = cursoService.buscarCurso(1L);

        var tcc = new Tcc();
        tcc.setCurso(curso);
        tcc.setUsuario(usuario);
        tcc.setAtivo(1);
        tcc.setTema("Aplicação de Inteligência Artificial na Agricultura");
        tcc.setResumo("Este trabalho analisa como técnicas de inteligência artificial podem ser aplicadas para otimizar práticas agrícolas, como monitoramento de plantações, previsão de colheitas e gestão de recursos hídricos. Além disso, aborda os desafios de implementação e os benefícios para a sustentabilidade.");
        tcc.setTitulo("Inteligência Artificial na Agricultura: Otimização e Sustentabilidade");

        tccRepository.save(tcc);

    }

    public CommandLineRunner runBanca(){
        return args -> {
            criarBanca();
        };
    }

    private void criarBanca() {

        var tcc =  tccRepository.findById(1L).orElseThrow(()->new TccNaoExisteException());

        var banca = new BancaRequest();
        banca.setIdTcc(tcc.getId());
        banca.setAtivo(1);

        bancaService.create(banca);
    }

    public CommandLineRunner runDocenteBanca(){
        return args -> {
            criarDocenteBanca();
        };
    }

    private void criarDocenteBanca() {

        var banca = bancaService.getById(1L);
        DocenteEnum docenteEnum = DocenteEnum.ORIENTADOR;
        var usuario = userUtil.buscaUsersById(2L);
        var docenteBanca = new DocenteBanca();

        docenteBanca.setAtivo(1);
        docenteBanca.setUsuario(usuario);
        docenteBanca.setTipoDocente(docenteEnum);
        docenteBanca.setBanca(banca);

        docenteBancaRepository.save(docenteBanca);

        DocenteEnum _docenteEnum = DocenteEnum.ORIENTADOR;
        var _docenteBanca = new DocenteBanca();

        _docenteBanca.setAtivo(1);
        _docenteBanca.setUsuario(usuario);
        _docenteBanca.setTipoDocente(_docenteEnum);
        _docenteBanca.setBanca(banca);

        docenteBancaRepository.save(_docenteBanca);

    }

    public CommandLineRunner runLoacalizacao(){
        return args -> {
            criarLoacalizacao();
        };
    }

    private void criarLoacalizacao() {

        var localizacao = new Localizacao();

        localizacao.setAtivo(1);
        localizacao.setSala("319");
        localizacao.setAndar("3 Andar");
        localizacao.setPredio("Dom Bosco I");

        localizacaoRepository.save(localizacao);
    }

    public CommandLineRunner runApresentacao(){
        return args -> {
            criarApresentacao();
        };
    }

    private void criarApresentacao() {

        var apresentacao = new Apresentacao();
        var tcc = tccRepository.findById(1L).orElseThrow(()->new TccNaoExisteException());
        var localizacao = localizacaoRepository.findById(1L).orElseThrow(()->new LocalizacaoNaoExisteException());

        apresentacao.setAtivo(1);
        apresentacao.setData(LocalDateTime.of(2024, 11, 15, 10, 0));
        apresentacao.setHoraInicio(LocalTime.of(10, 30));
        apresentacao.setHoraFim(LocalTime.of(12, 0));
        apresentacao.setDataCriacaoApresentacao(LocalDateTime.now());
        apresentacao.setLocalizacao(localizacao);
        apresentacao.setTcc(tcc);

        apresentacaoRepository.save(apresentacao);

    }

    private synchronized void addUsers() {
        try {
            // Verifica se o usuário Admin já existe no banco de dados
            Optional<Usuario> existingAdmin = Optional.ofNullable(userUtil.findByLogin("admin"));
            if (existingAdmin.isEmpty()) {

                log.info("Teste ");
                Grupo grpAdmin = grupoRepository.findByNome("Admin Group").get();
                log.info("Admin Group "+grpAdmin.toString() );
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
                admin.setGrupo(grpAdmin);
                userUtil.salvarUser(admin);
            }

            // Verifica se o usuário Professor já existe no banco de dados
            Optional<Usuario> existingProfessor = Optional.ofNullable(userUtil.findByLogin("professor"));
            if (existingProfessor.isEmpty()) {
                Grupo grpProfessor = grupoRepository.findByNome("Professor Group").get();

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
                professor.setGrupo(grpProfessor);
                userUtil.salvarUser(professor);
            }

            // Verifica se o usuário Coordenador já existe no banco de dados
            Optional<Usuario> existingCoordinator = Optional.ofNullable(userUtil.findByLogin("coordenador"));
            if (existingCoordinator.isEmpty()) {

                Grupo grpCoordenador = grupoRepository.findByNome("Coordenador Group").get();

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
                coordenador.setGrupo(grpCoordenador);
                userUtil.salvarUser(coordenador);
            }

            // Verifica se o usuário Aluno já existe no banco de dados
            Optional<Usuario> existingAluno = Optional.ofNullable(userUtil.findByEmail("aluno@gmail.com"));
            if (existingAluno.isEmpty()) {

                Grupo grpAluno = grupoRepository.findByNome("Aluno Group").get();

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
                aluno.setGrupo(grpAluno);
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

            runGrupo().run(args);
            addUsers();

            runCurso().run(args);
            runTcc().run(args);

            runLoacalizacao().run(args);
            runApresentacao().run(args);

            runBanca().run(args);
            runDocenteBanca().run(args);

        }
    }
}
