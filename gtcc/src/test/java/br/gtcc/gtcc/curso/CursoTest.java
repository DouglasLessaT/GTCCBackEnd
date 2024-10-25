package br.gtcc.gtcc.curso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

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
import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)//INicializa todos os MOcks antes de executar
public class CursoTest {
    
    private CursoService cursoService;
    private CursoRepository cursoRepository;
    private CursoUtil cursoUtil;
    
    @BeforeEach
    public void setUp() {
        cursoRepository = mock(CursoRepository.class);
        cursoUtil = new CursoUtil(cursoRepository);
        cursoService = new CursoService(cursoUtil);
        Curso curso = new Curso( 1L ,"S.I", 1);
        lenient().when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        lenient().when(cursoRepository.findAll()).thenReturn(Collections.singletonList(curso));
    }


    @Test
    @DisplayName("Vai lancar uma runtime exception caso o repositorio esteja vazio")
    public void lancarExececaoCasoRepositorioEstejaVazio(){
        lenient().when(cursoUtil.contagemDeCurso()).thenReturn(0L);
        assertThrows(RuntimeException.class, () -> cursoService.listaCursos());
        verify(cursoUtil).contagemDeCurso();
    }

    @Test
    @DisplayName("Deve lançar exeção quando tentar salvar curso inativo")
    public void naoDeveSalvarCursoInativo(){        
        var cursoInativo = criarCursoInativo();

        assertThrows(RuntimeException.class, 
            () -> cursoService.criarCurso(cursoInativo));
    }

    private Curso criarCursoInativo(){
        return new Curso(1L ,"S.I", 0);
    }

    // @Test
    // @DisplayName("Deve retornar uma lista com apenas um curso")
    // public void buscarTodosOsCursos(){
    //     List<Curso> listaCursos = cursoService .listaCursos();
    //     assertEquals(1, listaCursos.size());

    //     verify(cursoRepository).findAll();
    //     verifyNoInteractions(cursoRepository);
    // }

    // @Test
    // @DisplayName("Deve retornar um curso por id")
    // public void buscarUmCursoPorId(){

    //     assertThrows(RuntimeException.class, () -> cursoService.buscarCurso(1L));

    // }

}
