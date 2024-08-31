package br.gtcc.gtcc.model.mysql.repository;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.TipoDocente;
@Primary
@Repository
public interface TipoDocenteRepository extends JpaRepository<TipoDocente, Long> {
 Optional<TipoDocente> findByTitulo(String titulo);
}
