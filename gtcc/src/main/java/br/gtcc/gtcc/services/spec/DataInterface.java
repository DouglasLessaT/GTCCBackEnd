package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface DataInterface<T, E> {

   public  T   createData(T data);
   
   public  T   updateData( E id ,T Data);
   
   public  T   deleteData(E id);
   
   public  T getData(E id);
   
   public List<T> getAllData();
    
}
