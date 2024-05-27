package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.DataInterface;

import br.gtcc.gtcc.model.neo4j.Data;
import br.gtcc.gtcc.model.neo4j.repository.DataRepository;

import java.time.LocalTime;
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
    
        Integer dia = data.getDate().getDayOfMonth();
        Integer mes = data.getDate().getMonthValue();
        Integer ano = data.getDate().getYear();
        LocalTime horasComeco = data.getHorasComeco();
        LocalTime horasFim = data.getHorasFim();
        
        Boolean exists = repository.countByDayMonthYearAndHours(dia, mes, ano ,horasComeco ,horasFim ) > 0;

        if (exists) {

            return null;
        
        }
       
        return this.repository.save(data);

       }
       
       return null;
   
   }
   
   @Override
   public  Data   updateData(String id ,Data data){

        if(id != null && data != null){

            Data dataRepo = this.getData(id);

            if( dataRepo != null){

                dataRepo.setApresentacao(data.getApresentacao());
                dataRepo.setDate(data.getDate());     
                dataRepo.setHorasComeco(data.getHorasComeco());
                dataRepo.setHorasFim(data.getHorasFim());    
                dataRepo.setIsLock(data.getIsLock());       
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
