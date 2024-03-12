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

import br.gtcc.gtcc.model.nitriteid.Data;
import br.gtcc.gtcc.model.nitriteid.Tcc;
import br.gtcc.gtcc.services.spec.DataInterface;

@CrossOrigin
@RestController
@RequestMapping("/v1/data")
public class DataController {
    
    @Autowired
    private DataInterface interfaceData;
    
     @PostMapping("/create")
    public ResponseEntity<Data> createTcc(@RequestBody Data data) {
        
        Data createdData = interfaceData.createData(data);
        
        if (createdData != null) { 
            return new ResponseEntity<>(createdData, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Data> updateTcc(@PathVariable("id") String id, @RequestBody Data data) {
       
        Data updatedData = interfaceData.updateData(data);
       
        if (updatedData != null) {
       
            return new ResponseEntity<>(updatedData, HttpStatus.OK);
       
        } else {
       
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTcc(@PathVariable("id") String id, @RequestBody Data data ) {
       
        Data DataToDelete = new Data();
       
        Data deletedData = interfaceData.deleteData(DataToDelete);
       
        if (deletedData != null) {
       
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       
        } else {
       
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Data>> getAllDatas() {
        
        List<Data> datas = interfaceData.getAllData();
        
        return new ResponseEntity<>(datas, HttpStatus.OK);
    
    }

    @GetMapping("/get_tcc/{id}")
    public ResponseEntity<Data> getDataById(@PathVariable("id") String id, @RequestBody Data data ) {
    
        Data _data = new Data();
        
        Data foundData = interfaceData.getData(_data);
        
        if (foundData != null) {
        
            return new ResponseEntity<>(foundData, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        }
    }
}
