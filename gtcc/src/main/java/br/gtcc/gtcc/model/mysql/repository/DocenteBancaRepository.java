package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.DocenteBanca;
import java.util.List;


@Repository
public interface DocenteBancaRepository extends JpaRepository<DocenteBanca, Long> {

    @Query("SELECT COUNT(DocenteBanca) FROM  tb_docente_banca as tdb ")
    Long checkConflictDocenteBanca(Long idDocente);

}
