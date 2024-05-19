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
import br.gtcc.gtcc.services.spec.DataInterface;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class DataController {
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private DataInterface interfaceData;
    
     @PostMapping("/data")
    public ResponseEntity<Object> createData(@RequestBody Data data) {
        
        @SuppressWarnings("unchecked")
        Optional<Data> createdData = Optional.ofNullable((Data) interfaceData.createData(data));
        
        if (createdData.isPresent()) {
            
            return ResponseEntity.status(HttpStatus.CREATED).body("Data criada com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar Data");
        
        }
    }

    @PutMapping("/data/{id}")
    public ResponseEntity<Object> updateData(@PathVariable("id") String id, @RequestBody Data data) {
       
        @SuppressWarnings("unchecked")
        Optional<Data> updatedData = Optional.ofNullable((Data) interfaceData.updateData(id ,data));
       
        if (updatedData.isPresent()) {
       
            return ResponseEntity.status(HttpStatus.OK).body("Data atualizada com sucesso");
       
        } else {
       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar Data");
       
        }
    }

    @SuppressWarnings("unchecked")
    @DeleteMapping("/data/{id}")
    public ResponseEntity<Object> deleteData(@PathVariable("id") String id ) {
       
        Optional<Data> deletedData = Optional.ofNullable((Data) interfaceData.deleteData(id));
        
        if (deletedData.isPresent()) {
       
            return ResponseEntity.status(HttpStatus.OK).body("Data deletada com sucesso");
       
        } else {
       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar Data");
       
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/datas")
    public ResponseEntity<Object> getAllDatas() {
        
        List<Optional<Data>> datas = (List<Optional<Data>>) interfaceData.getAllData();
        
        if(datas.isEmpty() != true){

            return new ResponseEntity<>(datas, HttpStatus.OK);
            
        }else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sem datas cadatradas");
            
        }
            
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/data/{id}")
    public ResponseEntity<Object> getDataById(@PathVariable("id") String id) {
    
        Optional<Data> foundData = Optional.ofNullable((Data) interfaceData.getData(id));
        
        if (foundData.isPresent()) {
        
            return new ResponseEntity<>(foundData, HttpStatus.OK);
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data não encontrada");
            
        }
    }
}
