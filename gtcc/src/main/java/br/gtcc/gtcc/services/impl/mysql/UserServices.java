package br.gtcc.gtcc.services.impl.mysql;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.services.spec.UserInterface;
import br.gtcc.gtcc.util.services.UsuarioUtil;
import br.gtcc.gtcc.util.Console;

@Service
public class UserServices implements UserInterface<Usuario, Long> {

  @Autowired
  public UsuarioUtil utilUser;

  @Override
  public Usuario createUsers(Usuario users) {

    users = this.utilUser.validaUser(users);

    users.setSenha(this.utilUser.enconder(users.getSenha()));

    return this.utilUser.salvarUser(users);

  }

  @Override
  public Usuario updateUsers(Usuario users, Long id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(users.getId());

    Usuario userRepository = this.getUser(id);

    userRepository.setNome(users.getNome());
    userRepository.setEmail(users.getEmail());
    userRepository.setDataNascimento(users.getDataNascimento());
    userRepository.setTelefone(users.getTelefone());
    // userRepository.setPermissoes(users.getPermissoes());
    // userRepository.setTccsGerenciados(users.getTccsGerenciados());

    return this.utilUser.salvarUser(userRepository);
  }

  @Override
  public Usuario deleteUsers(Long id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);

    Usuario user = this.getUser(id);

    this.utilUser.deleteUsers(id);
    return user;

  }

  @Override
  public List<Usuario> getAllUsers() {

    if (this.utilUser.checkRepositoryIsEmpty() == true)
      throw new RuntimeException("Não existe usuários cadastrados");

    return this.utilUser.buscarTodosUsuários();

  }

  @Override
  public Usuario getUser(Long id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);
    return this.utilUser.buscaUsersById(id);

  }

  // Metodos aluno
  @Override
  public List<Usuario> getAlunos() {

    if (this.utilUser.checkAlunosRepositoryIsEmpty() == true)
      throw new RuntimeException("Não existe usuários cadastrados");

    return this.utilUser.buscarTodosAlunos();

  }

  @Override
  public Usuario createdAluno(Usuario user) {

    this.utilUser.validaIdForCreate(user.getId());
    user = this.utilUser.moldeAluno(user);
    return this.utilUser.salvarUser(user);

  }

  @Override
  public Usuario updateAluno(Usuario users, Long id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);

    Usuario userRepository = this.getUser(id);

    userRepository.setNome(users.getNome());
    userRepository.setEmail(users.getEmail());
    userRepository.setDataNascimento(users.getDataNascimento());
    userRepository.setTelefone(users.getTelefone());
    // userRepository.setTccsGerenciados(users.getTccsGerenciados());

    return this.utilUser.salvarUser(userRepository);

  }

  @Override
  public Usuario deleteAluno(Long id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);
    Usuario users = this.getUser(id);

    this.utilUser.deleteUsers(id);
    return users;
  }

  @Override
  public List<Usuario> getAlunosSemTcc() {

    this.utilUser.countAlunosSemTccRelacionado();

    return this.utilUser.buscarTodosOsALunosSemTccRelacionados();

  }

  // metodos Professores
  @Override
  public List<Usuario> getProfessores() {

    this.utilUser.countProfessores();
    return this.utilUser.buscarProfessores();

  }

  @Override
  public Usuario createdProfessor(Usuario user) {

    this.utilUser.validaIdForCreate(user.getId());
    this.utilUser.checkExistsProfessor(user.getId());

    user = this.utilUser.moldeProfessor(user);

    return this.utilUser.salvarUser(user);

  }

  @Override
  public Usuario updateProfessor(Usuario users, Long id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);

    Usuario userRepository = this.getUser(id);

    userRepository.setNome(users.getNome());
    userRepository.setEmail(users.getEmail());
    userRepository.setDataNascimento(users.getDataNascimento());
    userRepository.setTelefone(users.getTelefone());
    // userRepository.setTccsGerenciados(users.getTccsGerenciados());

    return this.utilUser.salvarUser(userRepository);

  }

  @Override
  public Usuario deleteProfessor(Long id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);
    Usuario u = this.getUser(id);

    this.utilUser.deleteUsers(id);
    return u;

  }

  @Override
  public List<Usuario> getMatricula(Usuario users) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getMatricula'");
  }

  @Override
  public List<Usuario> getEmail(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
  }
}