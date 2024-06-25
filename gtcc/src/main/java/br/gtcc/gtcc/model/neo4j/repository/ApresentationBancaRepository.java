package br.gtcc.gtcc.model.neo4j.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import br.gtcc.gtcc.model.neo4j.ApresentationBanca;

public interface ApresentationBancaRepository extends Neo4jRepository<ApresentationBanca, String> {
 
    @Query("MATCH (ap:ApresentationBanca)-[:MEMBER_ONE_OF|MEMBER_TWO_OF]->(user:Users), " +
           "(ap)-[:ON_DATE]->(agenda:Agenda) " +
           "WHERE (elementId(user) = $member1 OR elementId(user) = $member2) " +
           "AND datetime(agenda.date) = datetime($date) " +
           "AND time(agenda.horasComeco) = time($horasComeco) " +
           "AND time(agenda.horasFim) = time($horasFim) " +
           "RETURN count(DISTINCT ap)")
    Integer countConflictingApresentationsByData(@Param("date") LocalDateTime date, LocalTime horasComeco, LocalTime horasFim, String member1, String member2);

    @Query("MATCH(t:Tcc)-[:TCC_APRESENTA_EM]->(a:ApresentationBanca)WHERE elementId(t)= $tccId RETURN COUNT(t)")
    Integer countConflictTccs(@Param("tccId") String tccId);

    @Query("MATCH(ap:ApresentationBanca)-[:ON_DATE]->(agenda:Agenda) "+
    "WHERE elementId(agenda) = $agendaId  AND " +
    "AND datetime(agenda.date) = datetime($date) " + 
    "AND time(agenda.horasComeco) = time($horasComeco) " + 
    "AND time(agenda.horasFim) = time($horasFim)" + 
    "RETURN count(DISTINCT ap)")
    Integer countConflictApresentationWithoutMembers(@Param("agendaId") String agendaId ,@Param("date") LocalDateTime date, @Param("horasComeco")LocalTime horasComeco, @Param("horasFim") LocalTime horasFim);
    

}
