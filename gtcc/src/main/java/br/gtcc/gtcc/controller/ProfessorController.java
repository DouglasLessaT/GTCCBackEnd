package br.gtcc.gtcc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
            return new ResponseEntity<>(foundUsers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @GetMapping("/alunos")
    public ResponseEntity<Object> getAllAlunos() {
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getAlunos());
        if (list.isPresent()) {
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
        }
    }

    @GetMapping("/Professores")
    public ResponseEntity<Object> getAllProfessores() {
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getProfessores());
        if (list.isPresent()) {
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuários não encontrados");
        }
    }

}