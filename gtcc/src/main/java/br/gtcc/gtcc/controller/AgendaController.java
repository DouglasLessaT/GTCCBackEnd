package br.gtcc.gtcc.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import br.gtcc.gtcc.model.neo4j.Agenda;
import br.gtcc.gtcc.services.spec.AgendaInterface;
import br.gtcc.gtcc.util.UtilController;
import java.util.Optional;
import br.gtcc.gtcc.util.Console;

@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class AgendaController {
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private AgendaInterface interfaceAgenda;
    
     @PostMapping("/agenda")
    public ResponseEntity<Object> createAgenda(@RequestBody Agenda agenda) {
        
        @SuppressWarnings("unchecked")
        Optional<Agenda> createdAgenda = Optional.ofNullable((Agenda) interfaceAgenda.createAgenda(agenda));
        return UtilController.buildResponseFromOptional( createdAgenda, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Agenda criado com sucesso", "Erro ao criar agenda");
        
    }

    @PutMapping("/agenda/{id}")
    public ResponseEntity<Object> updateAgenda(@PathVariable("id") String id, @RequestBody Agenda agenda) {
       
        @SuppressWarnings("unchecked")
        Optional<Agenda> updatedAgenda = Optional.ofNullable((Agenda) interfaceAgenda.updateAgenda(id ,agenda));
        return UtilController.buildResponseFromOptional( updatedAgenda, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Agenda alterada com sucesso", "Erro ao alterar agenda");
        
    }

    @DeleteMapping("/agenda/{id}")
    public ResponseEntity<Object> deleteAgenda(@PathVariable("id") String id ) {

        @SuppressWarnings("unchecked")
        Optional<Agenda> deletedAgenda = Optional.ofNullable((Agenda) interfaceAgenda.deleteAgenda(id));
        return UtilController.buildResponseFromOptional( deletedAgenda, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Agenda deletada com sucesso", "Erro ao deletar agenda");
        
    }

    @GetMapping("/agendas")
    public ResponseEntity<Object> getAllAgendas() {

        @SuppressWarnings("unchecked")
        Optional<List<Agenda>> agendas = Optional.ofNullable(interfaceAgenda.getAllAgenda());
        return UtilController.buildResponseFromOptional( agendas, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de agendas", "Lista Vazia");
            
    }

    @GetMapping("/agenda/{id}")
    public ResponseEntity<Object> getDataById(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<Agenda> foundAgenda = Optional.ofNullable((Agenda) interfaceAgenda.getAgenda(id));
        return UtilController.buildResponseFromOptional( foundAgenda, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Agenda encontrada", "Erro ao encontrar agenda");

    }

    @GetMapping("/agendas-livres")
    public ResponseEntity<Object> getAllAgendasFree() {

        @SuppressWarnings("unchecked")
        Optional<List<Agenda>> agendas = Optional.ofNullable( interfaceAgenda.getAllAgendasFree());
        return UtilController.buildResponseFromOptional( agendas, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de agendas livres", "Lista Vazia");

    }

    @PostMapping("/agenda/{idAgenda}/adiciona/{idApresentacao}")
    public ResponseEntity<Object> adicionarApresentacaoEmAgenda(@PathVariable("idAgenda") String idAgenda ,@PathVariable("idApresentaoca") String idApresencao) {

        @SuppressWarnings("unchecked")
        Optional<Agenda> agendas = Optional.ofNullable( (Agenda) interfaceAgenda.adicionarApresentacaoEemAgenda(idAgenda ,idApresencao));
        return UtilController.buildResponseFromOptional( agendas, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentacao adicionada a Agenda", "Erro ao adicionar apresentacao");

    }
}
