package br.gtcc.gtcc.curso;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.BDDMockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.repository.CursoRepository;
import br.gtcc.gtcc.services.exception.CursosNaoCadastradosException;
import br.gtcc.gtcc.services.impl.mysql.CursoService;
import br.gtcc.gtcc.util.services.CursoUtil;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {
    
    @InjectMocks
    private CursoService cursoService;
    
    @Mock
    private CursoUtil cursoUtil;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("Vai lancar uma runtime exception caso o repositorio esteja vazio")
    public void lancarExececaoCasoRepositorioEstejaVazio(){
        lenient().when(cursoUtil.contagemDeCurso()).thenReturn(0L);
        assertThrows(RuntimeException.class, () -> cursoService.listaCursos());
    }

    @Test
    @DisplayName("Deve lançar exeção quando tentar salvar curso inativo")
    public void naoDeveSalvarCursoInativo(){        
        var cursoInativo = criarCursoInativo();
        BDDMockito.given(cursoUtil.isAtivo(cursoInativo)).willThrow(new RuntimeException("Não pode criar curso inativo"));
        assertThrows(RuntimeException.class, 
            () -> cursoService.criarCurso(cursoInativo));
    }

    @Test
    @DisplayName("Deve tesar salvar um curso ativo")
    public void deveSalvarCursoAtivo(){        
        var cursoAtivo = criarCurso();
        BDDMockito.given(cursoUtil.isAtivo(cursoAtivo)).willReturn(true);
        assertDoesNotThrow(() -> cursoService.criarCurso(cursoAtivo));
    }

    @Test
    @DisplayName("Deve retornar uma lista com apenas um curso")
    public void buscarTodosOsCursos(){
        var curso = this.criarCurso();

        BDDMockito.given(this.cursoUtil.contagemDeCurso()).willReturn(1L);
        BDDMockito.given(this.cursoUtil.listaCursos()).willReturn(Collections.singletonList(curso));

        List<Curso> listaCursos = cursoService .listaCursos();
        assertEquals(1, listaCursos.size());

    }

    @Test
    @DisplayName("Teste deve lancar excecao quando nao encontrar nehum curso cadastrado")
    public void deveLancarExcecaoCasoNaoEcontreNenhumCurso(){
       
        given(cursoUtil.contagemDeCurso()).willReturn(0L);
        assertThrows(CursosNaoCadastradosException.class, ()->cursoService.listaCursos());
        then(cursoUtil).should(times(1)).contagemDeCurso();
    }

    @Test
    @DisplayName("Deve retornar um curso por id")
    public void buscarUmCursoPorId(){
        var curso = criarCurso();

        BDDMockito.given(cursoUtil.existeCurso(curso.getId())).willReturn(true);
        BDDMockito.given(cursoUtil.buscarCurso(1L)).willReturn(curso);
        
        Optional<Curso> cursoEncontrado = Optional.ofNullable(cursoService.buscarCurso(curso.getId()));
        
        assertNotNull(cursoEncontrado);

    }

    @Test
    @DisplayName("Teste de exclusao de curso com sucesso")
    public void deletarCursoPorId(){
        var cursoCriado = criarCurso();

        given(cursoUtil.validId(cursoCriado.getId()))
        .willReturn(true);
        given(cursoUtil.existeCurso(cursoCriado.getId()))
        .willReturn(true);
        given(cursoUtil.buscarCurso(cursoCriado.getId()))
        .willReturn(cursoCriado);

        var cursoDeletado = cursoUtil.buscarCurso(cursoCriado.getId());
        assertEquals(cursoCriado, cursoDeletado);
        cursoService.deletarCurso(cursoDeletado.getId());
        
        then(cursoUtil).should(times(1)).validId(cursoCriado.getId());
        then(cursoUtil).should(times(1)).existeCurso(cursoCriado.getId());
        then(cursoUtil).should(times(2)).buscarCurso(cursoCriado.getId());
        then(cursoUtil).should(times(1)).deleteCurso(cursoCriado.getId());
       
    }

    @Test
    @DisplayName("Teste deve lancar exececao caso nao encontre o curso")
    public void deveFalharCasoNaoEncontreCurso(){
        var cursoCriado = criarCurso();
        
        Long id = cursoCriado.getId();
        given(cursoUtil.existeCurso(id)).willThrow(new RuntimeException("O Curso não existe"));

        assertThrows(RuntimeException.class, ()->cursoService.deletarCurso(id));

        then(cursoUtil).should(times(1)).existeCurso(id);

    }

    @Test
    @DisplayName("Teste a alteracao com sucesso")
    public void devePassarAlteracaoDeCurso(){
        var cursoOld = criarCurso();
        var crusoNew = criarCursoInativo();
        var newId = crusoNew.getId();
        var oldId = cursoOld.getId();

        given(cursoUtil.validIdForUpdate(newId)).willReturn(true);
        given(cursoUtil.moldeCurso(crusoNew)).willReturn(crusoNew);
        given(cursoUtil.salvarCurso(crusoNew)).willReturn(crusoNew);

        cursoService.alterarCurso(oldId, crusoNew);
        assertEquals(newId, oldId);

        then(cursoUtil).should(times(1)).validIdForUpdate(newId);
        then(cursoUtil).should(times(1)).moldeCurso(crusoNew);
        then(cursoUtil).should(times(1)).salvarCurso(crusoNew);
    }

    @Test
    @DisplayName("Teste deve lancar uma exececao caso ele passe o id do curso invalido")
    public void deveLancarExececaoCasoIdSejaInvalido(){
        var cursoOld = criarCurso();
        var crusoNew = criarCursoInativo();
        var oldId = cursoOld.getId();

        given(cursoUtil.validIdForUpdate(oldId)).willThrow(new RuntimeException("O id é invalido"));
        assertThrows(RuntimeException.class, ()-> cursoService.alterarCurso(oldId, crusoNew));
        then(cursoUtil).should(times(1)).validIdForUpdate(oldId);
    }

    private Curso criarCursoInativo(){
        return new Curso(1L ,"S.I", 0);
    }

    private Curso criarCurso(){
        return new Curso(1L ,"S.I", 1);
    }

}