package br.gtcc.gtcc.services.impl.neo4j;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;

@Service
public class UserServices implements UserInterface<Users, String> {

 @Autowired
 public UsersRepository repository;

 @Override
 public Users createUsers(Users users) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'createUsers'");
 }

 @Override
 public Users updateUsers(Users users) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'updateUsers'");
 }

 @Override
 public Users deleteUsers(String id) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'deleteUsers'");
 }

 @Override
 public List<Users> getAllUsers() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
 }

 @Override
 public Users getUsers(String id ) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getUsers'");
 }
 
}
