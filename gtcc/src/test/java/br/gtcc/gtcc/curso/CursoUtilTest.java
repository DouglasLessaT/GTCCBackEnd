package br.gtcc.gtcc.curso;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Collections;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.CursoRepository;
import br.gtcc.gtcc.services.impl.mysql.CursoService;
import br.gtcc.gtcc.util.services.CursoUtil;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
public class CursoUtilTest {
    
    @InjectMocks
    private CursoUtil cursoUtil;

    @Mock
    private CursoRepository cursoRepository;


    @BeforeEach
    public void setUp(){

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
        BDDMockito.given(cursoRepository.save(cursoCriado)).willReturn(cursoCriado);

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
        
        assertThrows(RuntimeException.class ,()-> this.cursoUtil.validId(cursoCriado.getId()));
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
        
        assertThrows(RuntimeException.class ,()-> this.cursoUtil.validIdForUpdate(cursoCriado.getId()));
    }

    @Test
    @DisplayName("Teste para verificar se o curso existe")
    public void existeCurso(){
        
        var cursoCriado = criarCurso();

        BDDMockito.given(cursoRepository.existsById(cursoCriado.getId())).willReturn(true);

        Boolean existsCurso = cursoUtil.existeCurso(cursoCriado.getId());

        assertTrue(existsCurso);
    }

    @Test
    @DisplayName("Teste deve lançar uma execeção caso o curso não exista")
    public void deveLancarExececaoCasoNaoExistaCurso(){

        var cursoCriado = criarCurso();

        BDDMockito.given(cursoRepository.existsById(cursoCriado.getId())).willReturn(false);
        assertThrows(RuntimeException.class , () -> cursoUtil.existeCurso(cursoCriado.getId()));
    }

    @Test
    @DisplayName("Testando a busca de curso")
    public void buscarCursosPorId(){
        var cursoCriado = criarCurso();

        BDDMockito.given(cursoRepository.findById(cursoCriado.getId())).willReturn(Optional.of(cursoCriado));
        Optional<Curso> cursoEncontrado = Optional.ofNullable(cursoUtil.buscarCurso(cursoCriado.getId()));
        assertTrue(cursoEncontrado.isPresent());
        
    }
    
    @Test
    @DisplayName("Testando a busca de curso")
    public void buscarCursosPorIdDeveLancarExececaoCasoNaoExista(){
        var cursoCriado = criarCurso();
        assertThrows(NoSuchElementException.class, ()-> cursoUtil.buscarCurso(cursoCriado.getId()));
        
    }
    

}
