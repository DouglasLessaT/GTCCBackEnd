/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.services.impl.nitritedb;

import br.gtcc.gtcc.model.nitriteid.Users;
import br.gtcc.gtcc.services.spec.UserInterface;
import java.util.List;
import org.dizitart.no2.collection.FindOptions;
import org.dizitart.no2.common.SortOrder;
import org.dizitart.no2.common.WriteResult;
import org.dizitart.no2.filters.FluentFilter;
import static org.dizitart.no2.filters.FluentFilter.where;
import org.dizitart.no2.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author mrbee
 * 
 * Classe que serve aos controllers, essa classe manipula diretamente as consultas e retornar as 
 * informações para as controllers
 * 
 * 
 * criar interface entre a service e controller
 */
@Service
public class UserServices implements UserInterface<Users, String>{
   
    @Autowired
    ObjectRepository<Users> repositoryUsers;
    
    @Override
    public Users createUsers(Users users){
        
        if( users != null ){
            
            if( users.getRegistration() != null ){
            
                repositoryUsers.insert(users);
                
            }else{
                
                return null;
                
            }
            
        }else{
            
            return null;
        
        }
        
        return users;
    }
    
    @Override
    public Users deleteUsers(String id){
        
        Users getU = this.getUsers(id);
        
        if( getU != null ){
            
                if( getU != null ){
                
                    WriteResult result = repositoryUsers.remove(where("id").eq(id));
                    getU = (Users) result;
            
                } else {

                    return null;

                }            
            
        } else {
            
            return null;
            
        }
        
        return getU;
    }
    
    @Override
    public Users updateUsers(Users users){
        
           String  id = users.getId();   
           Users getU = this.getUsers(id);
        
        if( users != null ){
            
            if( users.getRegistration() != null ){
            
                if( getU != null ){
                
                    WriteResult result = repositoryUsers.update(where("id").eq(users.getRegistration()), users);
                    users = (Users) result;
            
                } else {

                    return null;

                }
                
            } else {
                
                return null;
                
            }            
            
        } else {
            
            return null;
            
        }
        
        return users;
    }
    
    @Override
    public List<Users> getAllUsers()  throws IllegalArgumentException{

        List<Users> list = repositoryUsers.find(FindOptions.orderBy("name", SortOrder.Ascending)).toList(); 
        
        if( list != null){
        
             return list; 
            
        }else{
        
            //Exeção caso não encontra nada
            throw new IllegalArgumentException("Lista Vazia");
            
        }

    }

    @Override
    public Users getUsers(String id)  throws IllegalArgumentException{
        
        if( id != null ){
            
                //Realiza  buscano banco de dados
               Users user = repositoryUsers.find(FluentFilter.where("id").eq(id)).firstOrNull();
               
               if( user != null ){
               
                    return user;
                   
               }else{
               
                   //Exeção caso o usuário não é encontrado
                   return null;
               
               }
                
            
        } else {
        
            //Execeção caso o usuário fornecido seja nulo 
            return null;
                    
        }
    
    }
}
