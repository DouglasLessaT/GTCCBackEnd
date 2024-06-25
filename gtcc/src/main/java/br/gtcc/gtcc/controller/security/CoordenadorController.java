package br.gtcc.gtcc.controller.security;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ClassUtils.Interfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@ValidaAcesso("ROLE_COORDENADOR")
@RestController
@RequestMapping("coordenacao/tcc/v1/coordenador")
public class CoordenadorController {
    @Autowired
    public UserInterface<Users, String> usersInterface;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id) {
        Optional<Users> foundUsers = Optional.ofNullable(usersInterface.getUsers(id));
        if (foundUsers.isPresent()) {
            return new ResponseEntity<>(foundUsers, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @GetMapping("/alunos")
    public ResponseEntity<Object> getAllAlunos() {
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getAlunos());
        if (list.isPresent()) {
            return new ResponseEntity<>(list, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
        }
    }

    @GetMapping("/professores")
    public ResponseEntity<Object> getAllProfessores() {
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getProfessores());
        if (list.isPresent()) {
            return new ResponseEntity<>(list, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
        }
    }

        @PostMapping("/professor")
    public ResponseEntity<Object> createProfessor(@RequestBody Users users) {
        try {
            Users createdUser = usersInterface.createdProfessor(users);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o usuário");
        }
    }

        @DeleteMapping("/professor/{id}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable String id){
    
        @SuppressWarnings("unchecked")
        Optional<Users> deletedUsers = Optional.ofNullable((Users) usersInterface.deleteProfessor(id));  

        if (deletedUsers.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuáio não encontrado");
        
        } 

    }
    
    @PutMapping("/professor/{id}")
    public ResponseEntity<Object> updateProfessor(@RequestBody(required = true) Users users ,@PathVariable("id") String id){
        
        @SuppressWarnings("unchecked")
        Optional<Users> updatedUser = Optional.ofNullable((Users)  usersInterface.updateProfessor(users , id));

        if (updatedUser.isPresent()) {
        
            return ResponseEntity.status(HttpStatus.OK).body("Usuario alterado com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        
        } 
    
    }
    
}
