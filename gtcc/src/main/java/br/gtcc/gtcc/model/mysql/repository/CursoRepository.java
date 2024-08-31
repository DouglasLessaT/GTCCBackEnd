package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("UPDATE TABLE Curso SET ativo = 'False' WHERE id = :idCurso ")
    public void inativar(Long idCurso);
}
