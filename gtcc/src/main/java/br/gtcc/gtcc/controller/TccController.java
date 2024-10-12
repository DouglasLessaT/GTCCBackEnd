package br.gtcc.gtcc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.util.Console;
import br.gtcc.gtcc.util.UtilController;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_COORDENADOR")
@RequestMapping("coordenacao/tcc/v1")
public class TccController {
  
    @SuppressWarnings("rawtypes")
    @Autowired
    private TccInterface tccInterface; 
 
    @PostMapping("/tcc")
    public ResponseEntity<Object> createTcc(@RequestBody Tcc tcc) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) tccInterface.createTcc(tcc));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc criado com sucesso", "Erro ao criar tcc");
        
    }

    @PutMapping("/tcc/{id}")
    public ResponseEntity<Object> updateTcc(@PathVariable("id") String id, @RequestBody Tcc tcc) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> updatedTcc = Optional.ofNullable((Tcc) tccInterface.updateTCC(tcc, id));
        return UtilController.buildResponseFromOptional( updatedTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc alterado com sucesso", "Erro ao alterado tcc");
       
    }

    @DeleteMapping("/tcc/{id}")
    public ResponseEntity<Object> deleteTcc(@PathVariable("id") String id) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> deletedTcc = Optional.ofNullable((Tcc) tccInterface.deleteTCC(id));
        return UtilController.buildResponseFromOptional( deletedTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc deletado com sucesso", "Erro ao deletar tcc");
    
    }
    
    @GetMapping("/tccs")
    public ResponseEntity<Object> getAllTccs() {
        
        @SuppressWarnings("unchecked")
        Optional<List<Tcc>> list = Optional.ofNullable( tccInterface.getAllTCC());
        return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Tcc", "Lista Vazia");
    
    }

    @GetMapping("/tcc/{id}")
    public ResponseEntity<Object> getTccById(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<Tcc> foundTcc = Optional.ofNullable( (Tcc) tccInterface.getTCC(id) ) ;
        return UtilController.buildResponseFromOptional( foundTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc econtrado", "Lista Vazia");
    
    }

    @GetMapping("/tcc/")
    public ResponseEntity<Object> getTccByTitle(@RequestParam("title") String title) {

        @SuppressWarnings("unchecked")
        Optional<Tcc> foundTcc = Optional.ofNullable( (Tcc) tccInterface.getTCCByTitle(title) ) ;
        return UtilController.buildResponseFromOptional( foundTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Titulo do tcc econtrado", "Erro ao buscar tcc");

    }

    @GetMapping("/tcc/sem-apresentacoes")
    public ResponseEntity<Object> getTccSemApresentation() {

        @SuppressWarnings("unchecked")
        Optional<List<Tcc>> foundTcc = Optional.ofNullable( (List<Tcc>) tccInterface.getTccSemApresentacao() ) ;
        return UtilController.buildResponseFromOptional( foundTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de tcc sem apresentações", "Lista Vazia");

    }

    @PostMapping("/tcc/adicionar-aluno/{idTcc}/aluno/{idAluno}") 
    public ResponseEntity<Object> adicionarAlunoEmTcc(@PathVariable("idTcc") String idTcc , @PathVariable("idAluno") String idAluno) {

        @SuppressWarnings("unchecked")
        Optional<Tcc> tcc = Optional.ofNullable( (Tcc) tccInterface.adicionarAlunoEmTcc(idTcc ,idAluno) ) ;
        return UtilController.buildResponseFromOptional( tcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno adicionado ao tcc", "Erro ao adicionar o aluno no tcc");

    }

    @PostMapping("/tcc/adicionar-aluno/{idTcc}/orientador/{idOrientador}")
    public ResponseEntity<Object> adicionarOrientadorEmTcc(@PathVariable("idTcc") String idTcc , @PathVariable("idOrientador") String idOrientador) {

        @SuppressWarnings("unchecked")
        Optional<Tcc> tcc = Optional.ofNullable( (Tcc) tccInterface.adicionarAlunoEmTcc(idTcc ,idOrientador) ) ;
        return UtilController.buildResponseFromOptional( tcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Orientador adicionado ao tcc", "Erro ao adicionar o aluno no tcc");

    }
}
