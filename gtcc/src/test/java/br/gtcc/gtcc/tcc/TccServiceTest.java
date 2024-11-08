package br.gtcc.gtcc.tcc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.Grupo;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.services.impl.mysql.TccServices;
import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.tcc.NaoExisteTccNoBancoException;
import br.gtcc.gtcc.util.exceptions.tcc.NaoExisteTccParaRelacionarComApresentacoesException;
import br.gtcc.gtcc.util.exceptions.tcc.TccExisteException;
import br.gtcc.gtcc.util.exceptions.tcc.TccNaoExisteException;
import br.gtcc.gtcc.util.exceptions.usuario.AlunoNaoEncontradoException;
import br.gtcc.gtcc.util.exceptions.usuario.AlunoTemTccException;
import br.gtcc.gtcc.util.exceptions.usuario.UsuarioNaoEcontradoException;
import br.gtcc.gtcc.util.services.TccUtil;
import br.gtcc.gtcc.util.services.UsuarioUtil;

@ExtendWith(MockitoExtension.class)
public class TccServiceTest {
    
    @InjectMocks
    private TccServices tccServices;

    @Mock
    private UsuarioUtil usuarioUtil;

    @Mock
    private TccUtil tccUtil;

    private Tcc tccCriado; 
    
    private Tcc criarTccNulo(){
        return null;
    }

    private Tcc criarTcc(){
        return new Tcc(
            1L,
            "Aplicação de Machine Learning em Análise de Dados Educacionais",
            "Machine Learning",
            "Este trabalho propõe o uso de algoritmos de aprendizado de máquina para análise de dados em ambientes educacionais...",
            1,
            criaUsuario(),
            criarCurso()
        );
    }

    private Tcc criarOutroTcc(){
        return new Tcc(
                2L,
                "Análise de Sentimento em Redes Sociais Utilizando Processamento de Linguagem Natural",
                "Processamento de Linguagem Natural",
                "Este trabalho explora o uso de técnicas de NLP para análise de sentimentos em redes sociais...",
                1,
                criarUsuarioProfessor(),
                criarOutroCurso()
        );
    }

    private Curso criarOutroCurso(){
        return new Curso(2L ,"ADM", 1);
    }

    private Tcc criarTccAlunoNulo(){
        return new Tcc(
            1L,
            "Aplicação de Machine Learning em Análise de Dados Educacionais",
            "Machine Learning",
            "Este trabalho propõe o uso de algoritmos de aprendizado de máquina para análise de dados em ambientes educacionais...",
            1,
            null,
            criarCurso()
        );
    }

    private Curso criarCurso(){
        return new Curso(1L ,"S.I", 1);
    }

    private Usuario criaUsuario(){
        return new Usuario(
             1L,
             "João da Silva",
             "2021001",
             "joaosilva",
             "joao.silva@exemplo.com",
             "2000-05-10",
             "senhaSegura123",
             "(12) 00000-0000",
             List.of("ALUNO"),
             new Grupo(1L,"ALUNO",1),
             1
         );
    }

    private Usuario criarUsuarioProfessor(){
        return new Usuario(
             1L,
             "João da Silva",
             "2021001",
             "joaosilva",
             "joao.silva@exemplo.com",
             "2000-05-10",
             "senhaSegura123",
             "(12) 00000-0000",
             List.of("PROFESSOR"),
             new Grupo(1L,"PROFESSOR",1),
             1
         );
    }

    @Test
    @DisplayName("Teste deve falhar caso seja passado id de aluno inválido quando criar tcc")
    public void deveLancarExcecaoCasoSejaPassadoUmIdInvalido(){

        tccCriado = criarTcc();
        tccCriado.getUsuario().setIdUsuario(null);

        willThrow(new IdInvalidoException("O id do aluno informado é inválido"))
                .given(tccUtil).validaIdAluno(tccCriado.getUsuario().getIdUsuario());
        
        assertThrows(IdInvalidoException.class, ()-> tccServices.createTcc(tccCriado));

        then(tccUtil).should(times(1))
                .validaIdAluno( tccCriado.getUsuario().getIdUsuario() );
    }

    @Test
    @DisplayName("Teste deve falhar caso seja passado id do tcc inválido quando criar tcc")
    public void deveLancarExcecaoCasoSejaPassadoUmIdInvalidoTcc(){

        tccCriado = criarTcc();
        tccCriado.setId(null);

        willThrow(new IdInvalidoException("O id do aluno informado é inválido"))
                .given(tccUtil).validaIdTccParaCriacao(tccCriado.getId());
        
        assertThrows(IdInvalidoException.class, ()-> tccServices.createTcc(tccCriado));

        then(tccUtil).should(times(1))
                .validaIdTccParaCriacao( tccCriado.getId() );
    }

    @Test
    @DisplayName("Deve lancar excecao caso o tcc exista")
    public void deveLancarExcecaoCasoOTccExista(){
        tccCriado = criarTcc();

        doThrow(new TccExisteException("Tcc já existe no banco"))
        .when(tccUtil).checkExistsTccpParaCriacao(tccCriado.getId());
    
        assertThrows(TccExisteException.class, ()-> tccServices.createTcc(tccCriado));
    
        then(tccUtil).should(times(1))
            .checkExistsTccpParaCriacao(tccCriado.getId());
    }

    @Test
    @DisplayName("Deve lancar excecao caso o Aluno nao exista  exista")
    public void deveLancarExcecaoCasoOAlunoNaoExista(){
        tccCriado = criarTcc();

        doThrow(new AlunoNaoEncontradoException("O Aluno informado não existe"))
        .when(tccUtil).existsAluno(tccCriado.getUsuario().getIdUsuario());
    
        assertThrows(AlunoNaoEncontradoException.class, ()-> tccServices.createTcc(tccCriado));
    
        then(tccUtil).should(times(1))
            .existsAluno(tccCriado.getId());
    }

    @Test
    @DisplayName("Deve lancar excecao caso nao encontre o Aluno ao realizar sua busca")
    public void deveLancarExcecaoCasoNaoEncontreAluno(){
        tccCriado = criarTcc();

        given(usuarioUtil.buscaUsersById(tccCriado.getUsuario().getIdUsuario()))
            .willThrow(new UsuarioNaoEcontradoException("Usuário não encontrado"));
        assertThrows(UsuarioNaoEcontradoException.class, 
            ()->tccServices.createTcc(tccCriado)
        );
        then(usuarioUtil).should(times(1))
            .buscaUsersById(tccCriado.getUsuario().getIdUsuario());
    }

    @Test
    @DisplayName("Teste verificado se o aluno foi adicionado no tcc")
    public void devePassarQuandoOAlunoForAdicionadoNoTcc(){

        tccCriado = criarTcc();
        var alunoCriado = criaUsuario();

        given(tccUtil.adicionarAlunoNoTcc(tccCriado, alunoCriado))
            .willReturn(tccCriado);

        var tcc = tccUtil.adicionarAlunoNoTcc(tccCriado, alunoCriado);
        assertSame(tcc, tccCriado);
        assertEquals(tcc, tccCriado);

        then(tccUtil).should(times(1))
            .adicionarAlunoNoTcc(tccCriado, alunoCriado);
    }

    @Test
    @DisplayName("Teste deve lancar ecxecao caso o aluno ja esteja em outro Tcc")
    public void deveLancarEcxecaoCasoAlunoJaTenhaTcc(){

        tccCriado = criarTcc(); 
        var aluno = tccCriado.getUsuario();

        given(usuarioUtil.buscaUsersById(aluno.getIdUsuario())).willReturn(aluno);
        given(tccUtil.checkSeAlunoTemTcc(aluno)).willThrow(new AlunoTemTccException("O aluno ja esta em outro tcc."));

        assertThrows(AlunoTemTccException.class, 
            ()->tccServices.createTcc(tccCriado));

        then(tccUtil).should(times(1)).checkSeAlunoTemTcc(aluno);
        then(usuarioUtil).should(times(1)).buscaUsersById(aluno.getIdUsuario());

    }

    @Test
    @DisplayName("Teste deve lancar erro caso ele tente salvar o usuario nulo")
    public void deveLancarExcecaoCasoEleTenteSalvarAlunoNulo(){
        tccCriado = criarTcc();
        var aluno = tccCriado.getUsuario();

        given(usuarioUtil.buscaUsersById(aluno.getIdUsuario())).willReturn(aluno);
        given(usuarioUtil.salvarUser(aluno)).willThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, ()->tccServices.createTcc(tccCriado));

        then(usuarioUtil).should(times(1)).buscaUsersById(aluno.getIdUsuario());
        then(usuarioUtil).should(times(1)).salvarUser(aluno);
    }

    @Test
    @DisplayName("Teste deve lancar erro caso tente salvar um TCC nulo")
    public void deveLancarExcecaoCasoTccSejaNulo(){
        tccCriado = criarTcc();
        var aluno = tccCriado.getUsuario();

        given(usuarioUtil.buscaUsersById(aluno.getIdUsuario())).willReturn(aluno);
        given(tccUtil.adicionarAlunoNoTcc(tccCriado, aluno)).willReturn(tccCriado);
        given(tccUtil.salvarTcc(tccCriado)).willThrow(new IllegalArgumentException());
        
        assertThrows(IllegalArgumentException.class, 
            ()->tccServices.createTcc(tccCriado)
        );

        then(tccUtil).should(times(1)).salvarTcc(tccCriado);
        then(tccUtil).should(times(1))
            .adicionarAlunoNoTcc(tccCriado, aluno);
        then(usuarioUtil).should(times(1)).buscaUsersById(aluno.getIdUsuario());
       
    }

    @Test
    @DisplayName("Deve lancar uam excecao caso ele tente passar um id invalido ao deletar um tcc")
    public void deveLancarExcecaoCasoOIdSejaInvalidoParaDeletarTCC(){
        tccCriado = criarTcc();
     
        given(tccUtil.validaIdTcc(tccCriado.getId()))
            .willThrow(new IdInvalidoException("O id do Tcc informado é inválido"));
        
        assertThrows( IdInvalidoException.class , 
            ()->tccServices.deleteTCC(tccCriado.getId())
        );
        
        then(tccUtil).should(times(1)).validaIdTcc(tccCriado.getId());
    }

    @Test
    @DisplayName("Deve lancar excecao caso nao encontre tcc ao relaizar o exclucao")
    public void deveLancarExcecaoCasoTccNaoExista(){
        tccCriado = criarTcc();

        given(tccUtil.validaIdTcc(tccCriado.getId())).willReturn(true);
        given(tccUtil.checkExistsTcc(tccCriado.getId()))
            .willThrow( new TccNaoExisteException("Tcc não existe no banco"));


        assertThrows( TccNaoExisteException.class , 
            ()->tccServices.deleteTCC(tccCriado.getId())
        );

        then(tccUtil).should(times(1)).validaIdTcc(tccCriado.getId());
        then(tccUtil).should(times(1)).checkExistsTcc(tccCriado.getId());

    }

    @Test
    @DisplayName("Teste deve retornar uam excecao caso TCC nao exista ")
    public void deveLancarExcecaoCasoTccNaoExistaAoExcluir(){
        tccCriado = criarTcc();

        given(tccUtil.buscarTcc(tccCriado.getId()))
            .willThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, 
            ()->tccServices.deleteTCC(tccCriado.getId())
        );

        then(tccUtil).should(times(1)).buscarTcc(tccCriado.getId());
        
    }

    @Test
    @DisplayName("Teste deve retornar uam excecao caso TCC nao exista ")
    public void deveLancarExcecaoCasoTccSejaNuloAoExcluir(){
        tccCriado = criarTcc();

        willThrow(new IllegalArgumentException()).given(tccUtil).deleteTcc(null);
        
        assertThrows(IllegalArgumentException.class, 
            ()->tccServices.deleteTCC(null)
        );

        then(tccUtil).should(times(1)).deleteTcc(null);
        
    }

    @Test
    @DisplayName("Testa se o objeto deletado é igual ao objeto buscado antes de deletar")
    public void deveVerificarSeOobjetoEigual(){
        tccCriado = criarTcc();

        given(tccUtil.buscarTcc(tccCriado.getId())) 
            .willReturn(tccCriado);
        willDoNothing().given(tccUtil).deleteTcc(tccCriado.getId());
        
        Tcc tccFinded = tccUtil.buscarTcc(tccCriado.getId());
        tccUtil.deleteTcc(tccCriado.getId());

        Tcc tccDeleted = tccServices.deleteTCC(tccCriado.getId());

        assertEquals(tccFinded ,tccDeleted, "O tcc deletado tem ser igual");
        assertSame(tccFinded, tccDeleted , "Apontam para o mesmo objeto na memoria");

        then(tccUtil).should(times(2)).buscarTcc(tccCriado.getId());
        then(tccUtil).should(times(2)).deleteTcc(tccCriado.getId());
       
    }

    @Test
    @DisplayName("Deve lancar excecao caso nao exista Tcc cadastrado no banco ")
    public void deveLancarExcecaoCasoNaoExistaTccNoBanco(){
        tccCriado = criarTcc();
        given(tccUtil.countDeTccs())
        .willThrow(new NaoExisteTccNoBancoException("Não existe Tcc no banco"));
    
        assertThrows(NaoExisteTccNoBancoException.class, ()->tccServices.getAllTCC());

        then(tccUtil).should(times(1)).countDeTccs();
    }

    @Test
    @DisplayName("Deve retornar uma lista com um elmento")
    public void deveRetornarUmListaComUmElemento(){

        given(tccUtil.buscarTodosOsTcc()).willReturn(Collections.singletonList(new Tcc()));
        Boolean isUniqueElement = tccServices.getAllTCC().size() > 0;
        assertTrue(isUniqueElement);
        then(tccUtil).should(times(1)).buscarTodosOsTcc();
    }

    @Test
    @DisplayName("Deve passar a o buscar um tcc no banco ")
    public void devePassarAoBuscarTcc(){
        tccCriado = criarTcc();
        var idTcc = tccCriado.getId();

        given(tccUtil.validaIdTcc(idTcc))
            .willThrow(new IdInvalidoException("O id do Tcc informado é inválido"));
        assertThrows(IdInvalidoException.class, ()->tccServices.getTCC(idTcc));
        then(tccUtil).should(times(1)).validaIdTcc(idTcc);
    
        reset(tccUtil);

        given(tccUtil.validaIdTcc(idTcc)).willReturn(true);
        given(tccUtil.checkExistsTcc(idTcc)).willThrow(new TccNaoExisteException("Tcc não existe no banco"));
    
        assertThrows(TccNaoExisteException.class, ()->tccServices.getTCC(idTcc));

        then(tccUtil).should(times(1)).validaIdTcc(idTcc);    
        then(tccUtil).should(times(1)).checkExistsTcc(idTcc);

        reset(tccUtil);

        given(tccUtil.validaIdTcc(idTcc)).willReturn(true);
        given(tccUtil.checkExistsTcc(idTcc)).willReturn(true);
        given(tccUtil.buscarTcc(idTcc)).willReturn(tccCriado);

        Tcc tccFinded = tccUtil.buscarTcc(idTcc);
        Tcc tccFinded_ = tccServices.getTCC(idTcc);

        assertEquals(tccFinded, tccFinded_);
        assertSame(tccFinded, tccFinded_);

        then(tccUtil).should(times(1)).validaIdTcc(idTcc);    
        then(tccUtil).should(times(1)).checkExistsTcc(idTcc);
        then(tccUtil).should(times(2)).buscarTcc(idTcc);
    }

    @Test
    @DisplayName("Deve testar a busca de tcc sem apresentcoes")
    public void deveTestarBuscaTccSemApresentacoes(){
        tccCriado = criarTcc();

        given(tccUtil.countDeTccsSemApresentacao())
            .willThrow(new NaoExisteTccParaRelacionarComApresentacoesException("Não existe tcc a ser relacionado a apreentaçoes"));
       
        assertThrows(NaoExisteTccParaRelacionarComApresentacoesException.class,
            ()->tccServices.getTccSemApresentacao()
        );

        then(tccUtil).should(times(1)).countDeTccsSemApresentacao();
    
        reset(tccUtil);
    
        given(tccUtil.countDeTccsSemApresentacao()).willReturn(2L);
        given(tccUtil.listaDeTccSemApresentacao()).willReturn(Collections.singletonList(new Tcc()));

        Long numerOfTcc = tccUtil.countDeTccsSemApresentacao();
        List<Tcc> listOfTcc = tccServices.getTccSemApresentacao();

        assertTrue(numerOfTcc > 0L);
        assertTrue(listOfTcc.size() > 0L);
        
        then(tccUtil).should(times(2)).countDeTccsSemApresentacao();        
        then(tccUtil).should(times(1)).listaDeTccSemApresentacao();
    
    }

    @Test
    @DisplayName("Deve testar a funcioalidade de buscar um tcc pelo seu titulo")
    public void deveBuscarSeuTccPeloSeuTitulo(){
        tccCriado = criarTcc();

        given(tccUtil.buscarTccPorTitulo(tccCriado.getTitulo())).willReturn(tccCriado);

        Tcc tccFindedByTitle = tccServices.getTCCByTitle(tccCriado.getTitulo());

        assertEquals(tccCriado, tccFindedByTitle);
        assertSame(tccCriado, tccFindedByTitle);

        then(tccUtil).should(times(1)).buscarTccPorTitulo(tccCriado.getTitulo());
    }

    //Teste de que adiciona aluno em tcc
    @Test
    @DisplayName("Deve testar se um aluno esta adicionado no tcc ")
    public void deveTestarSeUmaAlunoFoiAdicionadoNoTcc(){
        
    }
}
