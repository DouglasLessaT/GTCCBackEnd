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
import br.gtcc.gtcc.util.Console;

import java.util.Optional;

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
        Optional<Agenda> createdData = Optional.ofNullable((Agenda) interfaceAgenda.createAgenda(agenda));
        
        if (createdData.isPresent()) {
            
            return ResponseEntity.status(HttpStatus.CREATED).body("Data criada com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar Data");
        
        }
    }

    @PutMapping("/agenda/{id}")
    public ResponseEntity<Object> updateAgenda(@PathVariable("id") String id, @RequestBody Agenda agenda) {
       
        @SuppressWarnings("unchecked")
        Optional<Agenda> updatedAgenda = Optional.ofNullable((Agenda) interfaceAgenda.updateAgenda(id ,agenda));
       
        if (updatedAgenda.isPresent()) {
       
            return ResponseEntity.status(HttpStatus.OK).body("Data atualizada com sucesso");
       
        } else {
       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar Data");
       
        }
    }

    @SuppressWarnings("unchecked")
    @DeleteMapping("/agenda/{id}")
    public ResponseEntity<Object> deleteAgenda(@PathVariable("id") String id ) {
       
        Optional<Agenda> deletedAgenda = Optional.ofNullable((Agenda) interfaceAgenda.deleteAgenda(id));
        
        if (deletedAgenda.isPresent()) {
       
            return ResponseEntity.status(HttpStatus.OK).body("Data deletada com sucesso");
       
        } else {
       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar Data");
       
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/agendas")
    public ResponseEntity<Object> getAllAgendas() {
        
        Console.log("TEstre controller");
        List<Optional<Agenda>> agendas = (List<Optional<Agenda>>) interfaceAgenda.getAllAgenda();
        
        Console.log("TEstre controller II ");
        if(agendas.isEmpty() != true){

            return new ResponseEntity<>(agendas, HttpStatus.OK);
            
        }else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sem datas cadatradas");
            
        }
            
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/agenda/{id}")
    public ResponseEntity<Object> getDataById(@PathVariable("id") String id) {
    
        Optional<Agenda> foundAgenda = Optional.ofNullable((Agenda) interfaceAgenda.getAgenda(id));
        
        if (foundAgenda.isPresent()) {
        
            return new ResponseEntity<>(foundAgenda, HttpStatus.OK);
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data não encontrada");
            
        }
    }
}
