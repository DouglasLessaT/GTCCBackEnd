package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
