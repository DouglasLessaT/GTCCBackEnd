package br.gtcc.gtcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.model.Tcc;
import br.gtcc.gtcc.services.impl.TccService;


@RestController
@RequestMapping("/api/tccs")
public class TccController {
 @Autowired
 private TccService tccService;
 @PostMapping
    public ResponseEntity<Tcc> createTcc(@RequestBody Tcc tcc) {
        Tcc createdTcc = tccService.createTcc(tcc);
        if (createdTcc != null) {
            return new ResponseEntity<>(createdTcc, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tcc> updateTcc(@PathVariable("id") String id, @RequestBody Tcc tcc) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        Tcc updatedTcc = tccService.updateTCC(tcc);
        if (updatedTcc != null) {
            return new ResponseEntity<>(updatedTcc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTcc(@PathVariable("id") String id) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        Tcc tccToDelete = new Tcc(); // Você precisa criar um objeto Tcc com o ID fornecido
        Tcc deletedTcc = tccService.deleteTCC(tccToDelete);
        if (deletedTcc != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tcc>> getAllTccs() {
        List<Tcc> tccs = tccService.getAllTCC();
        return new ResponseEntity<>(tccs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tcc> getTccById(@PathVariable("id") String id) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        Tcc tcc = new Tcc(); // Você precisa criar um objeto Tcc com o ID fornecido
        Tcc foundTcc = tccService.getTCC(tcc);
        if (foundTcc != null) {
            return new ResponseEntity<>(foundTcc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
