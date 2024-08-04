package br.gtcc.gtcc.util.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.gtcc.gtcc.model.neo4j.Agenda;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.repository.AgendaRepository;

@Component
public class AgendaUtil {
   
   @Autowired
   public AgendaRepository repository;

   public Agenda salvarAgenda(Agenda agenda){
        return this.repository.save(agenda);
   }

   public void deletarAgenda(String id){
        this.repository.deleteById(id);
   }

   public Boolean agendaIsNull(Agenda agenda){
     if(agenda == null)
          return true;
     throw new RuntimeException("O id da Agenda");  
   }

   public Boolean validaId(String id){
   
        if (id == null || id == "" || id == " ")
            throw new RuntimeException("O id do Agenda informado é inválido");
        return true; 
   }

   public Boolean validaIdAgendaParaCriacao(String id){
        if (id == null || id == "" || id == " ")
            return true;
        throw new RuntimeException("O id do Agenda informado é inválido");
   }
   
   public Boolean validaData(LocalDateTime date){
        if(date != null)
            return true;
        throw new RuntimeException("O id do Agenda informado é inválido");
   }

   public Boolean validaHoras(LocalTime horasComeco ,LocalTime horasFim){
        if(horasComeco != null && horasFim != null)
            return true;
        throw new RuntimeException("A agenda informado é inválido"); 
   }

   public Boolean checkExistAgenda(String id){
        Boolean exists = this.repository.existsById(id);
        if(exists)
            return true;
        throw new RuntimeException("A agenda informada não existe"); 
    }
   
   public Agenda buscarAgendaSemApresentacao(String id){
        return this.repository.buscarAgendaSemApresentacao(id);  
   }

   public Agenda buscarAgenda(String id){
        return this.repository.findById(id).get();
   }

   public Agenda moldAgenda(Agenda oldAgenda ,Agenda newAgenda){
        
        oldAgenda.setId(newAgenda.getId());
        oldAgenda.setDate(newAgenda.getDate());
        oldAgenda.setHorasComeco(newAgenda.getHorasComeco());
        oldAgenda.setHorasFim(newAgenda.getHorasFim());
        oldAgenda.setIsLock(newAgenda.getIsLock());

        if(newAgenda.getApresentacao() != null){

          oldAgenda.setApresentacao( newAgenda.getApresentacao());
      
        }      

        return oldAgenda;
   }

   public List<Agenda> buscarTodasAgendas(){
        return this.repository.getAllAgendas();
   }

   public List<Agenda> buscarTodasAgendasLivres(){
        return this.repository.listAgendaFree();
   }

   public Boolean countAgendas(){

        if(this.repository.count() > 0)
            return true;
        throw new RuntimeException("Não existe agendas cadastradas"); 
        
   }

   public Boolean countAgendasFree(){

        if(this.repository.listAgendaFree().size() > 0)
            return true;
        throw new RuntimeException("Não existe agendas livres cadastradas");
   }    

   public Boolean countConflicts(LocalDateTime date ,LocalTime horasComeco ,LocalTime horasFim){
        Boolean exists = this.repository.countConflitosByDateAndHours(date ,horasComeco ,horasFim) == 0;
        if(exists)
            return true; 
        throw new RuntimeException("Existe conflito de agendas");
   }

   public Boolean agendaIsLock(Agenda agendaApresentacao){
        Boolean isLock = agendaApresentacao.getIsLock();
        if(isLock == true)
          throw new RuntimeException("Existe conflito nesta data");
        return false;
   }

    public Agenda adicionarApresentacaoDentroDaAgenda(ApresentationBanca ap ,Agenda agenda){
          
          agenda.setApresentacao(ap);
          agenda.setIsLock(true);

          return agenda;
    }

    public void liberarAgenda(Agenda agenda){

          agenda.setIsLock(false);
          this.repository.save(agenda);
    }
 
    public void trocarAgendaDentroApresentacao(String newAgendaId ,ApresentationBanca apresentationBanca ,Agenda oldAgenda ,Agenda newAgendaRepo){

          oldAgenda.setIsLock(false);
          oldAgenda.setApresentacao(null);

          this.repository.save(oldAgenda);

          newAgendaRepo.setApresentacao(apresentationBanca);
          newAgendaRepo.setIsLock(true);
          apresentationBanca.setIdAgenda(newAgendaId);
     
    }

}
