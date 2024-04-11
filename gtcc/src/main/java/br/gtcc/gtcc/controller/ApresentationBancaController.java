package br.gtcc.gtcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;


import java.util.List;// pode usar para apresentaçao
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class ApresentationBancaController {
    
 @Autowired
 private  ApresentationBancaInterface interfaceBanca;

 @PostMapping("/apresentacao")
   public ResponseEntity<Object> createApresentantion(@RequestBody ApresentationBanca apresentation) {
       
        Optional<ApresentationBanca>  createdApresentationBanca  = (Optional<ApresentationBanca>) interfaceBanca.createApresentationBanca(apresentation);
       
       if (  createdApresentationBanca.isPresent()) { 
           
           return new ResponseEntity<>(  createdApresentationBanca , HttpStatus.CREATED);
       
       } else {
       
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       
       }
   }

   @DeleteMapping("/apresentacao/{id}")
    public ResponseEntity<Object> deleteApresentationBanca(@RequestParam(required = true) ApresentationBanca apresentation){
    
        Optional<ApresentationBanca> deletedApresentationBanca = (Optional<ApresentationBanca>) interfaceBanca.deleteApresentationBanca(apresentation);  

        if (deletedApresentationBanca.isPresent()) {
        
            return new ResponseEntity<>(deletedApresentationBanca, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 

    }

    @PutMapping("/apresentacao/{id}")
    public ResponseEntity<Object> updateApresentationBanca(@RequestParam(required = true) ApresentationBanca apresentation){ 
 
       Optional<ApresentationBanca> updatedApresentationBanca =  (Optional<ApresentationBanca>) interfaceBanca.updateApresentationBanca(apresentation);

        if (updatedApresentationBanca.isPresent()) {
        
            return new ResponseEntity<>( updatedApresentationBanca, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 
    
    }

    @GetMapping("/apresentacao/{id}")
    public ResponseEntity<Object> getApresentationBancaById(@RequestParam(required = true) ApresentationBanca apresentation){ 
 
        Optional<ApresentationBanca> getApresentationBanca =  (Optional<ApresentationBanca>) interfaceBanca.getApresentationBanca(apresentation);

        if (getApresentationBanca.isPresent()) {
        
            return new ResponseEntity<>( getApresentationBanca, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 
    
    }

    
    @GetMapping("/apresentacao/{id}")
    public ResponseEntity<Object> getAllApresentationBanca(){ 
 
        List<Optional<ApresentationBanca>> getApresentationBancaList = (List<Optional<ApresentationBanca>>) interfaceBanca.getAllApresentationBanca();

        if (getApresentationBancaList.isEmpty()) {
        
            return new ResponseEntity<>( getApresentationBancaList, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 
    
    }

}
