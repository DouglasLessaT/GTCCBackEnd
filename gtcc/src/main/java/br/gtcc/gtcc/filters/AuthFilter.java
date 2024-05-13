package br.gtcc.gtcc.filters;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter
@Component
public class AuthFilter extends OncePerRequestFilter  {

@Autowired
UsersRepository usersRepository;

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
   throws ServletException, IOException {

  String context = request.getServletContext().getContextPath();

  String url = request instanceof HttpServletRequest
    ? ((HttpServletRequest) request).getRequestURL().toString()
    : "N/A";

  try {
   URL _url = new URI(url).toURL();
   url = _url.getFile();
   url = url.replace(context, "");
  } catch (URISyntaxException e) {
   e.printStackTrace();
  }

  System.out.println("processando a url: " + url);
  
   String email = request.getHeader("email");
        logger.info("Successfully authenticated user  " +
          email);
  Users u = usersRepository.findByEmail(email);
  Set<UserType> auts = u.getUserType();
  if(auts.contains(UserType.ADMIN)){
        filterChain.doFilter(request, response);
  }else{
   throw new ServletException("Sem autorização");
  }

 }
 
}
