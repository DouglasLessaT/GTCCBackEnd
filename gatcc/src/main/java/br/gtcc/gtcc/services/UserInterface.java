/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.gtcc.gtcc.services;

import br.gtcc.gtcc.model.Users;
import java.util.List;



/**
 *
 * @author privateclasswizard
 */
public interface UserInterface {
    
   public Users createUsers(Users users);
   
   public Users updateUsers(Users users);
   
   public Users deleteUsers(Users users);
   
   public List<Users> getAllUsers();
   
   public Users getUsers(Users users);
   
}
