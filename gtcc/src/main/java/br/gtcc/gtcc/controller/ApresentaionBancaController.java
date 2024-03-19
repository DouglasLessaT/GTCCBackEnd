package br.gtcc.gtcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.model.nitriteid.ApresentationBanca;
import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;


import java.util.List;// pode usar para apresentaçao


@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class ApresentaionBancaController {
    
 @Autowired
    private  ApresentationBancaInterface interfaceBanca;

 @PostMapping("/apresentacao")
   public ResponseEntity<ApresentationBanca> createApresentantion(@RequestBody ApresentationBanca apresentation) {
       
        ApresentationBanca  createdApresentationBanca  = interfaceBanca.createApresentationBanca(apresentation);
       
       if (  createdApresentationBanca  != null) { 
           return new ResponseEntity<>(  createdApresentationBanca , HttpStatus.CREATED);
       } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }

   @DeleteMapping("/apresentacao/{id}")
    public ResponseEntity<ApresentationBanca> deleteApresentationBanca(@RequestParam(required = true) ApresentationBanca apresentation){
    
        ApresentationBanca deletedApresentationBanca =  interfaceBanca.deleteApresentationBanca(apresentation);  

        if (deletedApresentationBanca != null) {
        
            return new ResponseEntity<>(deletedApresentationBanca, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 

    }

    @PutMapping("/apresentacao/{id}")
    public ResponseEntity<ApresentationBanca> updateApresentationBanca(@RequestParam(required = true) ApresentationBanca apresentation){ 
 
        ApresentationBanca updatedApresentationBanca =  interfaceBanca.updateApresentationBanca(apresentation);

        if (updatedApresentationBanca != null) {
        
            return new ResponseEntity<>( updatedApresentationBanca, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 
    
    }

}
