package br.gtcc.gtcc.model.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import br.gtcc.gtcc.model.neo4j.Agenda;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface AgendaRepository extends Neo4jRepository<Agenda, String> {

    Agenda findByDate(LocalDateTime date);
 
    @Query("MATCH (d:Agenda) WHERE date(d.date).day = $day AND date(d.date).month = $month AND date(d.date).year = $year AND d.horasComeco = $horasComeco AND d.horasFim = $horasFim RETURN count(d)")
    Integer countByDayMonthYearAndHours(@Param("day") int day, @Param("month") int month, @Param("year") int year, @Param("horasComeco") LocalTime horasComeco, @Param("horasFim") LocalTime horasFim);
    
    @Query("match(a:Agendata)"+
    "where a.data= $date"+
    "and ((time(a.hi)>time($horasComeco) and time(a.hi)<time(horasFim))"+
    "or (time(a.hf)>time($horasComeco) and time(a.hf)<time(horasFim))"+
    "or (time(a.hi)<time($horasComeco) and time(a.hf)>time(horasFim))"+
    "or (time(a.hi)>time($horasComeco) and time(a.hf)<time(horasFim))) return a)")
     Integer countByDateAndHours(@Param("date") LocalDateTime date, @Param("horasComeco") LocalTime horasComeco, @Param("horasFim") LocalTime horasFim);
}