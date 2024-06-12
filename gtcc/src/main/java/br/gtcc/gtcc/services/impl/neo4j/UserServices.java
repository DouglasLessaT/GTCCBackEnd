package br.gtcc.gtcc.services.impl.neo4j;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;

import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;

@Service
public class UserServices implements UserInterface<Users, String> {

  @Autowired
  public UsersRepository userrepository;

  @Override
  public Users createUsers(Users users) {
    if (users != null && users.getId() == null) {
      return userrepository.save(users);
    } else {
      throw new IllegalArgumentException("O usuário fornecido é inválido ou já possui um ID.");
    }
  }

  @Override
  public Users updateUsers(Users users) {
    if (users != null && users.getId() != null) {
      Optional<Users> existingUserOpt = userrepository.findById(users.getId());
      if (existingUserOpt.isPresent()) {
        Users existingUser = existingUserOpt.get();
        existingUser.setBirthdate(users.getBirthdate());
        existingUser.setCellphone(users.getCellphone());
        existingUser.setEmail(users.getEmail());
        existingUser.setName(users.getName());
        existingUser.setUserType(users.getUserType());
        existingUser.setPermissoes(users.getPermissoes());
        existingUser.setTccsGerenciados(users.getTccsGerenciados());
        return userrepository.save(existingUser);
      } else {
        throw new IllegalArgumentException("Usuário não encontrado para o ID fornecido: " + users.getId());
      }
    } else {
      throw new IllegalArgumentException("O usuário fornecido é inválido ou não possui um ID.");
    }
  }

  private Users getUsersById(String id) {
    return userrepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o ID fornecido: " + id));
  }

  @Override
  public Users deleteUsers(String id) {
    Users deluser = this.getUsersById(id);
    if (deluser != null) {
      userrepository.delete(deluser);
    } else {
      throw new IllegalArgumentException("O Usuário é inválido ou já possui um ID.");
    }
    return null;
  }

  @Override
  public List<Users> getAllUsers() {
    return userrepository.findAll();
  }

  @Override
  public Users getUsers(String id) {
    return userrepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o ID fornecido: " + id));
  }

  @Override
  public List<Users> getAlunos() {
    return userrepository.findAlunos();
  }
}
