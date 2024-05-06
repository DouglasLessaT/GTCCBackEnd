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

import br.gtcc.gtcc.services.spec.UserInterface;

import java.util.List;
import br.gtcc.gtcc.services.spec.UserInterface;
import java.util.Optional;

/**
 *
 * @author mrbee
 * 
 *         Controller da esntidade Users, criação de rotas
 */

@CrossOrigin
@RestController
@RequestMapping("coordenacao/tcc/v1")
public class UsersController {

    @SuppressWarnings("rawtypes")
    @Autowired
    public UserInterface usersInterface;


    // @Autowired
    // UserServices service;

    @PostMapping("/usuario")
    public ResponseEntity<Object> createUser(@RequestBody(required = true) Users users) {

        // Users createdUser = userService.createUsers(users);
        // return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        Users createdUsers = (Users) usersInterface.createUsers(users);

        if (createdUsers != null) {

            return new ResponseEntity<>(createdUsers, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Object> deleteUsers(@PathVariable String id) {
        // userService.deleteUsers(id);
        // return ResponseEntity.noContent().build();

        // Users deletedUsers = service.deleteUsers(users);

        @SuppressWarnings("unchecked")
        Optional<Users> deletedUsers = (Optional<Users>) usersInterface.deleteUsers(id);

        if (deletedUsers.isPresent()) {

            return new ResponseEntity<>(deletedUsers, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    /**
     * @param id
     * @param users
     * @return
     */
    @PutMapping("/usuario/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable String id, @RequestBody Users users) {

        users.setId(id);
        // Users updatedUser = userService.updateUsers(users);
        // return ResponseEntity.ok(updatedUser);

        @SuppressWarnings("unchecked")
        Optional<Users> updatedUser = Optional.ofNullable((Users) usersInterface.updateUsers(users));

        if (updatedUser.isPresent()) {

            return updatedUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        } else {

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/usuarios")
    public ResponseEntity<Object> getAllUsers() {

        // Users user = userService.getUsers(id);
        // return ResponseEntity.ok(user);

        // List<Users> list = service.getAllUsers();
        List<Optional<Users>> list = (List<Optional<Users>>) usersInterface.getAllUsers();

        if (list.isEmpty() != true) {

            return new ResponseEntity<>(list, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id) {

        // Users user = userService.getUsers(id);
        // return ResponseEntity.ok(user);
        // Users foundUsers = service.getUsers(user);

        @SuppressWarnings("unchecked")
        Optional<Users> foundUsers = (Optional<Users>) usersInterface.getUsers(id);

        if (foundUsers.isPresent()) {
            return new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}