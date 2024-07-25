package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import br.gtcc.gtcc.model.neo4j.Agenda;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AgendaRepository extends Neo4jRepository<Agenda, String> {

    @Query("MATCH(a:Agenda) RETURN a LIMIT 10")
    List<Agenda> getAllAgendas();

    @Query("MATCH (d:Agenda) WHERE date(d.date).day = $day AND date(d.date).month = $month AND date(d.date).year = $year AND d.horasComeco = $horasComeco AND d.horasFim = $horasFim RETURN count(d)")
    Integer countByDayMonthYearAndHours(@Param("day") int day, @Param("month") int month, @Param("year") int year, @Param("horasComeco") LocalTime horasComeco, @Param("horasFim") LocalTime horasFim);
    
    @Query("MATCH (a:Agenda) WHERE datetime(a.date) = datetime($date) AND"+ 
    "((time(a.horasComeco) = time($horasComeco) AND time(a.horasFim) = time($horasFim) ) OR"+
    "(time(a.horasComeco) > time($horasComeco) AND time(a.horasComeco) < time($horasFim)) OR "+
    "(time(a.horasFim) > time($horasComeco) AND time(a.horasFim) < time($horasFim)) OR "+
    "(time(a.horasComeco) <= time($horasComeco) AND time(a.horasFim) >= time($horasFim)) OR"+
    "(time(a.horasComeco) > time($horasComeco) AND time(a.horasFim) < time($horasFim))) RETURN COUNT(a)")
    Integer countConflitosByDateAndHours(@Param("date") LocalDateTime date, @Param("horasComeco") LocalTime horasComeco, @Param("horasFim") LocalTime horasFim);

    @Query("MATCH(a:Agenda)WHERE a.isLock = false RETURN a LIMIT 10")
    List<Agenda> listAgendaFree();

    @Query("MATCH(a:Agenda)WHERE datetime(a.date)=datetime($date) RETURN a")
    List<Agenda> listAgendaByDate(@Param("date") LocalDateTime date);

    @Query("MATCH(a:Agenda)WHERE elementId(a) = $idAgenda AND NOT EXISTS((:ApresentationBanca)-[:ON_DATE]->(a)) RETURN a")
    Agenda buscarAgendaSemApresentacao(@Param("idAgenda") String idAgenda);
}