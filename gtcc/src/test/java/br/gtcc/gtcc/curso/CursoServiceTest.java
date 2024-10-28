package br.gtcc.gtcc.curso;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import br.gtcc.gtcc.services.impl.mysql.CursoService;
import br.gtcc.gtcc.util.services.CursoUtil;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    // @Mock
    // private CursoRepository cursoRepositoryMockInjected;
    // @Mock
    // private CursoUtil utilMock;
    // @InjectMocks
    // private CursoService serviceMock;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private CursoUtil cursoUtil;

    @InjectMocks
    private CursoService cursoService;

    // private CursoService cursoService;
    // private CursoRepository cursoRepository;
    // private CursoUtil cursoUtil;


    @BeforeEach
    public void setUp() {
        cursoRepository = mock(CursoRepository.class);
        cursoUtil = new CursoUtil(cursoRepository);
        cursoService = new CursoService(cursoUtil);
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

        assertThrows(RuntimeException.class, 
            () -> cursoService.criarCurso(cursoInativo));
    }

    @Test
    @DisplayName("Deve tesar salvar um curso ativo")
    public void deveSalvarCursoAtivo(){        
        var cursoAtivo = criarCurso();
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
    @DisplayName("Deve retornar um curso por id")
    public void buscarUmCursoPorId(){
        var curso = criarCurso();

        BDDMockito.given(cursoRepository.existsById(curso.getId())).willReturn(true);
        BDDMockito.given(cursoUtil.existeCurso(curso.getId())).willReturn(true);
        BDDMockito.given(cursoRepository.findById(curso.getId())).willReturn(Optional.of(curso));     
        BDDMockito.given(cursoUtil.buscarCurso(1L)).willReturn(curso);
        
        Optional<Curso> cursoEncontrado = Optional.ofNullable(cursoService.buscarCurso(curso.getId()));
        
        assertNotNull(cursoEncontrado);

    }


    private Curso criarCursoInativo(){
        return new Curso(1L ,"S.I", 0);
    }

    private Curso criarCurso(){
        return new Curso(1L ,"S.I", 1);
    }

}
