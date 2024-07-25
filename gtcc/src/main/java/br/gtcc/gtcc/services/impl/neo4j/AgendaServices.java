package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.AgendaInterface;
import br.gtcc.gtcc.util.services.AgendaUtil;
import br.gtcc.gtcc.util.services.ApresentacaoUtil;
import br.gtcc.gtcc.model.neo4j.Agenda;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import br.gtcc.gtcc.util.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaServices implements AgendaInterface<Agenda, String>{

   @Autowired
   public AgendaUtil agendaUtil;

   @Autowired
   public ApresentacaoUtil aprUtil;

   @Override
   public Agenda createAgenda(Agenda data){
       
        this.agendaUtil.validaIdAgendaParaCriacao(data.getId());
        
        LocalDateTime agenda = data.getDate();
        LocalTime horasComeco = data.getHorasComeco();
        LocalTime horasFim = data.getHorasFim();
        this.agendaUtil.validaData(agenda);     
        this.agendaUtil.validaHoras(horasComeco, horasFim);
        
        this.agendaUtil.countConflicts(agenda, horasComeco, horasFim);

        return this.agendaUtil.salvarAgenda(data);

   }
   
   @Override
   public Agenda updateAgenda (String id ,Agenda agenda ){

        LocalDateTime date = agenda.getDate();
        LocalTime horasComeco = agenda.getHorasComeco();
        LocalTime horasFim = agenda.getHorasFim();
        this.agendaUtil.validaId(id);
        this.agendaUtil.validaData(date);     
        this.agendaUtil.validaHoras(horasComeco, horasFim);

        this.agendaUtil.checkExistAgenda(id);
        Agenda dataRepo = this.agendaUtil.buscarAgenda(id);

        this.agendaUtil.countConflicts(date, horasComeco, horasFim);
        dataRepo = this.agendaUtil.moldAgenda(dataRepo, agenda);

        return this.agendaUtil.salvarAgenda(dataRepo);
   
   }
   
   @Override
   public Agenda deleteAgenda(String id){

        this.agendaUtil.validaId(id);
        this.agendaUtil.checkExistAgenda(id);
        Agenda dataRepo = this.agendaUtil.buscarAgenda(id);
        this.agendaUtil.deletarAgenda(id);
        return dataRepo; 

    }
   
    @Override
    public Agenda getAgenda(String id){

        this.agendaUtil.validaId(id);
        this.agendaUtil.checkExistAgenda(id);
        return this.agendaUtil.buscarAgenda(id);

    }
   
   @Override
   public List<Agenda> getAllAgenda(){

        this.agendaUtil.countAgendas();
        return this.agendaUtil.buscarTodasAgendas();

   }

   @Override
   public List<Agenda> getAllAgendasFree(){
    
        this.agendaUtil.countAgendasFree();
        return this.agendaUtil.buscarTodasAgendasLivres();
   
  }

  @Override
  public Agenda adicionarApresentacaoEemAgenda(String idApresentacao , String idAgenda){

        this.agendaUtil.validaId(idAgenda);
        this.agendaUtil.validaId(idApresentacao);

        this.agendaUtil.checkExistAgenda(idAgenda);
        this.aprUtil.checkExistsApresentacao(idApresentacao);

        Agenda agenda = this.agendaUtil.buscarAgendaSemApresentacao(idAgenda);
        ApresentationBanca apresentacao = this.aprUtil.buscarApresentacaoSemAgenda(idAgenda);

        apresentacao.setIdAgenda(idAgenda);
        agenda.setApresentacao(apresentacao);
        
        this.agendaUtil.salvarAgenda(agenda);
        this.aprUtil.salvar(apresentacao);
        return agenda;
  }
  
}
