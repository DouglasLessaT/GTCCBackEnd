package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface UsuarioInterface<T, E> {

   public T createUsers(T users);
   
   public T updateUsers(T users, E id);
   
   public T deleteUsers(E id);

   public List<T> getAllUsers();

   public T getUser(E id);

   public T createdAluno(T users);
   
   public T updateAluno(T users, E id);
   
   public T deleteAluno(E id);

   public List<T> getAlunos(); 

   public T createdProfessor(T users);
   
   public T updateProfessor(T users, E id);
   
   public T deleteProfessor(E id);

   public List<T> getProfessores();

   public List<T> getAlunosSemTcc();

   public List<T> getMatricula(T users);

   public List<T> getEmail(E id);
   
}