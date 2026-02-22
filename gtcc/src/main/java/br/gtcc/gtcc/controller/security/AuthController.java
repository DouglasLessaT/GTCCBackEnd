package br.gtcc.gtcc.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gtcc.gtcc.controller.DefaultController;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import br.gtcc.gtcc.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin("*")
@RestController
@Tag(name="Auth Controller", description = "Essa rota trata da autenticação de acesso ao sistema.")
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController extends DefaultController {

 private final JWTUtil jwtUtil;

 private final UsuarioRepository userRepository;

 private final PasswordEncoder passwordEncoder;

 @Operation(summary = "Essa rota trata da autenticação para possui acessa a manipulação as rotas dado um Login e senha ",
 description = "Dado um login e senha é retornado o token para ser usado a cada requisição a API")
 @CrossOrigin(origins = "*", allowedHeaders = "*")
 @PostMapping("/login")
 public ResponseEntity<String> login(@RequestParam String login, @RequestParam String senha, HttpServletRequest request) throws JsonProcessingException {
    Usuario user = userRepository.findByLogin(login);
  if (user == null) {
   request.setAttribute("jakarta.servlet.error.status_code", 400);
   throw new RuntimeException("Usuário não encontrado");
  }

  if (passwordEncoder.matches(senha, user.getSenha())) {
   List<GrantedAuthority> listaPermissoes = new ArrayList<>();
   user.getPermissoes().forEach(p -> {
    listaPermissoes.add(new SimpleGrantedAuthority(p));
   });

   Map<String, Object> claims = new HashMap<>();
   
   claims.put("LOGIN", login);
   
   claims.put("PERMISSOES", listaPermissoes);
   String jwttoken = jwtUtil.geraTokenUsuario(login, claims);

   log.info("JWT gerado: {}", jwttoken);

   Map<String, Object> retorno = new HashMap<>();
   retorno.put("login", login);
   retorno.put("permissoes", listaPermissoes);
   retorno.put("token", jwttoken);

   retorno.put("elementId", user.getIdUsuario());
 

   ObjectMapper objectMapper = new ObjectMapper();
   String retornoDados = objectMapper.writeValueAsString(retorno);

   return ResponseEntity.ok().body(retornoDados);

  } else {
   request.setAttribute("jakarta.servlet.error.status_code", 400);
   throw new RuntimeException("Senha incorreta");
  }

 }

}
