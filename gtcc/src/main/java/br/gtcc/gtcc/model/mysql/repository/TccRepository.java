package br.gtcc.gtcc.model.mysql.repository;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gtcc.gtcc.model.mysql.Tcc;


@Repository
public interface TccRepository extends JpaRepository <Tcc, Long> {

    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.titulo = :titulo")
    Tcc listTccByTitulo(String titulo);

    @Query("SELECT COUNT(*) as total FROM Apresentacao as tbca WHERE tbca.tcc.id IS NULL")
    Long countTccComApresentcao();    

    @Query("SELECT tbca FROM Apresentacao as tbca WHERE tbca.tcc.id IS NOT NULL")
    List<Tcc> listTccComApresentcao();

    @Query("SELECT tcc FROM Tcc as tcc WHERE tcc.usuario.id IS NULL")
    List<Tcc> listTccSemDiscente();

    @Query("UPDATE Tcc as tcc SET tcc.usuario = NULL WHERE tcc.usuario.id = :idUsuario AND tcc.id = :idTcc")
    void removeRelacaoEntreUsuarioTcc(Long idUsuario , Long idTcc);

    @Query("SELECT COUNT(tcc) FROM Tcc as tcc WHERE tcc.usuario.id = :idAluno")
    Long buscaTccAluno(Long idAluno);

    @Query("UPDATE Tcc as tcc SET tcc.usuario = NULL WHERE tcc.usuario.id = :idDiscente")
    void removerDiscenteTcc(@Param("idDiscente")Long idDiscente);
}
