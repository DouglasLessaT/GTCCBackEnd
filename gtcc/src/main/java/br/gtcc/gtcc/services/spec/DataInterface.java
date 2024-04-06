package br.gtcc.gtcc.services.spec;

import java.util.List;


import br.gtcc.gtcc.model.nitriteid.Data; 

public interface DataInterface<T, E> {

   public  T   createData(T data);
   
   public  T   updateData(T Data);
   
   public  T   deleteData(E data);
   
   public  T getData(E data);
   
   public List<T> getAllData();
    
}
