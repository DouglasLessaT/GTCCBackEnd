package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Date;

import java.util.List;
import java.util.Optional;


public interface DateRepository extends Neo4jRepository<Date, String> {
 Optional<Date> findById(String id);
}