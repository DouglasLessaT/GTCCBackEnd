package br.gtcc.gtcc.curso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.CursoRepository;
import br.gtcc.gtcc.services.impl.mysql.CursoService;
import br.gtcc.gtcc.util.services.CursoUtil;


@ExtendWith(MockitoExtension.class)//INicializa todos os MOcks antes de executar
public class CursoTest {
    
    @InjectMocks //Usado na classe principla
    private  CursoService cursoService;

    @Mock//Usado nas dependencias da classe principal 
    private CursoRepository cursoRepository;

    @Mock
    private CursoUtil cursoUtil;
    
    @BeforeEach
    public void setUp() {
        Curso curso = new Curso( 1L ,"S.I", 0);
        lenient().when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        lenient().when(cursoRepository.findAll()).thenReturn(Collections.singletonList(curso));
    }


    @Test
    @DisplayName("Deve retornar uma lista com apenas um curso")
    public void buscarTodosOsCursos(){

        // Curso curso = new Curso((long) 1 ,"S.I", 0);
        // Mockito.when(cursoRepository.findAll()).thenReturn(Collections.arrayToList(curso));
        List<Curso> listaCursos = cursoService .listaCursos();
        assertEquals(1, listaCursos.size());

        verify(cursoRepository).findAll();
        verifyNoInteractions(cursoRepository);
    }

    @Test
    @DisplayName("Deve lançar exeção quando tentar salvar curso inativo")
    public void naoDeveSalvarCursoInativo(){
        assertThrows(RuntimeException.class, () -> cursoService .criarCurso(curso()));
        verifyNoInteractions(cursoRepository);
    }

    @Test
    @DisplayName("Deve retornar um curso por id")
    public void buscarUmCursoPorId(){

        assertThrows(RuntimeException.class, () -> cursoService.buscarCurso(1L));

    }

    private Curso curso(){
        return new Curso((long) 1 ,"S.I", 1);
    }

}
