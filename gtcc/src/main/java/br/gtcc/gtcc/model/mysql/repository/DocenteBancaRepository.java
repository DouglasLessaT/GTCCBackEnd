package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.DocenteBanca;
import java.util.List;


@Repository
public interface DocenteBancaRepository extends JpaRepository<DocenteBanca, Long> {

    @Query("SELECT COUNT(tdb) FROM  DocenteBanca as tdb WHERE tdb.id = :idDocente")
    Long checkConflictDocenteBanca(@Param("idDocente") Long idDocente);

}
