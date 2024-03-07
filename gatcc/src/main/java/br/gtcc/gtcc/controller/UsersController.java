/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.controller;

import br.gtcc.gtcc.model.Users;
import br.gtcc.gtcc.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import br.gtcc.gtcc.services.UserInterface;

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
    public Users createUser(@RequestParam(required = false) Users users){

        //return service.createUsers(users);
        return usersInterface.createUsers(users);

    }

    @DeleteMapping("/delete")
    public Users deleteUsers(@RequestParam(required = false) Users users){
       
        return service.deleteUsers(users);
    
    }
    
    @PutMapping("/update")
    public Users updateUsers(@RequestParam(required = false) Users users){
        
        return service.updateUsers(users);
    
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
