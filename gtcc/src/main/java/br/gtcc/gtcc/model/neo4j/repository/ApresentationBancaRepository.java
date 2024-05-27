package br.gtcc.gtcc.model.neo4j.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import br.gtcc.gtcc.model.neo4j.ApresentationBanca;

public interface ApresentationBancaRepository extends Neo4jRepository<ApresentationBanca, String> {
 

    @Query("MATCH (a:ApresentationBanca)-[:MEMBER_OF]->(u:Users) WHERE elementId(u) IN [$member1Id, $member2Id] AND date(a.date) = date($date) AND a.horasComeco = $horasComeco AND a.horasFim = $horasFim RETURN count(a)")
    Integer countByMembersDateAndTime(@Param("member1Id") String member1Id, @Param("member2Id") String member2Id, @Param("date") LocalDateTime date, @Param("horasComeco") LocalTime horasComeco, @Param("horasFim") LocalTime horasFim);

}
