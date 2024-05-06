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
      throw new IllegalArgumentException("O user fornecido é inválido ou já possui um ID.");
    }
  }

  @Override
  public Users updateUsers(Users users) {
    if (users != null && users.getId() != null) {
      Users existingUser = getUsersById(users.getId());
      if (existingUser != null) {
        // Atualiza os dados do usuário existente com os dados fornecidos
        existingUser.setBirthdate(users.getBirthdate());
        existingUser.setCellphone(users.getCellphone());
        existingUser.setEmail(users.getEmail());
        existingUser.setName(users.getName());
        existingUser.setUserType(users.getUserType());
        // Salva e retorna o usuário atualizado
        return this.userrepository.save(existingUser);
      } else {
        throw new IllegalArgumentException("Usuário não encontrado para o ID fornecido: " + users.getId());
      }
    } else {
      throw new IllegalArgumentException("O usuário fornecido é inválido ou não possui um ID.");
    }
  }

  private Users getUsersById(String id) {
    return userrepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("user não encontrada para o ID fornecido: " + id));
  }

  @Override
  public Users deleteUsers(String id) {
    Users deluser = this.getUsersById(id);
    if (deluser != null) {
      userrepository.delete(deluser);
    } else {
      throw new IllegalArgumentException("O Usuario é invalido ou já possui um ID.");
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
        .orElseThrow(() -> new IllegalArgumentException("user não encontrada para o ID fornecido: " + id));
  }

}

