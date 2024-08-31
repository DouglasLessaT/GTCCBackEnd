package br.gtcc.gtcc.model.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.StatusConvite;
import br.gtcc.gtcc.model.mysql.Convite;

@Repository
public interface ConviteRepository extends JpaRepository<Convite, Long> {

 List<Convite> findByStatus(StatusConvite status);
}
