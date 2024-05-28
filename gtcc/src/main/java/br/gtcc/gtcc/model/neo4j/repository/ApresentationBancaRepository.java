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

    
    @Query("MATCH (ap1:ApresentationBanca)-[:MEMBER_ONE_OF|:MEMBER_TWO_OF]->(user:Users), " +
           "(ap1)-[:TCC_APRESENTA_EM]->(data1:Data) " +
           "MATCH (ap2:ApresentationBanca)-[:MEMBER_ONE_OF|:MEMBER_TWO_OF]->(user), " +
           "(ap2)-[:TCC_APRESENTA_EM]->(data2:Data) " +
           "WHERE ap1.id <> ap2.id " +
           "AND data1.date = $date " +
           "AND data1.horasComeco = $horasComeco " +
           "AND data1.horasFim = $horasFim " +
           "AND (user.id = $member1Id OR user.id = $member2Id) " +
           "RETURN count(DISTINCT ap1)")
    Integer countConflictingApresentations(String date, String horasComeco, String horasFim, String member1Id, String member2Id);

    @Query("MATCH (ap:ApresentationBanca)-[:MEMBER_ONE_OF|:MEMBER_TWO_OF]->(user:Users), " +
           "(ap)-[:ON_DATE]->(data:Data) " +
           "WHERE (user.id = $member1 OR user.id = $member2) " +
           "AND data.date = $date " +
           "AND data.horasComeco = $horasComeco " +
           "AND data.horasFim = $horasFim " +
           "RETURN count(DISTINCT ap)")
    Integer countConflictingApresentationsByData(String date, String horasComeco, String horasFim, String member1, String member2);


}
