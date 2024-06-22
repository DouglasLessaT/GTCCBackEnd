package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface UserInterface<T, E> {

   public T createUsers(T users);

   public T updateUsers(T users);

   public T deleteUsers(T users);

   public List<T> getAllUsers();

   public T getUsers(E id);
   
   public T createdAluno(T users);
   
   public List<T> getAlunos(); 

   public List<T> getProfessores();

}
