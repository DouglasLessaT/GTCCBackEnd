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

import br.gtcc.gtcc.model.nitriteid.Tcc;
import br.gtcc.gtcc.services.spec.TccInterface;

@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class TccController {
  
    @SuppressWarnings("rawtypes")
    @Autowired
    private TccInterface tccInterface; 

    //  @Autowired
    //  private TccService tccService;
 
    @PostMapping("/tcc")
    public ResponseEntity<Tcc> createTcc(@RequestBody Tcc tcc) {
        
        @SuppressWarnings("unchecked")
        Tcc createdTcc = (Tcc) tccInterface.createTcc(tcc);
        
        if (createdTcc != null) { 
      
            return new ResponseEntity<>(createdTcc, HttpStatus.CREATED);
      
        } else {
      
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      
        }
    }

    @PutMapping("/tcc/{id}")
    public ResponseEntity<Tcc> updateTcc(@PathVariable("id") String id, @RequestBody Tcc tcc) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        @SuppressWarnings("unchecked")
        Tcc updatedTcc = (Tcc) tccInterface.updateTCC(tcc);
      
        if (updatedTcc != null) {
      
            return new ResponseEntity<>(updatedTcc, HttpStatus.OK);
      
        } else {
      
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      
        }
    }

    @DeleteMapping("/tcc/{id}")
    public ResponseEntity<Void> deleteTcc(@PathVariable("id") String id) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        Tcc tccToDelete = new Tcc(); // Você precisa criar um objeto Tcc com o ID fornecido
        @SuppressWarnings("unchecked")
        Tcc deletedTcc = (Tcc) tccInterface.deleteTCC(tccToDelete);
    
        if (deletedTcc != null) {
    
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
        } else {
    
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    
        }
    }

    @GetMapping("/tccs")
    public ResponseEntity<List<Tcc>> getAllTccs() {
    
        @SuppressWarnings("unchecked")
        List<Tcc> tccs = tccInterface.getAllTCC();
    
        return new ResponseEntity<>(tccs, HttpStatus.OK);
    }

    @GetMapping("/tcc/{id}")
    public ResponseEntity<Tcc> getTccById(@PathVariable("id") String id) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        Tcc tcc = new Tcc(); // Você precisa criar um objeto Tcc com o ID fornecido
        @SuppressWarnings("unchecked")
        Tcc foundTcc = (Tcc) tccInterface.getTCC(tcc);

        if (foundTcc != null) {
        
            return new ResponseEntity<>(foundTcc, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        }
    }
}
