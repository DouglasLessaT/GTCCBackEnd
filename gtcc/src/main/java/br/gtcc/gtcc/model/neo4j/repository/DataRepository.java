package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import br.gtcc.gtcc.model.neo4j.Data;

import java.time.LocalDateTime;

public interface DataRepository extends Neo4jRepository<Data, String> {

    Data findByDate(LocalDateTime date);
    
}