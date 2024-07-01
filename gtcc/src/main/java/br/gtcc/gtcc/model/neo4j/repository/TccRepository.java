package br.gtcc.gtcc.model.neo4j.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;

import java.util.List;


public interface TccRepository extends Neo4jRepository<Tcc, String> {
 Tcc findByTitle(String title); // implementação futura
 Optional<Tcc> findById(String id);

    @Query("MATCH(u:Users)-[r:GERENCIA]->(t:Tcc)WHERE elementId(u)=$idUsuario AND elementId(t)=$idTcc DETACH DELETE r ")
    Void removeRelacaoEntreUsuarioTcc(@Param("idUsuario") String idUsuario ,@Param("idTcc") String idTcc );

    @Query("MATCH(u:Users)WHERE elementId(u)=$idElement RETURN u")
    Users getUsers(@Param("idElement") String idElement);

    @Query("MATCH(t:Tcc)WHERE NOT EXISTS((t)-[:TCC_APRESENTA_EM]->(:ApresentationBanca)) RETURN COUNT(t)")
    Long countTccInApresentation();

    @Query("MATCH(t:Tcc)WHERE NOT EXISTS((t)-[:TCC_APRESENTA_EM]->(:ApresentationBanca)) RETURN t")
    List<Tcc> getTccInApresentation();
}
