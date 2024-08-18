package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.DocenteBanca;

@Repository
public interface DocenteBancaRepository extends JpaRepository<DocenteBanca, Long> {

}
