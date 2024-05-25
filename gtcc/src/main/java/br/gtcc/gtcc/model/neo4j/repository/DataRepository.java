package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import br.gtcc.gtcc.model.neo4j.Data;

import java.time.LocalDateTime;

public interface DataRepository extends Neo4jRepository<Data, String> {

    Data findByDate(LocalDateTime date);
 
    @Query("MATCH (d:Data) WHERE date(d.date).day = $day AND date(d.date).month = $month AND date(d.date).year = $year RETURN count(d)")
    Integer countByDayMonthYear(@Param("day") int day, @Param("month") int month, @Param("year") int year);
    
}