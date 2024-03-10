/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.controller;

import br.gtcc.gtcc.model.nitriteid.Users;
import br.gtcc.gtcc.services.impl.nitritedb.UserServices;
import br.gtcc.gtcc.services.impl.nitritedb.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import br.gtcc.gtcc.services.spec.UserInterface;

/**
 *
 * @author mrbee
 * 
 * Controller da esntidade Users, criação de rotas  
 */

@CrossOrigin
@RestController
@RequestMapping("/v1/users")
public class UsersController {
    
    public UserInterface usersInterface;
    
    @Autowired
    UserServices service;
    
    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestParam(required = false) Users users){

        Users createdUsers = service.createUsers(users);
        
        if (createdUsers != null) {
        
            return new ResponseEntity<>(createdUsers, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Users> deleteUsers(@RequestParam(required = false) Users users){
       
        Users deletedUsers =  service.deleteUsers(users);
    
        if (deletedUsers != null) {
        
            return new ResponseEntity<>(deletedUsers, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 

    }
    
    @PutMapping("/update/${id}")
    public ResponseEntity<Users> updateUsers(@RequestParam(required = false) Users users){
        
        Users updatedUser =  service.updateUsers(users);
 
        if (updatedUser != null) {
        
            return new ResponseEntity<>( updatedUser, HttpStatus.OK);
        
        } else {
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        } 
    
    }
    
    @GetMapping("/list")
    public List<Users> getAllUsers(){
        
        return service.getAllUsers();
    
    }
    
    @GetMapping("/get_user")
    public Users getUser(@RequestParam(required = false) Users user){
        
        return service.getUsers(user);
        
    }
    
}
