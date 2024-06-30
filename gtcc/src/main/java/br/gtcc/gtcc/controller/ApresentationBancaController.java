package br.gtcc.gtcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Agenda;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.services.spec.AgendaInterface;
import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;
import br.gtcc.gtcc.util.Console;

import java.util.List;// pode usar para apresentaçao
import java.util.Optional;

@CrossOrigin
@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@RequestMapping("coordenacao/tcc/v1")
public class ApresentationBancaController {

    @SuppressWarnings("rawtypes")
    @Autowired
    private AgendaInterface interfaceAgenda;

    @SuppressWarnings("rawtypes")
    @Autowired
    private ApresentationBancaInterface interfaceBanca;

    @PostMapping("/apresentacao")
    public ResponseEntity<Object> createApresentantion(@RequestBody ApresentationBanca apresentation) {

        @SuppressWarnings("unchecked")
        Optional<ApresentationBanca> createdApresentationBanca = Optional
                .ofNullable((ApresentationBanca) interfaceBanca.createApresentationBanca(apresentation));

        if (createdApresentationBanca.isPresent()) {

            return ResponseEntity.status(HttpStatus.CREATED).body("Apresentação marcada com sucesso");

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Apresentação não marcada");

        }
    }

    @DeleteMapping("/apresentacao/{id}")
    public ResponseEntity<Object> deleteApresentationBanca(@PathVariable("id") String id) {

        Optional<ApresentationBanca> deletedApresentationBanca = Optional
                .ofNullable((ApresentationBanca) interfaceBanca.deleteApresentationBanca(id));

        if (deletedApresentationBanca.isPresent()) {

            return new ResponseEntity<>(deletedApresentationBanca, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @PutMapping("/apresentacao/{id}")
    public ResponseEntity<Object> updateApresentationBanca(@PathVariable("id") String id,
            @RequestBody(required = true) ApresentationBanca apresentation) {

        Optional<ApresentationBanca> updatedApresentationBanca = Optional
                .ofNullable((ApresentationBanca) interfaceBanca.updateApresentationBanca(id, apresentation));

        if (updatedApresentationBanca.isPresent()) {

            return new ResponseEntity<>(updatedApresentationBanca, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/apresentacao/{id}")
    public ResponseEntity<Object> getApresentationBancaById(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<ApresentationBanca> getApresentationBanca = Optional
                .ofNullable((ApresentationBanca) interfaceBanca.getApresentationBanca(id));

        if (getApresentationBanca.isPresent()) {

            return new ResponseEntity<>(getApresentationBanca, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/apresentacoes")
    public ResponseEntity<Object> getAllApresentationBanca() {

        Optional<List<ApresentationBanca>> getApresentationBancaList = Optional
                .ofNullable((List<ApresentationBanca>) interfaceBanca.getAllApresentationBanca());

        if (getApresentationBancaList.isPresent()) {

            return new ResponseEntity<>(getApresentationBancaList, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @SuppressWarnings("unchecked")
    @GetMapping("/agendasLivres")
    public ResponseEntity<Object> getAllAgendas() {

        List<Optional<Agenda>> agendas = (List<Optional<Agenda>>) interfaceAgenda.getAllAgenda();

        if (agendas.isEmpty() != true) {

            return new ResponseEntity<>(agendas, HttpStatus.OK);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sem datas cadatradas");

        }

    }

}
