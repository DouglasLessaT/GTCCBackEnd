package br.gtcc.gtcc.model.mysql.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Localizacao;

@Primary
@Repository
public interface LocalizacaoRepository extends JpaRepository <Localizacao, Long> {
    boolean existsByPredioAndSalaAndAndar(String predio, String sala, String andar);
}
