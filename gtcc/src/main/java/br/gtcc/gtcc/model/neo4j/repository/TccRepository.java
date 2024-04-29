package br.gtcc.gtcc.model.neo4j.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Tcc;
import java.util.List;


public interface TccRepository extends Neo4jRepository<Tcc, String> {

}
