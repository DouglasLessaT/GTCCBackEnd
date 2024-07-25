package br.gtcc.gtcc.model.neo4j.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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

    /*MATCH (a:Agenda) WHERE datetime(a.date) = datetime("2024-06-29T00:00:00") AND
    ((time(a.horasComeco) = time("22:50:00") AND time(a.horasFim) = time("23:46:00") ) OR
    (time(a.horasComeco) > time("22:50:00") AND time(a.horasComeco) < time("23:46:00")) OR
    (time(a.horasFim) > time("22:50:00") AND time(a.horasFim) < time("23:46:00")) OR 
    (time(a.horasComeco) <= time("22:50:00") AND time(a.horasFim) >= time("23:46:00")) OR
    (time(a.horasComeco) > time("22:50:00") AND time(a.horasFim) < time("23:46:00"))) RETURN count(a)  */
    @Query("MATCH(ap:ApresentationBanca)-[:ON_DATE]->(agenda:Agenda) "+
    "WHERE elementId(agenda) = $agendaId  AND " +
    "AND datetime(agenda.date) = datetime($date) " + 
    "AND time(agenda.horasComeco) = time($horasComeco) " + 
    "AND time(agenda.horasFim) = time($horasFim)" + 
    "RETURN count(DISTINCT ap)")
    Integer countConflictApresentationWithoutMembers(@Param("agendaId") String agendaId ,@Param("date") LocalDateTime date, @Param("horasComeco")LocalTime horasComeco, @Param("horasFim") LocalTime horasFim);
    
    @Query("MATCH (t:Tcc)-[:TCC_APRESENTA_EM]->(a:ApresentationBanca), " +
    "(o:Users)-[:ORIENTA]->(t) " +
    "WHERE elementId(a) = $elementId " +
    "RETURN t.title AS tituloTcc")
    String findTccTitleByApresentationId(String elementId);

    @Query("MATCH (t:Tcc)-[:TCC_APRESENTA_EM]->(a:ApresentationBanca), " +
    "(o:Users)-[:ORIENTA]->(t) " +
    "WHERE elementId(a) = $elementId " +
    "RETURN o.name AS nomeOrientador")
    String findTccNomeOrientadorByApresentationId(String elementId);

    @Query("MATCH(a:Apresentacao)WHERE NOT EXISTS((a)-[:ON_DATE]->(:Agenda)) AND elementId(a)=$idApresentacao RETURN a")
    ApresentationBanca buscarApresentacaoSemAgenda(@Param("idApresentacao") String idApresentacao);

}

