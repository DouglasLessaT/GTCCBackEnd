package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Users;

public interface UsersRepository extends Neo4jRepository<Users, String> {
 
}