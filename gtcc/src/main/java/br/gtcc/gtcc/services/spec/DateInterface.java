package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface DateInterface<T, E> {

   public  T   createDate(T date);
   
   public  T   updateDate(T Date);
   
   public  T   deleteDate(E date);
   
   public  T getDate(E date);
   
   public List<T> getAllDate();
    
}
