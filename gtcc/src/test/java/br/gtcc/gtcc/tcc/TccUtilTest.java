package br.gtcc.gtcc.tcc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.EnumSet;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.BDDMockito.times;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gtcc.gtcc.model.UserType;
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
public class TccUtilTest {

    @InjectMocks
    private TccUtil tccUtil;

    @Mock
    private TccRepository tccRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp(){
        // MockitoAnnotations.openMocks(this);
        // tccRepository = mock(TccRepository.class);
        // usuarioRepository = mock(UsuarioRepository.class);
        // tccUtil = new TccUtil(tccRepository, usuarioRepository);
    }

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
             new Grupo(1L,"TYPE_PROFESSOR",1),
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
        assertDoesNotThrow( ()->tccUtil.deleteTcc(null));
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

}

