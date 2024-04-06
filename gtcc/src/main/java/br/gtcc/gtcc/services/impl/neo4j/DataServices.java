package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.DataInterface;
import br.gtcc.gtcc.model.neo4j.Data;
import br.gtcc.gtcc.model.neo4j.repository.DataRepository;

import java.util.List;



public class DataServices implements DataInterface<Data, String>{

   
   @Override
   public  Data   createData(Data data){
       return null;
   }
   
   @Override
   public  Data   updateData(Data Data){
       return null;
   }
   
   @Override
   public  Data   deleteData(String data){
       return null;
   }
   
   @Override
   public  Data   getData(String data){
       return null;
   }
   
   @Override
   public List< Data > getAllData(){
       return null;
   }

    
}
