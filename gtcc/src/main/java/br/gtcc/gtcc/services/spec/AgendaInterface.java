package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface AgendaInterface<T, E> {

   public  T   createAgenda (T  agenda );
   
   public  T   updateAgenda ( E id ,T  Agenda );
   
   public  T   deleteAgenda (E id);
   
   public  T getAgenda (E id);
   
   public List<T> getAllAgenda ();
    
}
