package br.gtcc.gtcc.controller;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.services.spec.CursoInterface;
import br.gtcc.gtcc.util.UtilController;
import lombok.RequiredArgsConstructor;

import br.gtcc.gtcc.util.Console;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("coordenacao/tcc/v1")
@ValidaAcesso("ROLE_COORDENADOR")
@RequiredArgsConstructor
@Slf4j
public class CursoController {

    private final CursoInterface cursoInterface;

    @SuppressWarnings("unchecked")
    @PostMapping("/curso/")
    public ResponseEntity<Object> criacaoDeCurso(@RequestBody Curso curso) {

        Optional<Curso> cursoCriado = Optional.of( (Curso) cursoInterface.criarCurso(curso) );   
        return UtilController.buildResponseFromOptional(cursoCriado , HttpStatus.OK , HttpStatus.BAD_REQUEST, "Curso criada com sucesso" , "Erro ao criar curso");

    }

    @SuppressWarnings("unchecked")
    @PutMapping("/curso/{id}")
    public ResponseEntity<Object> atualizacaDeCurso(@PathVariable Long id, @RequestBody Curso curso) {

        Optional<Curso> cursoAlterado = Optional.of( (Curso) cursoInterface.alterarCurso(id ,curso) );   
        return UtilController.buildResponseFromOptional( cursoAlterado, HttpStatus.OK , HttpStatus.BAD_REQUEST, "Curso Alterada com sucesso" , "Erro ao Alterado curso");
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/curso/")
    public ResponseEntity<Object> listarCursos() {

        Optional<List<Curso>> listaDeCurso = Optional.of( (List<Curso>) cursoInterface.listaCursos() );   
        return UtilController.buildResponseFromOptional( listaDeCurso, HttpStatus.OK , HttpStatus.BAD_REQUEST, "Lista de curso" , "Lista Vazia");
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/curso/{id}")
    public ResponseEntity<Object> buscarCurso(@RequestParam Long id) {

        Optional<Curso> cursoEcontrado = Optional.of( (Curso) cursoInterface.buscarCurso(id) );   
        return UtilController.buildResponseFromOptional( cursoEcontrado, HttpStatus.OK , HttpStatus.BAD_REQUEST, "Curso encontrada" , "Error ao buscar Curso");
    }

    @SuppressWarnings("unchecked")
    @DeleteMapping("/curso/{id}")
    public ResponseEntity<Object> deletarCurso(@RequestParam Long idCurso) {

        Optional<Curso> cursoEcontrado = Optional.of( (Curso) cursoInterface.deletarCurso(idCurso) );   
        return UtilController.buildResponseFromOptional( cursoEcontrado, HttpStatus.OK , HttpStatus.BAD_REQUEST, "Curso deletada com sucesso!" , "Error ao deletar Curso");
    }
    
}
