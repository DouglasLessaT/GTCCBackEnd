package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.DataInterface;
import br.gtcc.gtcc.model.neo4j.Data;
import br.gtcc.gtcc.model.neo4j.repository.DataRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServices implements DataInterface<Data, String>{

   @Autowired
   public DataRepository repository;
   
   @Override
   public  Data   createData(Data data){
       
       if(data.getDate() != null){
        
        Data dataRepo = repository.findByDate(data.getDate());
        Boolean existsData = dataRepo==null? false : true ;
        
        if( existsData == false){

            return this.repository.save(data);

        }else{

            return null;

        }

       }
       
       return null;
   
   }
   
   @Override
   public  Data   updateData(String id ,Data data){

        if(id != null && data != null){

            Data dataRepo = this.getData(id);

            if( dataRepo != null){

                dataRepo.setListApresentacoes(data.getListApresentacoes());
                dataRepo.setDate(data.getDate());                
                return this.repository.save(dataRepo);

            } else {

                return null;
            
            }

        }

       return null;
   }
   
   @Override
   public  Data   deleteData(String id){

        if(id != null){

            Data dataRepo = this.repository.existsById(id)==true? repository.findById(id).get() : null;

            if(dataRepo != null){

                this.repository.deleteById(id);
                return dataRepo;

            }

            return null;

        }

        return null;

    }
   
   @SuppressWarnings("unused")
    @Override
    public  Data   getData(String id){

        if(id != null || id != " "){

            return this.repository.existsById(id)==true? repository.findById(id).get() : null;

        }

        return null;

    }
   
   @Override
   public List< Data > getAllData(){
  
    Long listData = this.repository.count();
    
    if(listData > 0){

        return this.repository.findAll();

    }

    return null;
   
  }

    
}
