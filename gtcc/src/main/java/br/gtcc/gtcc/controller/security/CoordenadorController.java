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
import br.gtcc.gtcc.util.UtilController;

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
    public ResponseEntity<Object> getUserByCoordenador(@PathVariable String id) {

        Optional<Users> foundUsers = Optional.ofNullable(usersInterface.getUser(id));
        return UtilController.buildResponseFromOptional( foundUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário econtrado", "Usuario não encontrado");
        
    }

    @GetMapping("/alunos")
    public ResponseEntity<Object> getAllAlunosByCoordenador() {
        
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getAlunos());
        return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Usuários", "Lista Vázia");
        
    }

    @GetMapping("/professores")
    public ResponseEntity<Object> getAllProfessoresByCoordenador() {
        
        Optional<List<Users>> list = Optional.ofNullable(usersInterface.getProfessores());
        return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de professores", "Lista Vazia");
        
    }

    @PostMapping("/professor")
    public ResponseEntity<Object> createProfessorByCoordenador(@RequestBody Users users) {
    
        Optional<Users> createdUser = Optional.of(usersInterface.createdProfessor(users));
        return UtilController.buildResponseFromOptional( createdUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Professor criado com sucesso", "Erro ao criar professor");
    
    }

    @DeleteMapping("/professor/{id}")
    public ResponseEntity<Object> deleteProfessorByCoordenador(@PathVariable String id){
    
        Optional<Users> deletedUsers = Optional.ofNullable((Users) usersInterface.deleteProfessor(id));      
        return UtilController.buildResponseFromOptional( deletedUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Professor deletado com sucesso", "Erro ao deletar professor");
    
    }
    
    @PutMapping("/professor/{id}")
    public ResponseEntity<Object> updateProfessorByCoordenador(@RequestBody(required = true) Users users ,@PathVariable("id") String id){
        
        Optional<Users> updatedUser = Optional.ofNullable((Users)  usersInterface.updateProfessor(users ,id));
        return UtilController.buildResponseFromOptional( updatedUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Professor alterado com sucesso", "Erro ao alterar profesor");
    
    }
}
