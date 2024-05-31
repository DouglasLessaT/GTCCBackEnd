package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.AgendaInterface;
import br.gtcc.gtcc.util.Console;
import br.gtcc.gtcc.model.neo4j.Agenda;

import br.gtcc.gtcc.model.neo4j.repository.AgendaRepository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaServices implements AgendaInterface<Agenda, String>{

   @Autowired
   public AgendaRepository repository;
   
   @Override
   public  Agenda   createAgenda(Agenda data){
       
       if(data.getDate() != null){
    
        Integer dia = data.getDate().getDayOfMonth();
        Integer mes = data.getDate().getMonthValue();
        Integer ano = data.getDate().getYear();
        LocalTime horasComeco = data.getHorasComeco();
        LocalTime horasFim = data.getHorasFim();
        
        Boolean exists = repository.countByDateAndHours( data.getDate() ,horasComeco ,horasFim ) > 0;

        if (exists) {

            return null;
        
        }
        return this.repository.save(data);

       }
       
       return null;
   
   }
   
   @Override
   public   Agenda    updateAgenda (String id , Agenda   agenda ){

        if(id != null &&  agenda  != null){

            Agenda  dataRepo = this.getAgenda(id);

            if( dataRepo != null){

                dataRepo.setApresentacao( agenda.getApresentacao());
                dataRepo.setDate( agenda.getDate());     
                dataRepo.setHorasComeco( agenda.getHorasComeco());
                dataRepo.setHorasFim( agenda.getHorasFim());    
                dataRepo.setIsLock( agenda.getIsLock());       
                return this.repository.save(dataRepo);

            } else {

                return null;
            
            }

        }

       return null;
   }
   
   @Override
   public   Agenda    deleteAgenda(String id){

        if(id != null){

            Agenda  dataRepo = this.repository.existsById(id)==true? repository.findById(id).get() : null;

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
    public   Agenda    getAgenda(String id){

        if(id != null || id != " "){

            return this.repository.existsById(id)==true? repository.findById(id).get() : null;

        }

        return null;

    }
   
   @Override
   public List<  Agenda  > getAllAgenda(){
    
    Long countAgendas = this.repository.count();
    
    if(countAgendas > 0){

        return this.repository.findAll();

    }

    return null;
   
  }
    
}
