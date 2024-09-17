package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("UPDATE Curso as curso SET curso.ativo = 1 WHERE curso.id = :idCurso ")
    public void inativar(@Param("idCurso") Long idCurso);
}
