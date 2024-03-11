package br.gtcc.gtcc.services.spec;

import java.util.List;


import br.gtcc.gtcc.model.nitriteid.Data; 

public interface DataInterface {

   public  Data createData(Data data);
   
   public  Data updateData(Data Data);
   
   public  Data deleteData(Data Data);
   
   public List<Data> getAllData();
   
   public  Data getData(Data Data);
    
}
