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

import br.gtcc.gtcc.model.neo4j.Data;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.services.spec.DataInterface;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class DataController {
    
    @Autowired
    private DataInterface interfaceData;
    
     @PostMapping("/data")
    public ResponseEntity<Object> createData(@RequestBody Data data) {
        
        Optional<Data> createdData = (Optional<Data>) interfaceData.createData(data);
        
        if (createdData.isPresent()) {
            
            return new ResponseEntity<>(createdData, HttpStatus.CREATED);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        }
    }

    @PutMapping("/data/{id}")
    public ResponseEntity<Object> updateData(@PathVariable("id") String id, @RequestBody Data data) {
       
        Optional<Data> updatedData = (Optional<Data>) interfaceData.updateData(data);
       
        if (updatedData.isPresent()) {
       
            return new ResponseEntity<>(updatedData, HttpStatus.OK);
       
        } else {
       
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       
        }
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<Object> deleteData(@PathVariable("id") String id, @RequestBody Data data ) {
       
        Data DataToDelete = new Data();
       
        Optional<Data> deletedData = (Optional<Data>) interfaceData.deleteData(DataToDelete);
       
        if (deletedData.isPresent()) {
       
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       
        } else {
       
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       
        }
    }

    @GetMapping("/data")
    public ResponseEntity<Object> getAllDatas() {
        
        List<Optional<Data>> datas = (List<Optional<Data>>) interfaceData.getAllData();
        
        if(datas.isEmpty() != true){

            return new ResponseEntity<>(datas, HttpStatus.OK);
            
        }else{

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }
            
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Object> getDataById(@PathVariable("id") String id, @RequestBody Data data ) {
    
        Data _data = new Data();
        
        Optional<Data> foundData = (Optional<Data>) interfaceData.getData(_data);
        
        if (foundData.isPresent()) {
        
            return new ResponseEntity<>(foundData, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        }
    }
}
