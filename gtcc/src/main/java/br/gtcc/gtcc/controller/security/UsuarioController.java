package br.gtcc.gtcc.controller.security;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import br.gtcc.gtcc.services.spec.UsuarioInterface;
import br.gtcc.gtcc.util.Console;
import br.gtcc.gtcc.util.UtilController;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_ADMIN")
@Tag(name="Usuario Controller",description = "Essa entidade so pode ser acessada pelo admin e realiza a crição de varios outros componentes como aluno, " +
        "professor , e coordenador")
@RequestMapping("coordenacao/tcc/v1")
public class UsuarioController {
    
    @SuppressWarnings("rawtypes")
    @Autowired
    public UsuarioInterface usersInterface;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Operation(summary ="Dado um objeto usuario ele criar um resgitro de um usuario sem realizar nenhum filtro")
    @PostMapping("/usuario")
    public ResponseEntity<Object> createUser(@RequestBody(required = true) Usuario users){
        
        @SuppressWarnings("unchecked")
        Optional<Usuario> createdUsers = Optional.ofNullable((Usuario) usersInterface.createUsers(users));
        return UtilController.buildResponseFromOptional( createdUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário criado com sucesso", "Erro ao criar usuarios");

    }

    @Operation(summary ="Dado um id do o usuario ele recupera ele do banco")
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Object> deleteUsers(@PathVariable String id){
    
        @SuppressWarnings("unchecked")
        Optional<Usuario> deletedUsers = Optional.ofNullable((Usuario) usersInterface.deleteUsers(id));  
        return UtilController.buildResponseFromOptional( deletedUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário deletado com sucesso", "Erro ao deletar usuarios");

    }

    @Operation(summary ="Dado um objeto e o id ele busca o usuario referente ao id passado e ataliza com as informações com o objeto passado")
    @PutMapping("/usuario/{id}")
    public ResponseEntity<Object> updateUsers(@RequestBody(required = true) Usuario users ,@PathVariable("id") String id){
        
        @SuppressWarnings("unchecked")
        Optional<Usuario> updatedUser = Optional.ofNullable((Usuario)  usersInterface.updateUsers(users , id));
        return UtilController.buildResponseFromOptional( updatedUser, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário alterado com sucesso", "Erro ao alterado usuarios");

    }

    @Operation(summary ="Essa rota busca todos os registro do banco dessa entidade")
    @GetMapping("/usuarios")
    public ResponseEntity<Object> getAllUsers(){
        
       @SuppressWarnings("unchecked")
       Optional<List<Usuario>> list = Optional.ofNullable((List<Usuario>) usersInterface.getAllUsers());
       return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de usuários", "Lista Vazia");
       
    }

    @Operation(summary ="Essa rota busca um usário pelo id")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id){
        
        @SuppressWarnings("unchecked")
        Optional<Usuario> foundUsers = Optional.ofNullable( (Usuario) usersInterface.getUser(id));
        return UtilController.buildResponseFromOptional( foundUsers, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Usuário encontrado", "Usuário não econtrado");        

    }
    
}
