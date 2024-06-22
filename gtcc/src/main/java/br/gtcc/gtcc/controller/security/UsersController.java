/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.controller.security;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import br.gtcc.gtcc.services.spec.UserInterface;
import br.gtcc.gtcc.util.Console;

import java.util.Optional;

/**
 *
 * @author mrbee
 * 
 *         Controller da entidade Users, criação de rotas
 */

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_ADMIN")
@RequestMapping("coordenacao/tcc/v1")
public class UsersController {

    @Autowired
    public UserInterface<Users, String> usersInterface;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/usuario")
    public ResponseEntity<Object> createUser(@RequestBody(required = true) Users users) {  
        users.setSenha(passwordEncoder.encode(users.getSenha()));
        Optional<Users> createdUsers = Optional.ofNullable(usersInterface.createUsers(users));
        if (createdUsers.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuário criado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Object> deleteUsers(@PathVariable String id) {
        Users user = usersInterface.getUsers(id);
        if (user != null) {
            usersInterface.deleteUsers(user);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<Object> updateUsers(@RequestBody(required = true) Users users, @PathVariable String id) {
        users.setId(id); // Assegurar que o ID do Path é definido no objeto Users
        Optional<Users> updatedUser = Optional.ofNullable(usersInterface.updateUsers(users));
        if (updatedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuário alterado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Object> getAllUsers() {
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getAllUsers());
        if (list.isPresent()) {
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id) {
        Optional<Users> foundUsers = Optional.ofNullable(usersInterface.getUsers(id));
        if (foundUsers.isPresent()) {
            return new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
}