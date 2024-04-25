package br.gtcc.gtcc.model.neo4j.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Tcc;

public interface TccRepository extends Neo4jRepository<Tcc, String> {
 Tcc findByName(String title); // implementação futura
 Optional<Tcc> findById(String id);
}
