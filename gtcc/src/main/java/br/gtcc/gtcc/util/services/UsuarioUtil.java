package br.gtcc.gtcc.util.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
//import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import br.gtcc.gtcc.util.exceptions.usuario.UsuarioNaoEcontradoException;

@Component
public class UsuarioUtil {

    public static final List<String> PERMISSOES = Arrays.asList(
            "ROLE_USER", "ROLE_ADMIN", "ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ALUNO");

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public void validaId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido: o ID não pode ser nulo ou negativo.");
        }
    }

    public Boolean usersIsNull(Usuario user) {
        if (user == null)
            throw new RuntimeException("Usuário é nulo");
        return false;
    }

    public Usuario salvarUser(Usuario user) {
        return repository.save(user);
    }

    public String enconder(String senha) {
        return passwordEncoder.encode(senha);
    }

    public Usuario validaUser(Usuario user) {
        validaIdForCreate(user.getIdUsuario());
        validaLogin(user.getMatricula());
        validaPermissao(user.getPermissoes());
        return user;
    }

    public Boolean validaIdForCreate(Long id) {
        if (id == null)
            return true;
        throw new RuntimeException("O id informado é inválido");
    }

    public Boolean validaLogin(String login) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        if (!pattern.matcher(login).matches()) {
            throw new RuntimeException("Login inválido, tem que ser números");
        }
        return true;
    }

    public Boolean validaPermissao(List<String> permissoes) {
        for (String permissao : permissoes) {
            if (!PERMISSOES.contains(permissao)) {
                throw new RuntimeException("Existe alguma permissão inválida");
            }
        }
        return true;
    }

    public Boolean checkExistsUser(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNaoEcontradoException("Usuário não encontrado");
        }
        return true;
    }

    public Usuario buscaUsersById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsuarioNaoEcontradoException("Usuário não encontrado"));
    }

    public List<Usuario> buscarTodosUsuários() {
        return repository.findAll();
    }

    public List<Usuario> buscarTodosAlunos() {
        return repository.findAlunos();
    }

    public Boolean checkRepositoryIsEmpty() {
        return repository.count() == 0;
    }

    public Boolean checkAlunosRepositoryIsEmpty() {
        return repository.countAlunos() == 0;
    }

    public Usuario moldeAluno(Usuario user) {
        user.setPermissoes(Arrays.asList("ROLE_USER", "ROLE_ALUNO"));
        return user;
    }

    public void deleteUsers(Long id) {
        repository.deleteById(id);
    }

    public Long countAlunosSemTccRelacionado() {
        Long count = repository.countUsersSemTccRelacionado();
        if (count == 0) {
            throw new RuntimeException("Não existe alunos livres.");
        }
        return count;
    }

    public List<Usuario> buscarTodosOsALunosSemTccRelacionados() {
        return repository.getUsersSemTccRelacionado();
    }

    public List<Usuario> buscarProfessores() {
        return repository.findProfessores();
    }

    public Long countProfessores() {
        Long count = repository.countProfessores();
        if (count == 0) {
            throw new RuntimeException("Não existe Professores cadastrados.");
        }
        return count;
    }

    public Boolean checkExistsProfessor(Long id) {
        return repository.existsById(id);
    }

    public Usuario moldeProfessor(Usuario user) {
        user.setPermissoes(Arrays.asList("ROLE_USER", "ROLE_PROFESSOR"));
        return user;
    }

    public Boolean validaIdMembro1(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Membro 1 não existe no banco");
        }
        return true;
    }

    public Boolean validaIdMembro2(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Membro 2 não existe no banco");
        }
        return true;
    }

    public Usuario findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email);
    }
}