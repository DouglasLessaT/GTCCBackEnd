package br.gtcc.gtcc.model.mysql.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Apresentacao;
@Primary
@Repository
public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {


    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.id_localizacao IS NULL")
    public List<Apresentacao> buscarApresentacaoSemApresentacao();

    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.id_tcc IS NULL")
    public List<Apresentacao> buscarApresentacaoSemTcc();
    
    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.ativo = false")
    public List<Apresentacao> buscarApresentacoesInativas();

    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.id_tcc = :idTcc")
    public Long countConflictTccs(Long idTcc);
    
    @Query("SELECT COUNT(a.id) FROM Apresentacao a WHERE a.data = :data"+
        " AND ( " +
            " (a.horasInicio = :horasInicio AND a.horasFim = :horasFim) OR " +
            " (a.horasInicio > :horasInicio AND a.horasInicio < :horasFim) OR "+ 
            " (a.horasFim > :horasInicio AND a.horasFim < :horasFim) OR "+
            " (a.horasInicio <= :horasInicio AND a.horasFim >= :horasFim) OR "+
            " (a.horasInicio > :horasInicio AND a.horasFim < :horasFim));")
    public Long countDates(LocalDateTime data , LocalTime horasInicio , LocalTime horasFim);

    @Query("SELECT COUNT(apresentacao) FROM Apresentacao as apresentacao WHERE apresentacao.id_localizacao = :idLocalizacao")
    public Long checkConflictLocalizacao(Long idLocalizacao);
    

}
