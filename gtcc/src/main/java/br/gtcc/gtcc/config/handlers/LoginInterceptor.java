package br.gtcc.gtcc.config.handlers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import br.gtcc.gtcc.services.impl.mysql.UsuarioServices;
import br.gtcc.gtcc.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

  private UsuarioServices userService;
  private UsuarioRepository usersRepository;
  private JWTUtil jwtUtil;
  private Optional<Usuario> user;

  public LoginInterceptor(UsuarioServices userService, UsuarioRepository usersRepository, JWTUtil jwtUtil) {
    this.userService = userService;
    this.usersRepository = usersRepository;
    this.jwtUtil = jwtUtil;
  }

  public boolean validaLogin(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    try {
      String jwttoken = jwtUtil.recuperaTokenRequisicao(request);
      String login = jwtUtil.getUsuarioNoToken(jwttoken);

      if (login != null && jwtUtil.validaToken(jwttoken, login)) {
        user = Optional.of(usersRepository.findByLogin(login)) ;
        if (user.isPresent()) {
          List<GrantedAuthority> listaPermissoes = new ArrayList<>();
          user.get().getPermissoes().forEach(p -> {
            listaPermissoes.add(new SimpleGrantedAuthority(p));
          });

          var token = new UsernamePasswordAuthenticationToken(user.get().getLogin(), null, listaPermissoes);
          SecurityContextHolder.getContext().setAuthentication(token);

          return validaAutorizacao(request, response, handler);
        } else {
          request.setAttribute("jakarta.servlet.error.status_code", 401);
          throw new RuntimeException("Usuário não encontrado");
        }

      } else {
        request.setAttribute("jakarta.servlet.error.status_code", 401);
        throw new RuntimeException("Token Inválido");
      }
    } catch (Exception ex) {
      throw ex;
    }
  }

  public boolean validaAutorizacao(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HandlerMethod hm = (HandlerMethod) handler;
    Method method = hm.getMethod();
    boolean autorizadoMetodo = false, autorizadoClasse = false;

    // caso queira alterar a forma de validação usando o endpoint
    String path = request.getRequestURI().substring(request.getContextPath().length());

    if (user == null) {
      request.setAttribute(null, method);
      request.setAttribute("jakarta.servlet.error.status_code", 401);
      throw new RuntimeException("Não logado");
    }

    // se é um restcontroller
    if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {

      if (method.getDeclaringClass().isAnnotationPresent(ValidaAcesso.class)) {

        String[] acessos = method.getDeclaringClass().getAnnotation(ValidaAcesso.class).value();

        for (String verificacao : acessos) {
          if (user.get().getPermissoes().contains(verificacao)) {
            autorizadoClasse = true;
            break;
          }
        }
      } else {
        autorizadoClasse = true;
      }

      if (method.isAnnotationPresent(ValidaAcesso.class)) {

        String[] acessos = method.getAnnotation(ValidaAcesso.class).value();

        for (String verificacao : acessos) {
          if (user.get().getPermissoes().contains(verificacao)) {
            autorizadoMetodo = true;
            break;
          }
        }
      } else {
        autorizadoMetodo = true;
      }

      if (!autorizadoClasse || (autorizadoClasse && !autorizadoMetodo)) {
        request.setAttribute("jakarta.servlet.error.status_code", 403);
        throw new RuntimeException("Recurso não autorizado");
      }
    }
    return true;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    boolean config = false;
    if (!config && (handler instanceof HandlerMethod)) {
      return this.validaLogin(request, response, handler);
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler, ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
  }
}
