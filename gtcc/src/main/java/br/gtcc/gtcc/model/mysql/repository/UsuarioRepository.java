package br.gtcc.gtcc.model.mysql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gtcc.gtcc.model.mysql.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Encontrar por ID
    Optional<Usuario> findById(Long id);

    // Encontrar todos os usuários por login e ordenar por login
    List<Usuario> findAllByOrderByLogin();

    // Encontrar todos os usuários e ordenar por login em ordem ascendente
    List<Usuario> findAllByOrderByLoginAsc();
    
    Usuario findByLogin(String login);

    // Encontrar um usuário por email utilizando uma query JPQL
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    Usuario findByEmail(String email);

    // Encontrar usuários com o tipo 'ALUNO' usando a estrutura de permissões
    @Query("SELECT u FROM Usuario u WHERE 'ROLE_ALUNO' MEMBER OF u.permissoes")
    List<Usuario> findAlunos();

    // Encontrar usuários com o tipo 'PROFESSOR' ou 'COORDENADOR' usando a estrutura de permissões
    @Query("SELECT u FROM Usuario u WHERE 'ROLE_PROFESSOR' MEMBER OF u.permissoes OR 'ROLE_COORDENADOR' MEMBER OF u.permissoes")
    List<Usuario> findProfessores();

    // Encontrar usuários sem TCC relacionado (assumindo a existência da tabela de relacionamento)
    @Query("SELECT u FROM Usuario u WHERE NOT EXISTS (SELECT t FROM Tcc t WHERE t.usuario.idUsuario = u.idUsuario) AND 'ROLE_ALUNO' MEMBER OF u.permissoes")
    List<Usuario> getUsersSemTccRelacionado();

    // Contar usuários sem TCC relacionado
    @Query("SELECT COUNT(u) FROM Usuario u WHERE NOT EXISTS (SELECT t FROM Tcc t WHERE u.idUsuario=:idAluno AND t.usuario.idUsuario = u.idUsuario) AND 'ROLE_ALUNO' MEMBER OF u.permissoes")
    Long checkSeAlunoTemTcc(@Param("idAluno") Long idAluno);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE NOT EXISTS (SELECT t FROM Tcc t WHERE t.usuario.idUsuario = u.idUsuario) AND 'ROLE_ALUNO' MEMBER OF u.permissoes")
    Long countUsersSemTccRelacionado();

    // Contar todos os usuários com o tipo 'ALUNO'
    @Query("SELECT COUNT(u) FROM Usuario u WHERE 'ROLE_ALUNO' MEMBER OF u.permissoes")
    Long countAlunos();

    // Contar todos os usuários com o tipo 'PROFESSOR' ou 'COORDENADOR'
    @Query("SELECT COUNT(u) FROM Usuario u WHERE 'ROLE_PROFESSOR' MEMBER OF u.permissoes OR 'ROLE_COORDENADOR' MEMBER OF u.permissoes")
    Long countProfessores();
}
