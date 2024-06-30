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
    
        LocalTime horasComeco = data.getHorasComeco();
        LocalTime horasFim = data.getHorasFim();
        
        Integer countAgenda =  repository.countByDateAndHours( data.getDate() ,horasComeco ,horasFim );

        Boolean exists = countAgenda > 0;

        if (exists) {

            throw new IllegalArgumentException("A data fornecida ja esta acriada.");
        
        }
        
        return this.repository.save(data);

       }
       
        throw new IllegalArgumentException("A data fornecida é nula.");
   
   }
   
   @Override
   public   Agenda    updateAgenda (String id , Agenda   agenda ){

        if(id != null &&  agenda  != null){

            Agenda  dataRepo = this.getAgenda(id);

            if( dataRepo != null){

                Boolean exists = repository.countByDateAndHours( agenda.getDate() , agenda.getHorasComeco() , agenda.getHorasFim() ) > 0;

                if (exists) {

                    throw new IllegalArgumentException("A data fornecida ja esta acriada.");
                        
                }

                if(agenda.getApresentacao() != null){

                    dataRepo.setApresentacao( agenda.getApresentacao());
                
                }
                dataRepo.setDate( agenda.getDate());     
                dataRepo.setHorasComeco( agenda.getHorasComeco());
                dataRepo.setHorasFim( agenda.getHorasFim());    
                dataRepo.setIsLock( agenda.getIsLock());       
                return this.repository.save(dataRepo);

            } else {
                
                throw new IllegalArgumentException("A data para atualizar é nula ou não existe.");
        
            }

        }
   
        throw new IllegalArgumentException("A data fornecida é nula.");
   
   }
   
   @Override
   public   Agenda    deleteAgenda(String id){

        if(id != null){

            Agenda  dataRepo = this.repository.existsById(id)==true? repository.findById(id).get() : null;

            if(dataRepo != null){

                this.repository.deleteById(id);
                return dataRepo;

            } else {

                throw new IllegalArgumentException("A data fornecida não existe.");
            }

        }

        throw new IllegalArgumentException("A data fornecida é nula.");
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

   @Override
   public List<  Agenda  > getAllAgendasFree(){
    
    Long countAgendas = this.repository.count();
    
    if(countAgendas > 0){

        return this.repository.listAgendaFree();

    }

    return null;
   
  }
   
  
}
