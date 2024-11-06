package br.gtcc.gtcc.tcc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.BDDMockito.times;

import br.gtcc.gtcc.util.exceptions.tcc.NaoExisteTccNoBancoException;
import br.gtcc.gtcc.util.exceptions.tcc.NaoExisteTccParaRelacionarComApresentacoesException;
import br.gtcc.gtcc.util.exceptions.tcc.TccExisteException;
import br.gtcc.gtcc.util.exceptions.tcc.TccNaoExisteException;
import br.gtcc.gtcc.util.exceptions.usuario.AlunoTemTccException;
import br.gtcc.gtcc.util.exceptions.usuario.UsuarioNaoAlunoException;
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
import br.gtcc.gtcc.model.mysql.repository.TccRepository;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.usuario.AlunoNaoEncontradoException;
import br.gtcc.gtcc.util.exceptions.usuario.OrientadorNaoEncontradoException;
import br.gtcc.gtcc.util.services.TccUtil;

@ExtendWith(MockitoExtension.class)
public class  TccUtilTest {

    @InjectMocks
    private TccUtil tccUtil;

    @Mock
    private TccRepository tccRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

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
    @DisplayName("Teste deve lancar excecao caso seja dado uma entidade nula")
    public void deveLancarExcecaoCasoEntidadeSejaNula(){
        var tccNulo = criarTccNulo();

        given(tccRepository.save(tccNulo)).willThrow(new IllegalArgumentException());

        assertThrows( IllegalArgumentException.class , ()-> tccUtil.salvarTcc(tccNulo));
    
        then(tccRepository).should(times(1)).save(tccNulo);
    }

    @Test
    @DisplayName("Teste deve salvar uam entidade normalmente ")
    public void salvarEntidadeNormalmente(){
        var tccCriado = criarTcc();
        given(tccRepository.save(tccCriado)).willReturn(tccCriado);
        tccUtil.salvarTcc(tccCriado);

        then(tccRepository).should(times(1)).save(tccCriado);

    }
    
    @Test
    @DisplayName("Teste deve lancar exececao caso seja dado um id nulo")
    public void deveLancarExececaoCasoIdSejaNulo(){
        Long id = null;

        willThrow(new IllegalArgumentException()).given(tccRepository).deleteById(id);
        assertThrows(IllegalArgumentException.class, ()->tccUtil.deleteTcc(id));

    }

    @Test
    @DisplayName("Teste de exclusao de entidade com sucesso")
    public void exclusaoCorretaDeUmaEntidade(){

        willThrow(new IllegalArgumentException()).given(tccRepository).deleteById(null);
        
        assertThrows(IllegalArgumentException.class, ()->tccUtil.deleteTcc(null));
    }

    @Test
    @DisplayName("Teste deve lancar exececao caso id do tcc seja diferente de nulo na hora de sua criacao de um tcc")
    public void deveLancarExececaoCasoIdSejaDiferenteDeNuloQuandoCriarUmTcc(){

        var tccCriado = criarTcc();
        assertThrows(IdInvalidoException.class, 
            ()->tccUtil.validaIdTccParaCriacao(tccCriado.getId())
        );

    }

    @Test
    @DisplayName("Teste deve passar corretamento caso o id seja nulo na hora de sua criacao de um tcc")
    public void devePassarCasoIdSejaIgualDeNuloQuandoCriarUmTcc(){

        assertTrue(
            ()->tccUtil.validaIdTccParaCriacao(null)
        );

    }

    @Test
    @DisplayName("Teste deve lancar exececao caso id do tcc seja igual a nulo na hora de sua alteracao")
    public void deveLancarExececaoCasoIdSejaIgualNuloQuandoAlterarUmTcc(){

        assertThrows(IdInvalidoException.class, 
            ()->tccUtil.validaIdTcc(null)
        );

    }

    @Test
    @DisplayName("Teste deve passar corretamento caso o id seja diferente de nulo na hora de seu update")
    public void devePassarCasoIdSejaDiferenteDeNuloQuandoAlterarUmTcc(){

        var tccCriado = criarTcc();
        assertTrue(
            ()->tccUtil.validaIdTcc(tccCriado.getId())
        );

    }

    @Test
    @DisplayName("Teste deve lancar exececao caso id do tcc seja igual a nulo na hora de sua alteracao")
    public void deveLancarExececaoCasoIdAlunoSejaIgualNuloQuandoCriarUmTcc(){

        assertThrows(IdInvalidoException.class, 
            ()->tccUtil.validaIdAluno(null)
        );

    }

    @Test
    @DisplayName("Teste deve passar corretamento caso o id seja diferente de nulo na hora de seu update")
    public void devePassarCasoIdAlunoSejaDiferenteDeNuloQuandoAlterarUmTcc(){

        var usuarioCriado = criaUsuario();
        assertTrue(
            ()->tccUtil.validaIdAluno(usuarioCriado.getIdUsuario())
        );

    }

    @Test
    @DisplayName("Teste deve lancar exececao caso id do tcc seja igual a nulo na hora de sua alteracao")
    public void deveLancarExececaoCasoIdOrientadorSejaIgualNuloQuandoCriarUmTcc(){

        assertThrows(IdInvalidoException.class, 
            ()->tccUtil.validaIdOrientador(null)
        );

    }

    @Test
    @DisplayName("Teste deve passar corretamento caso o id seja diferente de nulo na hora de seu update")
    public void devePassarCasoIdOrientadorSejaDiferenteDeNuloQuandoAlterarUmTcc(){

        var usuarioCriado = criaUsuario();
        assertTrue(
            ()->tccUtil.validaIdOrientador(usuarioCriado.getIdUsuario())
        );

    }

    @Test
    @DisplayName("Teste deve lancar uma execao caso nao encontre Aluno")
    public void deveLancarExececaoCasoAlunoNaoExista(){
        var alunoCriado = criaUsuario();

        given(usuarioRepository.existsById(alunoCriado.getIdUsuario()))
        .willReturn(false);
  
        assertThrows(AlunoNaoEncontradoException.class,
            ()->tccUtil.existsAluno(alunoCriado.getIdUsuario()
        ));

        then(usuarioRepository).should(times(1))
        .existsById(alunoCriado.getIdUsuario());
    }    

    @Test
    @DisplayName("Teste deve lancar uma execao caso nao encontre Aluno")
    public void devePassarCasoAlunoExista(){
        var alunoCriado = criaUsuario();

        given(usuarioRepository.existsById(alunoCriado.getIdUsuario()))
        .willReturn(true);
        assertTrue(
            ()->usuarioRepository.existsById(alunoCriado.getIdUsuario()
        ));
        
        assertTrue(
            ()->tccUtil.existsAluno(alunoCriado.getIdUsuario()
        ));

        then(usuarioRepository).should(times(2))
        .existsById(alunoCriado.getIdUsuario());
    }    

    @Test
    @DisplayName("Teste deve lancar uma execao caso nao encontre Aluno")
    public void deveLancarExececaoCasoOrientadorNaoExista(){
        var orientadorCriado = criaUsuario();

        given(usuarioRepository.existsById(orientadorCriado.getIdUsuario()))
        .willReturn(false);
  
        assertThrows(OrientadorNaoEncontradoException.class,
            ()->tccUtil.existsOrientador(orientadorCriado.getIdUsuario()
        ));

        then(usuarioRepository).should(times(1))
        .existsById(orientadorCriado.getIdUsuario());
    }    

    @Test
    @DisplayName("Teste deve lancar uma execao caso nao encontre Orientador")
    public void devePassarCasoOrientadorExista(){
        var orientadorCriado = criaUsuario();

        given(usuarioRepository.existsById(orientadorCriado.getIdUsuario()))
        .willReturn(true);
        assertTrue(
            ()->usuarioRepository.existsById(orientadorCriado.getIdUsuario()
        ));
        
        assertTrue(
            ()->tccUtil.existsOrientador(orientadorCriado.getIdUsuario()
        ));

        then(usuarioRepository).should(times(2))
        .existsById(orientadorCriado.getIdUsuario());
    }    
    
    @Test
    @DisplayName("Teste deve verificar se um aluno foi adicionado em um tcc")
    public void deveVerificarSeUmAlunoFoiAdicionadoEmUmTcc(){
        var tcc = new Tcc();
        var alunoCriado = criaUsuario();

        var tccAtualizado = tccUtil.adicionarAlunoNoTcc(tcc, alunoCriado);

        assertEquals(alunoCriado ,tccAtualizado.getUsuario());
        assertSame(alunoCriado ,tccAtualizado.getUsuario());
    }

    @Test
    @DisplayName("Teste testar se o tipo de usuario é um aluno")
    public void deveTestarSeUsuarioEAluno(){
        var criarAluno = criaUsuario();

        assertTrue(()->tccUtil.userTypeIsAluno(criarAluno));

    }

    @Test
    @DisplayName("Teste deve lancar uma excecao caso o usuario não é um aluno")
    public void deveLancarExcecaoCasoUsuarioNaoSejaAluno(){
        var professorCriado = criarUsuarioProfessor();
        assertThrows(UsuarioNaoAlunoException.class,
                ()->tccUtil.userTypeIsAluno(professorCriado)
        );

    }

    @Test
    @DisplayName("Teste deve lancar excecao caso o aluno ja tenha TCC")
    public void deveLacarExcecaoCasoAlunoJaTenhaTcc(){
        var criarAluno = criaUsuario();

        given(usuarioRepository.checkSeAlunoTemTcc(criarAluno.getIdUsuario()))
                .willReturn(1L);

        assertThrows(AlunoTemTccException.class,
                ()-> tccUtil.checkSeAlunoTemTcc(criarAluno)
        );

        then(usuarioRepository).should(times(1))
                .checkSeAlunoTemTcc(criarAluno.getIdUsuario());

    }

    @Test
    @DisplayName("Teste deve passar caso o aluno não tenha TCC")
    public void deveLacaPassarCasoAlunoNaoTenhaTcc(){
        var criarAluno = criaUsuario();

        given(usuarioRepository.checkSeAlunoTemTcc(criarAluno.getIdUsuario()))
                .willReturn(0L);

        assertDoesNotThrow(
                ()-> tccUtil.checkSeAlunoTemTcc(criarAluno)
        );

        then(usuarioRepository).should(times(1))
                .checkSeAlunoTemTcc(criarAluno.getIdUsuario());

    }

    @Test
    @DisplayName("Teste deve lancar caso o tcc exista")
    public void deveLancarExcecaoCasoTccExista(){
        var tccCriado = criarTcc();
        given(tccRepository.existsById(tccCriado.getId()))
                .willReturn(false);

        assertThrows(TccNaoExisteException.class,
                ()->tccUtil.checkExistsTcc(tccCriado.getId()
        ));

        then(tccRepository).should(times(1))
                .existsById(tccCriado.getId());
    }

    @Test
    @DisplayName("Teste deve passar caso o tcc não exista")
    public void deveLancarExcecaoCasoTccNaoExista(){
        var tccCriado = criarTcc();
        given(tccRepository.existsById(tccCriado.getId()))
                .willReturn(true);

        assertDoesNotThrow(()->tccUtil.checkExistsTcc(tccCriado.getId()));
        assertTrue(()->tccUtil.checkExistsTcc(tccCriado.getId()));


        then(tccRepository).should(times(2))
                .existsById(tccCriado.getId());
    }

    @Test
    @DisplayName("Teste deve passar caso o tcc não exista na hora de sua criação")
    public void devePassarCasoTccNaoExistaNaHoraDeSuaCriacao(){
        var tccCriado = criarTcc();

        given(tccRepository.existsById(tccCriado.getId())).willReturn(false);

        assertTrue(()->tccUtil.checkExistsTccpParaCriacao(tccCriado.getId()));
        assertDoesNotThrow(()->tccUtil.checkExistsTccpParaCriacao(tccCriado.getId()));

        then(tccRepository).should(times(2)).existsById(tccCriado.getId());
    }

    @Test
    @DisplayName("Teste deve lancar excecao caso o tcc exista na hora de sua criação")
    public void deveLancarExececaoCasoTccExistaNaHoraDeSuaCriacao(){
        var tccCriado = criarTcc();

        given(tccRepository.existsById(tccCriado.getId())).willReturn(true);

        assertThrows(TccExisteException.class,()->tccUtil.checkExistsTccpParaCriacao(tccCriado.getId()));

        then(tccRepository).should(times(1)).existsById(tccCriado.getId());
    }

    @Test
    @DisplayName("Teste deve passar caso encontre o tcc")
    public void devePassarAoBuscarUmTccPorId(){
        var tccCriado = criarTcc();

        given(tccRepository.findById(tccCriado.getId())).willReturn(Optional.of(tccCriado));

        var tccEncontrado = tccUtil.buscarTcc(tccCriado.getId());
        assertEquals(tccCriado, tccEncontrado);

        then(tccRepository).should(times(1)).findById(tccCriado.getId());
    }

    @Test
    @DisplayName("Testa se as instancias dos objetos sao diferentes mas o conteudo é igual na " +
            "hora da construção de um molde para o tcc")
    public void verificaSeAsInstanciasSaoDiferentesPoremConteudoIgual(){

        var tccNovoCriado = criarTcc();
        var tccAntigoCriado = criarOutroTcc();

        var res = tccUtil.moldeBasicoTcc(tccAntigoCriado , tccNovoCriado);
        assertNotSame(tccNovoCriado, res ,"As instancias devem ser diferentes");

        assertEquals(tccNovoCriado.getId(), res.getId());
        assertEquals(tccNovoCriado.getUsuario(), res.getUsuario());
        assertEquals(tccNovoCriado.getTitulo(), res.getTitulo());
        assertEquals(tccNovoCriado.getTema(), res.getTema());
        assertEquals(tccNovoCriado.getCurso(), res.getCurso());

    }

    @Test
    @DisplayName("Deve passar caso exista tcc no banco")
    public void devePassarCasoExistaNaBanco(){

        given(tccRepository.count()).willReturn(1L);
        assertEquals(1L ,tccUtil.countDeTccs());

        then(tccRepository).should(times(1)).count();
    }

    @Test
    @DisplayName("Deve lancar excecao caso não exista tcc no banco")
    public void deveLancarExcecaoCasoNaoExistaNaBanco(){

        given(tccRepository.count()).willReturn(0L);
        assertThrows(NaoExisteTccNoBancoException.class, ()->tccUtil.countDeTccs());

        then(tccRepository).should(times(1)).count();
    }

    @Test
    @DisplayName("Deve listar uma lista com um elemento ")
    public void deveListarUmaListaComUmElemento(){
        var tccCriado = criarTcc();
        given(tccRepository.findAll()).willReturn(Collections.singletonList(tccCriado));
        List<Tcc> listTcc = tccUtil.buscarTodosOsTcc();
        assertTrue(listTcc.size()>0);
        then(tccRepository).should(times(1)).findAll();
    }

    @Test
    @DisplayName("Deve lancar excecao caso nao econtre tcc para ser relacioado a uma apresentacao")
    public void deveLancarExcecaoCasoNaoEncontreTccParaSerRelacioado(){
        given(tccRepository.countTccComApresentcao())
                .willReturn(0L);
        assertThrows(NaoExisteTccParaRelacionarComApresentacoesException.class,
                ()->tccUtil.countDeTccsSemApresentacao());
        then(tccRepository).should(times(1)).countTccComApresentcao();
    }

    @Test
    @DisplayName("Deve passar caso econtre tcc para ser relacioado a uma apresentacao")
    public void devePassarCasoEncontreTccParaSerRelacioado(){
        given(tccRepository.countTccComApresentcao())
                .willReturn(8L);
        assertEquals(8,tccUtil.countDeTccsSemApresentacao());
        then(tccRepository).should(times(2)).countTccComApresentcao();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia com um tcc sem apresentacao")
    public void deveLancarUmaListaVaziaTccSemApresentacao(){
        var tccCriado = criarTcc();
        given(tccRepository.listTccSemApresentcao()).willReturn(Collections.singletonList(tccCriado));
        List<Tcc> listaTccSemApresentacao = tccUtil.listaDeTccSemApresentacao();
        assertTrue(listaTccSemApresentacao.size()>0);
        then(tccRepository).should(times(1)).listTccSemApresentcao();
    }

    @Test
    @DisplayName("Deve funcionar caso o aluno tenha tcc relacionado a ele")
    public void devePassarCasoAlunoTenhaTcc(){
        var usuarioCriado = criarUsuarioProfessor();
        given(tccRepository.buscaTccAluno(usuarioCriado.getIdUsuario()))
                .willReturn(1L);
        assertFalse(
                ()->tccUtil.checkSeAlunoTemTccSemExecao(usuarioCriado)
        );
        then(tccRepository).should(times(1))
                .buscaTccAluno(usuarioCriado.getIdUsuario());
    }

    @Test
    @DisplayName("Deve funcionar caso o aluno não tenha tcc relacionado a ele")
    public void devePassarCasoAlunoNaoTenhaTcc(){
        var usuarioCriado = criarUsuarioProfessor();
        given(tccRepository.buscaTccAluno(usuarioCriado.getIdUsuario()))
                .willReturn(0L);
        assertTrue(
                ()->tccUtil.checkSeAlunoTemTccSemExecao(usuarioCriado)
        );
        then(tccRepository).should(times(1))
                .buscaTccAluno(usuarioCriado.getIdUsuario());
    }

    @Test
    @DisplayName("Deve passar ao remover um aluno de um tcc")
    public void devePassarAoRemoverAlunoDeUmTcc(){
        var alunoCriado = criaUsuario();

        tccUtil.removendoAlunoDeUmTcc(alunoCriado.getIdUsuario());
        then(tccRepository).should(times(1))
                .removerDiscenteTcc(alunoCriado.getIdUsuario());
    }

    @Test
    @DisplayName("Deve atualizar o relacionamento de um aluno antigo para um aluno novo ")
    public void deveAtualizarRelacionamentoDeAlunosComUmTcc(){
        var tccCriado = criarTcc();
        var alunoNovoCriado = criaUsuario();

        var resultado = tccUtil.trocandoORelacionamentoDeAlunoComTcc(tccCriado,alunoNovoCriado);

        assertEquals(alunoNovoCriado, resultado.getUsuario());
        assertEquals(tccCriado, resultado);
        assertSame(tccCriado, resultado);

        then(usuarioRepository).should(times(1)).save(alunoNovoCriado);

     }

    @Test
    @DisplayName("Deve lancar excecao caso o aluno seja nulo ")
    public void deveLancarExcecaoCasoAlunoSejaNulo(){
        var tccCriado = criarTcc();
        given(usuarioRepository.save(null)).willThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class ,
                ()->tccUtil.trocandoORelacionamentoDeAlunoComTcc(tccCriado,null));
        then(usuarioRepository).should(times(1)).save(null);

    }

}