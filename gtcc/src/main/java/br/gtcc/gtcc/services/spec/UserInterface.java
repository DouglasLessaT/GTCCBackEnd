/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.gtcc.gtcc.services.spec;

import java.util.List;
import java.util.Optional;

import br.gtcc.gtcc.model.neo4j.Users;

/**
 *
 * @author privateclasswizard
 */
public interface UserInterface<T, E> {

   public T createUsers(T users);

   public T updateUsers(T users);

   public T deleteUsers(E id);

   public List<T> getAllUsers();

   public T getUsers(E id);

}
