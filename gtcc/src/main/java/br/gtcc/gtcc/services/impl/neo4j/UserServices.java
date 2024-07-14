package br.gtcc.gtcc.services.impl.neo4j;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import br.gtcc.gtcc.util.services.UserUtil;
import br.gtcc.gtcc.util.Console;

@Service
public class UserServices implements UserInterface<Users, String> {

  @Autowired
  public UserUtil utilUser;

  @Override
  public Users createUsers(Users users) {

    users = this.utilUser.validaUser(users);

    users.setSenha(this.utilUser.enconder(users.getSenha()));

    return this.utilUser.salvarUser(users);
   
  }

  @Override
  public Users updateUsers(Users users, String id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(users.getId());

    Users userRepository = this.getUser(id);

    userRepository.setName(users.getName());
    userRepository.setEmail(users.getEmail());
    userRepository.setBirthdate(users.getBirthdate());
    userRepository.setCellphone(users.getCellphone());
    userRepository.setUserType(users.getUserType());
    userRepository.setPermissoes(users.getPermissoes());
    userRepository.setTccsGerenciados(users.getTccsGerenciados());
  
    return this.utilUser.salvarUser(userRepository);
  }

  @Override
  public Users deleteUsers(String id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);

    Users user = this.getUser(id);

    this.utilUser.repository.deleteById(id);
    return user;

  }

  @Override
  public List<Users> getAllUsers() {

    if (this.utilUser.checkRepositoryIsEmpty() == true)
      throw new RuntimeException("Não existe usuários cadastrados"); 
    
    return this.utilUser.buscarTodosUsuários(); 

  }

  @Override
  public Users getUser(String id) {
  
    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);
    return this.utilUser.buscaUsersById(id);

  }

  // Metodos aluno 
  @Override
  public List<Users> getAlunos() {

    if (this.utilUser.checkAlunosRepositoryIsEmpty() == true)
      throw new RuntimeException("Não existe usuários cadastrados"); 

    return this.utilUser.buscarTodosAlunos();

  }

  @Override
  public Users createdAluno(Users user) {

    this.utilUser.validaIdForCreate(user.getId());
    user = this.utilUser.moldeAluno(user);
    return this.utilUser.salvarUser(user);
  
  }

  @Override
  public Users updateAluno(Users users, String id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);

    Users userRepository = this.getUser(id);

    userRepository.setName(users.getName());
    userRepository.setEmail(users.getEmail());
    userRepository.setBirthdate(users.getBirthdate());
    userRepository.setCellphone(users.getCellphone());
    userRepository.setTccsGerenciados(users.getTccsGerenciados());
    
    return this.utilUser.salvarUser(userRepository);

  }

  @Override
  public Users deleteAluno(String id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);
    Users users = this.getUser(id);

    this.utilUser.repository.deleteById(id);
    return users;
  }

  @Override
  public List<Users> getAlunosSemTcc() {

    this.utilUser.countAlunosSemTccRelacionado();

    return this.utilUser.buscarTodosOsALunosSemTccRelacionados();
   
  }

  // metodos Professores
  @Override
  public List<Users> getProfessores() {

    this.utilUser.countProfessores();
    return this.utilUser.buscarProfessores();

  }

  @Override
  public Users createdProfessor(Users user) {

    this.utilUser.validaIdForCreate(user.getId());
    this.utilUser.checkExistsProfessor(user.getId());

    user = this.utilUser.moldeProfessor(user);

    return this.utilUser.salvarUser(user);
  
  }

  @Override
  public Users updateProfessor(Users users, String id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);

    Users userRepository = this.getUser(id);

    userRepository.setName(users.getName());
    userRepository.setEmail(users.getEmail());
    userRepository.setBirthdate(users.getBirthdate());
    userRepository.setCellphone(users.getCellphone());
    userRepository.setTccsGerenciados(users.getTccsGerenciados());

    return this.utilUser.salvarUser(userRepository);

  }

  @Override
  public Users deleteProfessor(String id) {

    this.utilUser.validaId(id);
    this.utilUser.checkExistsUser(id);
    Users u = this.getUser(id);

    this.utilUser.deleteUsers(id);
    return u;

  }
}