package br.gtcc.gtcc.model.neo4j.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import br.gtcc.gtcc.model.neo4j.Users;

public interface UsersRepository extends Neo4jRepository<Users, String> {
  
 Optional<Users> findById(String id);

 @Query("MATCH (u:Users {login: $login}) RETURN u")
 Optional<Users> findByLogin(String login);

 List<Users> findAllByLoginOrderByLogin(String login);

 List<Users> findAllByOrderByLoginAsc();

 @Query("MATCH (u:Users {email: $0}) RETURN u")
 Users findByEmail(String email);

 @Query("MATCH (u:Users) WHERE 'ALUNO' IN u.userType RETURN u")
 List<Users> findAlunos();

 @Query("MATCH (u:Users) WHERE 'PROFESSOR' IN u.userType RETURN u ")
 List<Users> findProfessores();
}