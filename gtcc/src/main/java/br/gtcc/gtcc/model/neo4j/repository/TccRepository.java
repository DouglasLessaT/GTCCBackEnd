package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Tcc;

public interface TccRepository extends Neo4jRepository<Tcc, String> {
 
}
