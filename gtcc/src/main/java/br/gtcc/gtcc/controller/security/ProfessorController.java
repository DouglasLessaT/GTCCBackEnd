package br.gtcc.gtcc.controller.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@RequestMapping("coordenacao/tcc/v1/Professor")
public class ProfessorController {

    @Autowired
    public UserInterface<Users, String> usersInterface;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id) {
        Optional<Users> foundUsers = Optional.ofNullable(usersInterface.getUsers(id));
        if (foundUsers.isPresent()) {
            return new ResponseEntity<>(foundUsers.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @GetMapping("/alunos")
    public ResponseEntity<Object> getAllAlunos() {
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getAlunos());
        if (list.isPresent()) {
            return new ResponseEntity<>(list.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
        }
    }

    @GetMapping("/Professores")
    public ResponseEntity<Object> getAllProfessores() {
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getProfessores());
        if (list.isPresent()) {
            return new ResponseEntity<>(list.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
        }
    }

    @PostMapping("/aluno")
    public ResponseEntity<Object> createAluno(@RequestBody Users users) {
        try {
            Users createdUser = usersInterface.createdAluno(users);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o usuário");
        }
    }

        @DeleteMapping("/aluno/{id}")
    public ResponseEntity<Object> deleteAluno(@PathVariable String id){
    
        @SuppressWarnings("unchecked")
        Optional<Users> deletedUsers = Optional.ofNullable((Users) usersInterface.deleteAluno(id));  

        if (deletedUsers.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuáio não encontrado");
        
        } 

    }
    
    @PutMapping("/aluno/{id}")
    public ResponseEntity<Object> updateAluno(@RequestBody(required = true) Users users ,@PathVariable("id") String id){
        
        @SuppressWarnings("unchecked")
        Optional<Users> updatedUser = Optional.ofNullable((Users)  usersInterface.updateAluno(users , id));

        if (updatedUser.isPresent()) {
        
            return ResponseEntity.status(HttpStatus.OK).body("Usuario alterado com sucesso");
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        
        } 
    
    }
    
    @GetMapping("/alunofree/")
    public ResponseEntity<Object> getAlunosSemTcc(){
        
        Optional<List<Users>> getAlunosSemTcc = Optional.ofNullable(usersInterface.getAlunosSemTcc());

        if (getAlunosSemTcc.isPresent()) {
        
            return ResponseEntity.status(HttpStatus.OK).body(getAlunosSemTcc);
        
        } else {
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        
        } 
    
    }
}