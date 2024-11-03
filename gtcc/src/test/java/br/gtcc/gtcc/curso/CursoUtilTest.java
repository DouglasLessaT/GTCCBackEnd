package br.gtcc.gtcc.curso;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.BDDMockito.times;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.CursoRepository;
import br.gtcc.gtcc.services.impl.mysql.CursoService;
import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.cursos.CursoInativoException;
import br.gtcc.gtcc.util.exceptions.cursos.CursoNaoExisteExeception;
import br.gtcc.gtcc.util.services.CursoUtil;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
public class CursoUtilTest {
    
    @InjectMocks
    private CursoUtil cursoUtil;

    @Mock
    private CursoRepository cursoRepository;

    //private Curso curso;

    @BeforeEach
    public void setUp(){
        //MockitoAnnotations.openMocks(this);
    }

    private Curso criarCursoInativo(){
        return new Curso(1L ,"S.I", 0);
    }
    private Curso criarCurso(){
        return new Curso(1L ,"S.I", 1);
    }
    private Curso criarCursoInvĺido(){
        return new Curso(null ,"S.I", 1);
    }

    @Test
    @DisplayName("Teste de salvamento de curso")
    public void salvarCurso(){
        var cursoCriado = criarCurso();
        given(cursoRepository.save(cursoCriado)).willReturn(cursoCriado);

        Curso cursoTest = cursoUtil.salvarCurso(cursoCriado);
        assertEquals(cursoTest, cursoCriado);

    }

    @Test
    @DisplayName("Testa se o id é valido para criação")
    public void validaId(){
        var cursoCriado = criarCurso();
        
        assertDoesNotThrow(()-> this.cursoUtil.validId(cursoCriado.getId()));
    }

    @Test
    @DisplayName("Testa se o id é invalido para criação e lanca uma execeção")
    public void deveLancarExcecaoAoValidarId(){
        var cursoCriado = criarCursoInvĺido();
        
        assertThrows(IdInvalidoException.class ,()-> this.cursoUtil.validId(cursoCriado.getId()));
    }

    @Test
    @DisplayName("Testa se o id é valido para criação")
    public void validaIdParaUpdate(){
        var cursoCriado = criarCursoInvĺido();
        
        assertDoesNotThrow(()-> this.cursoUtil.validIdForUpdate(cursoCriado.getId()));
    }

    @Test
    @DisplayName("Testa se o id é valido para criação")
    public void deveLancarExcecaoAoValidarIdParaUpdate(){
        var cursoCriado = criarCurso();
        
        assertThrows(IdInvalidoException.class ,()-> this.cursoUtil.validIdForUpdate(cursoCriado.getId()));
    }

    @Test
    @DisplayName("Teste para verificar se o curso existe")
    public void existeCurso(){
        
        var cursoCriado = criarCurso();
        given(cursoRepository.existsById(cursoCriado.getId())).willReturn(true);
        Boolean existsCurso = cursoUtil.existeCurso(cursoCriado.getId());
        assertTrue(existsCurso);
    }

    @Test
    @DisplayName("Teste deve lançar uma execeção caso o curso não exista")
    public void deveLancarExececaoCasoNaoExistaCurso(){

        var cursoCriado = criarCurso();

        given(cursoRepository.existsById(cursoCriado.getId())).willReturn(false);
        assertThrows(CursoNaoExisteExeception.class , () -> cursoUtil.existeCurso(cursoCriado.getId()));
    }

    @Test
    @DisplayName("Testando a busca de curso")
    public void buscarCursosPorId(){
        var cursoCriado = criarCurso();

        given(cursoRepository.findById(cursoCriado.getId())).willReturn(Optional.of(cursoCriado));
        Optional<Curso> cursoEncontrado = Optional.ofNullable(cursoUtil.buscarCurso(cursoCriado.getId()));
        assertTrue(cursoEncontrado.isPresent());
        
    }
    
    @Test
    @DisplayName("Testando a busca de curso")
    public void buscarCursosPorIdDeveLancarExececaoCasoNaoExista(){
        var cursoCriado = criarCurso();
        assertThrows(NoSuchElementException.class, ()-> cursoUtil.buscarCurso(cursoCriado.getId()));
        
    }

    @Test
    @DisplayName("Teste deve trazar uma lista com um Curso")
    public void deveBuscarUmaListaComUmCurso(){
        var cursoCriado = criarCurso();

        given(cursoRepository.findAll()).willReturn(Collections.singletonList(cursoCriado));
        Integer listaCursos = cursoUtil.listaCursos().size();
        assertTrue(listaCursos > 0);
    }    

    @Test
    @DisplayName("Teste deve lancar execao caso a lista seja vazia")
    public void  deveLancarExecaoCasoListaVazia(){
        List<Curso> listaVaziaDeCursos = new ArrayList<>();
        given(cursoRepository.findAll()).willReturn(listaVaziaDeCursos);
        Integer listaCursosVazio = cursoUtil.listaCursos().size();
        assertFalse(listaCursosVazio > 0);
    }    

    @Test 
    @DisplayName("Teste deve deletar um curso com sucesso")
    public void testeExclusaoDeCurso(){
        var cursoCriado = criarCurso();
        
        cursoUtil.deleteCurso(cursoCriado.getId());
        then(cursoRepository).should(times(1)).deleteById(cursoCriado.getId());
       
    }

    @Test 
    @DisplayName("Teste deve lancar excecao caso ele nao encontre o curso")
    public void deveLancarExececaoCasoNaoEncontreOCurso(){
        var cursoInexistente = criarCurso();

        willThrow(new EmptyResultDataAccessException(1)).given(cursoRepository).deleteById(cursoInexistente.getId());
        assertThrows(EmptyResultDataAccessException.class, ()->cursoUtil.deleteCurso(cursoInexistente.getId()));
        then(cursoRepository).should(times(1)).deleteById(cursoInexistente.getId());

    }
    
    @Test
    @DisplayName("Teste inativacao de curso")
    public void deveInativarCurso(){
        var cursoCriado = criarCurso();

        cursoUtil.inativarCurso(cursoCriado.getId());
        then(cursoRepository).should(times(1)).inativar(cursoCriado.getId());

    }

    @Test
    @DisplayName("Teste método moldeCurso() ")
    public void testeMetodoMoldeCurso(){
        var cursoCriado = criarCurso();

        var cursoMoldado = cursoUtil.moldeCurso(cursoCriado);

        assertEquals(cursoCriado.getAtivo(), cursoMoldado.getAtivo());
        assertEquals(cursoCriado.getTitulo(), cursoMoldado.getTitulo());

        assertNotSame(cursoCriado, cursoMoldado);
    }

    @Test
    @DisplayName("Teste de transferencia de método do objeto")
    public void testeMetodoTransferenciaDeObjeto(){
        var cursoCriado = criarCurso();
        var cursoTransferido = cursoUtil.transferenciaDeObjeto(cursoCriado);

        assertEquals(cursoCriado.getTitulo(), cursoTransferido.getTitulo());

        assertSame(cursoCriado, cursoTransferido);
    }

    @Test
    @DisplayName("Deve retornar verdadeiro caso o curso esta ativo")
    public void deveRetornarVerdadeiroCasoCursoEstejaAtivo(){
        var cursoCriado =  criarCurso();
        var isAtivo = cursoUtil.isAtivo(cursoCriado);
        assertTrue(isAtivo);
    }

    @Test
    @DisplayName("Deve retornar um execao caso o curso seja inativo")
    public void deveRetornarExceaoCasoCursoInativo(){
        var cursoInativoCriado = criarCursoInativo();
        
        assertThrows(CursoInativoException.class,
         ()-> cursoUtil.isAtivo(cursoInativoCriado));
    }
}
