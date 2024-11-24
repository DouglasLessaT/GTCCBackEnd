package br.gtcc.gtcc.controller.security;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.services.spec.UsuarioInterface;
import br.gtcc.gtcc.util.UtilController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@Tag(name="Professor Controller", description = "Essa entidade trata de gerenciar as funções do professor ")
@RequestMapping("coordenacao/tcc/v1/professor")
public class ProfessorController {

    @Autowired
    public UsuarioInterface<Usuario, Long> usersInterface;

    @Operation(summary = "Essa rota retornar um usuario dado seu id")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {

        Optional<Usuario> foundUsers = Optional.ofNullable(usersInterface.getUser(id));
        return UtilController.buildResponseFromOptional(foundUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário encontrado", "Usuário não econtrado");
   
    }

    @Operation(summary = "Essa rota retornar todos os usuários do tipo aluno")
    @GetMapping("/alunos")
    public ResponseEntity<Object> getAllAlunos() {
   
        Optional<List<Usuario>> list = Optional.ofNullable(usersInterface.getAlunos());
        return UtilController.buildResponseFromOptional(list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de alunos", "Lista vazia");
   
    }

    @Operation(summary = "Essa rota retornar todos os usuários do tipo professor")
    @GetMapping("/professores")
    public ResponseEntity<Object> getAllProfessores() {
    
        Optional<List<Usuario>> list = Optional.ofNullable(usersInterface.getProfessores());
        return UtilController.buildResponseFromOptional(list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de professores", "Lista vazia");
    
    }

    @Operation(summary = "Essa rota retornar um aluno que foi registrado no banco dado um objeto to tipo aluno")
    @PostMapping("/aluno")
    public ResponseEntity<Object> createAluno(@RequestBody Usuario users) {

        Optional<Usuario> createdUser = Optional.of(usersInterface.createdAluno(users));
        return UtilController.buildResponseFromOptional( createdUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno criado com sucesso", "Erro ao criar aluno");
    
    }

    @Operation(summary = "Essa rota retornar um aluno que foi registrado no banco dado um objeto to tipo aluno")
    @DeleteMapping("/aluno/{id}")
    public ResponseEntity<Object> deleteAluno(@PathVariable Long id){
    
        Optional<Usuario> deletedUsers = Optional.ofNullable((Usuario) usersInterface.deleteAluno(id));  
        return UtilController.buildResponseFromOptional( deletedUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno deletado com sucesso", "Erro ao deletar aluno");

    }

    @Operation(summary = "Essa rota retornar um aluno que foi alterado no banco dado um objeto to tipo aluno e o id do objeto que se deseja atualizar")
    @PutMapping("/aluno/{id}")
    public ResponseEntity<Object> updateAluno(@RequestBody(required = true) Usuario users ,@PathVariable("id") Long id){
        
        Optional<Usuario> updatedUser = Optional.ofNullable((Usuario)  usersInterface.updateAluno(users , id));
        return UtilController.buildResponseFromOptional( updatedUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Aluno alterado com sucesso", "Erro ao alterar aluno");                              
    
    }

    @Operation(summary = "Essa rota retornar uma lista de alunos que não possuem um tcc relacionado a ele")
    @GetMapping("/alunofree/")
    public ResponseEntity<Object> getAlunosSemTcc(){
        
        Optional<List<Usuario>> listAlunosSemTcc = Optional.ofNullable(usersInterface.getAlunosSemTcc());
        return UtilController.buildResponseFromOptional( listAlunosSemTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Alunos sem tcc", "Lista Vazia");
    
    }
}