package br.gtcc.gtcc.model.mysql.repository;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import br.gtcc.gtcc.model.mysql.Tcc;


@Repository
public interface TccRepository extends JpaRepository <Tcc, Long> {

    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.titulo = :titulo")
    Tcc listTccByTitulo(String titulo);

    @Query("SELECT COUNT(*) as total FROM tb_apresentacao as tbca WHERE tbapr.id_tcc IS NULL")
    Long countTccComApresentcao();    

    @Query("SELECT apresentacao FROM Apresentacao as tbca WHERE tbapr.id_tcc IS NULL")
    List<Tcc> listTccComApresentcao();

    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.id_discente IS NULL")
    List<Tcc> listTccSemDiscente();

    @Query("UPDATE TABLE Tcc SET id_discente = NULL WHERE id_discente = :idUsuario AND id = :idTcc")
    void removeRelacaoEntreUsuarioTcc(Long idUsuario , Long idTcc);

    @Query("SELECT COUNT(tcc) FROM Tcc as tcc WHERE tcc.id_discente = :idAluno")
    Long buscaTccAluno(Long idAluno);

    @Query("UPDATE TABLE Tcc SET id_discente = NULL WHERE id_discente = :idUsuario")
    void removerDiscenteTcc(Long idDiscente);
}
