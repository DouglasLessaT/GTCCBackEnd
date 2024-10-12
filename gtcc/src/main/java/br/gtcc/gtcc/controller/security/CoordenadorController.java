package br.gtcc.gtcc.controller.security;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.services.spec.UsuarioInterface;
import br.gtcc.gtcc.util.UtilController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@ValidaAcesso("ROLE_COORDENADOR")
@RestController
@RequestMapping("coordenacao/tcc/v1/coordenador")
@RequiredArgsConstructor
public class CoordenadorController {
    
    private final UsuarioInterface<Usuario, Long> usersInterface;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUserByCoordenador(@PathVariable Long id) {

        Optional<Usuario> foundUsers = Optional.ofNullable(usersInterface.getUser(id));
        return UtilController.buildResponseFromOptional( foundUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário econtrado", "Usuario não encontrado");
        
    }

    @GetMapping("/alunos")
    public ResponseEntity<Object> getAllAlunosByCoordenador() {
        
        Optional<List<Usuario>> list = Optional.ofNullable(usersInterface.getAlunos());
        return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Usuários", "Lista Vázia");
        
    }

    @GetMapping("/professores")
    public ResponseEntity<Object> getAllProfessoresByCoordenador() {
        
        Optional<List<Usuario>> list = Optional.ofNullable(usersInterface.getProfessores());
        return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de professores", "Lista Vazia");
        
    }

    @PostMapping("/professor")
    public ResponseEntity<Object> createProfessorByCoordenador(@RequestBody Usuario users) {
    
        Optional<Usuario> createdUser = Optional.of(usersInterface.createdProfessor(users));
        return UtilController.buildResponseFromOptional( createdUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Professor criado com sucesso", "Erro ao criar professor");
    
    }

    @DeleteMapping("/professor/{id}")
    public ResponseEntity<Object> deleteProfessorByCoordenador(@PathVariable Long id){
    
        Optional<Usuario> deletedUsers = Optional.ofNullable((Usuario) usersInterface.deleteProfessor(id));      
        return UtilController.buildResponseFromOptional( deletedUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Professor deletado com sucesso", "Erro ao deletar professor");
    
    }
    
    @PutMapping("/professor/{id}")
    public ResponseEntity<Object> updateProfessorByCoordenador(@RequestBody(required = true) Usuario users , @PathVariable("id") Long id){
        
        Optional<Usuario> updatedUser = Optional.ofNullable((Usuario)  usersInterface.updateProfessor(users ,id));
        return UtilController.buildResponseFromOptional( updatedUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Professor alterado com sucesso", "Erro ao alterar profesor");
    
    }
}
