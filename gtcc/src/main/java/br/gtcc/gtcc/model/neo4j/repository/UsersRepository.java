package br.gtcc.gtcc.model.neo4j.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Users;

public interface UsersRepository extends Neo4jRepository<Users, String> {
 Users findByName(String name);
 Optional<Users> findById(String id);
 Users findByEmail(String email);
}