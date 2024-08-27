package br.gtcc.gtcc.model.mysql.repository;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import br.gtcc.gtcc.model.mysql.Tcc;


@Repository
public interface TccRepository extends JpaRepository <Tcc, Long> {

    @Query("SELECT * FROM tb_tcc as tcc WHERE tcc.titulo = $titulo")
    List<Tcc> listTccByTitulo(String titulo);

    @Query("SELECT COUNT(*) as total FROM tb_apresentacao as tbca WHERE tbapr.id_tcc IS NULL")
    Long countTccComApresentcao();    

    @Query("SELECT * FROM tb_apresentacao as tbca WHERE tbapr.id_tcc IS NULL")
    List<Tcc> listTccComApresentcao();

    @Query("SELECT * FROM tb_tcc as tcc WHERE tcc.id_discente IS NULL")
    List<Tcc> listTccSemDiscente();

    @Query("UPDATE TABLE tb_tcc SET id_discente = NULL WHERE id_discente = $idUsuario AND id = $idTcc")
    void removeRelacaoEntreUsuarioTcc(Long idUsuario , Long idTcc);
}
