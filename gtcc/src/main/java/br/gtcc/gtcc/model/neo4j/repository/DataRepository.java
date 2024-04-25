package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Data;

import java.util.List;
import java.util.Optional;


public interface DataRepository extends Neo4jRepository<Data, String> {
 Data findByName(String name);
 Optional<Data> findById(String id);
}