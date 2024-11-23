package br.gtcc.gtcc.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_COORDENADOR")
@RequestMapping("coordenacao/tcc/v1")
@RequiredArgsConstructor
public class TccController {
  
    @SuppressWarnings("rawtypes")
    private final TccInterface tccInterface;

    @Operation(summary ="Dado um objeto tcc ele criar um tcc, certifique-se de passar o id como zero ")
    @PostMapping("/tcc")
    public ResponseEntity<Object> createTcc(@RequestBody Tcc tcc) {
        log.info("Teste model "+ tcc.toString());
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) tccInterface.createTcc(tcc));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc criado com sucesso", "Erro ao criar tcc");
        
    }

    @Operation(summary ="Dado um objeto tcc e o id ele atualiza o tcc referente ao id passado com o objeto no parametro")
    @PutMapping("/tcc/{id}")
    public ResponseEntity<Object> updateTcc(@PathVariable("id") Long id, @RequestBody Tcc tcc) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> updatedTcc = Optional.ofNullable((Tcc) tccInterface.updateTCC(tcc, id));
        return UtilController.buildResponseFromOptional( updatedTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc alterado com sucesso", "Erro ao alterado tcc");
       
    }

    @Operation(summary ="Dado o id do TCC ele deleta o tcc referente ao id passado")
    @DeleteMapping("/tcc/{id}")
    public ResponseEntity<Object> deleteTcc(@PathVariable("id") Long id) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> deletedTcc = Optional.ofNullable((Tcc) tccInterface.deleteTCC(id));
        return UtilController.buildResponseFromOptional( deletedTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc deletado com sucesso", "Erro ao deletar tcc");
    
    }

    @Operation(summary ="Retornar todos os Tcc's")
    @GetMapping("/tccs")
    public ResponseEntity<Object> getAllTccs() {
        
        @SuppressWarnings("unchecked")
        Optional<List<Tcc>> list = Optional.ofNullable( tccInterface.getAllTCC());
        return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Tcc", "Lista Vazia");
    
    }

    @Operation(summary = "Busca um TCC por ID", description = "Retorna um TCC baseado no seu identificador único.")
    @GetMapping("/tcc/{id}")
    public ResponseEntity<Object> getTccById(@PathVariable("id") Long id) {

        @SuppressWarnings("unchecked")
        Optional<Tcc> foundTcc = Optional.ofNullable( (Tcc) tccInterface.getTCC(id) ) ;
        return UtilController.buildResponseFromOptional( foundTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Tcc econtrado", "Lista Vazia");
    
    }

    @Operation(summary = "Busca um TCC por titulo", description = "Retorna um TCC baseado no seu titulo.")
    @GetMapping("/tcc/")
    public ResponseEntity<Object> getTccByTitle(@RequestParam("title") String title) {

        @SuppressWarnings("unchecked")
        Optional<Tcc> foundTcc = Optional.ofNullable( (Tcc) tccInterface.getTCCByTitle(title) ) ;
        return UtilController.buildResponseFromOptional( foundTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Titulo do tcc econtrado", "Erro ao buscar tcc");

    }
    @Operation(summary = "Busca um TCC sem apresentacao", description = "Retorna um TCC's que não possuem apresentcao.")
    @GetMapping("/tcc/sem-apresentacoes")
    public ResponseEntity<Object> getTccSemApresentation() {

        @SuppressWarnings("unchecked")
        Optional<List<Tcc>> foundTcc = Optional.ofNullable( (List<Tcc>) tccInterface.getTccSemApresentacao() ) ;
        return UtilController.buildResponseFromOptional( foundTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de tcc sem apresentações", "Lista Vazia");

    }

    @Operation(summary = "Relaciona um tcc a um aluno", description = "Adiciona aluno em tcc dado o seu id do tcc e do aluno.")
    @PostMapping("/tcc/adicionar-aluno/{idTcc}/aluno/{idAluno}") 
    public ResponseEntity<Object> adicionarAlunoEmTcc(@PathVariable("idTcc") Long idTcc , @PathVariable("idAluno") Long idAluno) {

        @SuppressWarnings("unchecked")
        Optional<Tcc> tcc = Optional.ofNullable( (Tcc) tccInterface.adicionarAlunoEmTcc(idTcc ,idAluno) ) ;
        return UtilController.buildResponseFromOptional( tcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno adicionado ao tcc", "Erro ao adicionar o aluno no tcc");

    }

}
