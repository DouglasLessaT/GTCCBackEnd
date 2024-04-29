/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.controller;

import br.gtcc.gtcc.model.neo4j.Users;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import br.gtcc.gtcc.services.spec.UserInterface;

import java.util.Optional;

/**
 *
 * @author mrbee
 * 
 * Controller da entidade Users, criação de rotas  
 */

@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class UsersController {
    
    @SuppressWarnings("rawtypes")
    @Autowired
    public UserInterface usersInterface;
        
    @PostMapping("/usuario")
    public ResponseEntity<Object> createUser(@RequestBody(required = true) Users users){

        @SuppressWarnings("unchecked")
        Optional<Users> createdUsers = Optional.ofNullable((Users) usersInterface.createUsers(users));
        
        if (createdUsers.isPresent()) {
        
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado"); 
        
        } 
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Object> deleteUsers(@PathVariable long id){
       
        //Users deletedUsers =  service.deleteUsers(users);
    
        @SuppressWarnings("unchecked")
        Optional<Users> deletedUsers = Optional.ofNullable((Users) usersInterface.deleteUsers(id));  

        if (deletedUsers.isPresent()) {

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário deletado com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuáio não encontrado");
        
        } 

    }
    
    @PutMapping("/usuario/{id}")
    public ResponseEntity<Object> updateUsers(@RequestBody(required = true) Users users, @PathVariable("id") String id){
        
        @SuppressWarnings("unchecked")
        Optional<Users> updatedUser = Optional.ofNullable((Users)  usersInterface.updateUsers(users , id));

        if (updatedUser.isPresent()) {
        
            return ResponseEntity.status(HttpStatus.OK).body("Usuario alterado com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        
        } 
    
    }
    
    @GetMapping("/usuarios")
    public ResponseEntity<Object> getAllUsers(){
        
       @SuppressWarnings("unchecked")
       Optional<List<Users>> list = Optional.ofNullable((List<Users>) usersInterface.getAllUsers());
       
       if(list.isPresent()){
    
            return new ResponseEntity<>( list , HttpStatus.FOUND);
           
       }else {
       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
       }
       
    }
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id){
        
        @SuppressWarnings("unchecked")
        Optional<Users> foundUsers = Optional.ofNullable( (Users) usersInterface.getUsers(id));
        

        if (foundUsers.isPresent()) {
        
            return new ResponseEntity<>(foundUsers, HttpStatus.FOUND);
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        
        }
 

    }
    
}
