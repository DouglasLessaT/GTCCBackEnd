package br.gtcc.gtcc.services.impl.neo4j;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;


@Service
public class UserServices implements UserInterface<Users, String> {

 @Autowired
 public UsersRepository repository;

 @Override
 public Users createUsers(Users users) {
    
    if(users.getId() == null || users.getId() == "" || users.getId() == " "){
        
        return  this.repository.save(users);
    
    }
    if( this.repository.existsById(users.getId()) != true){

        return this.repository.save(users);

    }
    return  null;
 }

 @Override
 public Users updateUsers(Users users , String id) {
    
    if(id != null || id != "" || id != " "){
       
        Users u = this.getUsers(id);
       
        if(u != null){

            u.setName(users.getName());
            u.setEmail(users.getEmail());
            u.setBirthdate(users.getBirthdate());
            u.setCellphone(users.getCellphone());
            u.setUserType(users.getUserType());

            return this.repository.save(u);

        }
    }

    return null;
 
}

 @Override
 public Users deleteUsers(String id) {

    if(id != "" || id != null || id != " ") {

        Users u = this.getUsers(id);
        if( u != null ){

            this.repository.deleteById(id);
            return u; 

        }
    }

    return null;

 }

 @Override
 public List<Users> getAllUsers() {

    if(this.repository.count() > 0){

        return this.repository.findAll();
    
    }
    
    return null;

 }

 @Override
 public Users getUsers(String id ) {
  
    if(id != " " || id != null){

       return repository.existsById(id)==true? repository.findById(id).get() : null;
    }

    return null;
    
 }
 
}
