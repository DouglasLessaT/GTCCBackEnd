package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Apresentacao;
@Primary
@Repository
public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {

}
