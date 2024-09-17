package br.gtcc.gtcc.model.mysql.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Apresentacao;
@Primary
@Repository
public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {


    @Query("SELECT apr FROM Apresentacao apr WHERE apr.localizacao IS NULL")
    public List<Apresentacao> buscarApresentacaoSemLocalizacao();

    @Query("SELECT apr FROM Apresentacao as apr WHERE apr.tcc IS NULL")
    public List<Apresentacao> buscarApresentacaoSemTcc();
    
    @Query("SELECT apr FROM Apresentacao as apr WHERE apr.ativo = 0")
    public List<Apresentacao> buscarApresentacoesInativas();

    @Query(value = "SELECT COUNT(*) FROM tb_presentacao as apr WHERE apr.id_tcc = :idTcc", nativeQuery = true)
    public Long countConflictTccs(@Param("idTcc") Long idTcc);
    
    @Query(value = "SELECT COUNT(id) FROM tb_apresentacao WHERE data = :data " +
                   "AND ( " +
                   " (horas_inicio = :horasInicio AND horas_fim = :horasFim) OR " +
                   " (horas_inicio > :horasInicio AND horas_inicio < :horasFim) OR " +
                   " (horas_fim > :horasInicio AND horas_fim < :horasFim) OR " +
                   " (horas_inicio <= :horasInicio AND horas_fim >= :horasFim) OR " +
                   " (horas_inicio > :horasInicio AND horas_fim < :horasFim) " +
                   ")", nativeQuery = true)
    Long countDates(@Param("data") LocalDateTime data,
                    @Param("horasInicio") LocalTime horasInicio,
                    @Param("horasFim") LocalTime horasFim);


    @Query("SELECT COUNT(apresentacao) FROM Apresentacao as apresentacao WHERE apresentacao.localizacao.id = :idLocalizacao")
    public Long checkConflictLocalizacao(Long idLocalizacao);
    

}
