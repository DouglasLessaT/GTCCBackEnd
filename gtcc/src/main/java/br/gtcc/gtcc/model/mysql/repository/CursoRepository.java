package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
