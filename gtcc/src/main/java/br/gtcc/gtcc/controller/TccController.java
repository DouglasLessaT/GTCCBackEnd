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
import java.util.Optional;

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
    public ResponseEntity<Object> createTcc(@RequestBody Tcc tcc) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = (Optional<Tcc>) tccInterface.createTcc(tcc);
        
        if (createdTcc.isPresent()) { 
      
            return new ResponseEntity<>(createdTcc, HttpStatus.CREATED);
      
        } else {
      
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      
        }
    }

    @PutMapping("/tcc/{id}")
    public ResponseEntity<Object> updateTcc(@PathVariable("id") String id, @RequestBody Tcc tcc) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        @SuppressWarnings("unchecked")
        Optional<Tcc> updatedTcc = (Optional<Tcc>) tccInterface.updateTCC(tcc);
      
        if (updatedTcc.isPresent()) {
      
            return new ResponseEntity<>(updatedTcc, HttpStatus.OK);
      
        } else {
      
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      
        }
    }

    @DeleteMapping("/tcc/{id}")
    public ResponseEntity<Object> deleteTcc(@PathVariable("id") String id) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        Tcc tccToDelete = new Tcc(); // Você precisa criar um objeto Tcc com o ID fornecido
        @SuppressWarnings("unchecked")
        Optional<Tcc> deletedTcc = (Optional<Tcc>) tccInterface.deleteTCC(tccToDelete);
    
        if (deletedTcc.isPresent()) {
    
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
        } else {
    
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    
        }
    }

    @GetMapping("/tccs")
    public ResponseEntity<Object> getAllTccs() {
    
        @SuppressWarnings("unchecked")
        List<Optional<Tcc>> tccs = (List<Optional<Tcc>>) tccInterface.getAllTCC();
    
        if(tccs.isEmpty() != true){
      
            return new ResponseEntity<>(tccs, HttpStatus.OK);
            
        }else{
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }
      
    }

    @GetMapping("/tcc/{id}")
    public ResponseEntity<Object> getTccById(@PathVariable("id") String id) {
        // Assume que o ID é passado como string, você pode alterar conforme necessário
        Tcc tcc = new Tcc(); // Você precisa criar um objeto Tcc com o ID fornecido
        @SuppressWarnings("unchecked")
        Optional<Tcc> foundTcc = (Optional<Tcc>) tccInterface.getTCC(tcc);

        if (foundTcc.isPresent()) {
        
            return new ResponseEntity<>(foundTcc, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        }
    }
}
