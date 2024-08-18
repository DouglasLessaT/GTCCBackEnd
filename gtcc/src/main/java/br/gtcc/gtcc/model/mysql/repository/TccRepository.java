package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Tcc;


@Repository
public interface TccRepository extends JpaRepository <Tcc, Long> {

}
