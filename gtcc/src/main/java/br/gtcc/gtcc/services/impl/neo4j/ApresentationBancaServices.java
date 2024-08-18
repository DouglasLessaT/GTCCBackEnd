package br.gtcc.gtcc.services.impl.neo4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.Agenda;
import br.gtcc.gtcc.model.neo4j.Tcc;  
import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;
import br.gtcc.gtcc.util.services.AgendaUtil;
import br.gtcc.gtcc.util.services.ApresentacaoUtil;
import br.gtcc.gtcc.util.services.TccUtil;
import br.gtcc.gtcc.util.services.UserUtil;
import br.gtcc.gtcc.util.Console;

@Service
public class ApresentationBancaServices implements ApresentationBancaInterface<ApresentationBanca, String>{

    @Autowired
    public ApresentacaoUtil apresentacaoUtil;

    @Autowired
    public TccUtil tccUtil;

    @Autowired
    public AgendaUtil agendaUtil;

    @Autowired 
    public UserUtil userUtil;

    @Override
    public ApresentationBanca createApresentationBanca(ApresentationBanca aB) {

        this.apresentacaoUtil.apresentationIsNull(aB);

        this.apresentacaoUtil.validaIdApresentacaoParaCriacao(aB.getId());
        this.tccUtil.validaIdTcc(aB.getIdTcc());
        this.agendaUtil.validaId(aB.getIdAgenda());
    
        this.apresentacaoUtil.checkExistsApresentacaoParaCriacao(aB.getId());
        this.tccUtil.checkExistsTcc(aB.getIdTcc());
        this.agendaUtil.checkExistAgenda(aB.getIdAgenda());
        
        Agenda agendaApresentacao = this.agendaUtil.buscarAgenda(aB.getIdAgenda());
        ApresentationBanca apresentacaoDentroDaAgenda = agendaApresentacao.getApresentacao();
    
        this.apresentacaoUtil.apresentacaoDentroAgenda(apresentacaoDentroDaAgenda);
        this.agendaUtil.validaData(agendaApresentacao.getDate());
        this.agendaUtil.validaHoras(agendaApresentacao.getHorasComeco(), agendaApresentacao.getHorasFim());

        LocalDateTime date = agendaApresentacao.getDate();
        LocalTime horasComeco = agendaApresentacao.getHorasComeco();
        LocalTime horasFim = agendaApresentacao.getHorasFim();

        this.apresentacaoUtil.countConflitosDentroDeTcc(aB.getIdTcc());

        String idMemberI = aB.getMember1().getId();
        String idMemberII = aB.getMember2().getId();

        Boolean idValidMember1 = this.userUtil.validaIdMembro1(idMemberI);
        Boolean idValidMember2 = this.userUtil.validaIdMembro2(idMemberII);

        if(idValidMember1 == true || idValidMember2 == true){
                      
            this.apresentacaoUtil.isLockedMemberOneAndMemberTwo(date ,horasComeco ,horasFim ,idMemberI ,idMemberII);
            this.apresentacaoUtil.adicionarMembroVazioDentroDaApresentacao(aB ,true);
            this.apresentacaoUtil.adicionarMembroVazioDentroDaApresentacao(aB ,false);
        }

        Tcc tcc = this.tccUtil.buscarTcc(aB.getIdTcc());
        this.agendaUtil.agendaIsLock(agendaApresentacao);
        this.agendaUtil.adicionarApresentacaoDentroDaAgenda(aB, agendaApresentacao);
        
        this.apresentacaoUtil.adicionarTccDentroDeApresentacao(aB ,tcc);
        
        this.apresentacaoUtil.salvar(aB);
        this.agendaUtil.salvarAgenda(agendaApresentacao);
        return aB;

    }

    @Override
    public ApresentationBanca updateApresentationBanca(String id,ApresentationBanca apresentationBanca) {
       
        this.apresentacaoUtil.validaId(id);
        this.apresentacaoUtil.validaId(apresentationBanca.getId());
        this.tccUtil.validaIdTcc(apresentationBanca.getIdTcc());
        this.agendaUtil.validaId(apresentationBanca.getIdAgenda());
    
        this.apresentacaoUtil.checkExistsApresentacao(id); 
        this.tccUtil.checkExistsTcc(apresentationBanca.getIdTcc());
        this.agendaUtil.checkExistAgenda(apresentationBanca.getIdAgenda());

        String newTccId = apresentationBanca.getIdTcc(); 
        String newAgendaId = apresentationBanca.getIdAgenda();
        
        ApresentationBanca repoApresentacao = this.getApresentationBanca(id);

        Agenda newAgendaRepo = this.agendaUtil.buscarAgenda(newAgendaId);
        Tcc newTcc = this.tccUtil.buscarTcc(newTccId);
        Tcc oldTccRepo  =this.tccUtil.buscarTcc(repoApresentacao.getIdTcc());

        this.apresentacaoUtil.countConflitosDentroDeTccUpdate(newAgendaId);

        ApresentationBanca apresentacaoDentroDaAgenda = newAgendaRepo.getApresentacao();     
        this.apresentacaoUtil.apresentacaoDentroAgenda(apresentacaoDentroDaAgenda);

        String newMemberIdOneRepo = apresentationBanca.getMember1().getId();
        String newMemberIdTwoRepo = apresentationBanca.getMember2().getId();

        LocalDateTime agenda = newAgendaRepo.getDate();
        LocalTime horasComeco = newAgendaRepo.getHorasComeco();
        LocalTime horasFim = newAgendaRepo.getHorasFim();

        if( newMemberIdOneRepo != null || newMemberIdTwoRepo != null){
        
            this.apresentacaoUtil.isLockedMemberOneAndMemberTwoParaUpdate(agenda ,horasComeco ,horasFim , newMemberIdOneRepo ,newMemberIdTwoRepo);
            this.apresentacaoUtil.adicionarMembroVazioDentroDaApresentacao( apresentationBanca,true);
            this.apresentacaoUtil.adicionarMembroVazioDentroDaApresentacao( apresentationBanca,false);

        } 

        this.agendaUtil.agendaIsLock(newAgendaRepo);            
        Boolean isEqualsAgendas = newAgendaId.equals(repoApresentacao.getIdAgenda());
        if( isEqualsAgendas==false ){

            Agenda oldAgenda = this.agendaUtil.buscarAgenda(repoApresentacao.getIdAgenda());
            this.agendaUtil.trocarAgendaDentroApresentacao(newAgendaId, apresentationBanca, oldAgenda, newAgendaRepo);
        
        }

        Boolean isEqualsTcc = newTccId.equals(repoApresentacao.getIdTcc());
            
        if(isEqualsTcc == false){

            this.tccUtil.trocaTccDentroApresentacao( newTccId, apresentationBanca, newTcc);            

        }  else {//Possivel trecho desnesessário

            this.tccUtil.trocaTccDentroApresentacao( oldTccRepo.getId(), apresentationBanca, oldTccRepo);            
            
        }

        this.tccUtil.salvarTcc(newTcc);
        this.agendaUtil.salvarAgenda(newAgendaRepo);

        return this.apresentacaoUtil.salvar(apresentationBanca);

    }

    @Override
    public ApresentationBanca deleteApresentationBanca(String id) {

        this.apresentacaoUtil.validaId(id);
        this.apresentacaoUtil.checkExistsApresentacao(id);

        ApresentationBanca apresentationBancaRepo = this.apresentacaoUtil.buscarApresentacao(id);
        
        Agenda agendaRepo = this.agendaUtil.buscarAgenda(apresentationBancaRepo.getIdAgenda());
        this.agendaUtil.liberarAgenda(agendaRepo);

        this.apresentacaoUtil.delete(id);
        return apresentationBancaRepo;

    }

    @Override
    public ApresentationBanca getApresentationBanca(String id) {

        this.apresentacaoUtil.validaId(id);
        this.apresentacaoUtil.checkExistsApresentacao(id);
        return this.apresentacaoUtil.buscarApresentacao(id);

    }

    @Override
    public List<ApresentationBanca> getAllApresentationBanca() {
       
        this.apresentacaoUtil.countDeApresentacoes();
        return this.apresentacaoUtil.listaTodasApresentacoes();   
    }
    
    @Override
      public String getTccTitlePeloIdDaApresentacao(String elementId) {
        
        this.apresentacaoUtil.validaId(elementId);
        this.apresentacaoUtil.checkExistsApresentacao(elementId);
   
        return this.apresentacaoUtil.getTccTitlePeloIdDaApresentacao(elementId);
    }

    @Override
      public String getNomeOrintadorPeloIdDaApresentacao(String elementId) {
        
        this.apresentacaoUtil.validaId(elementId);

        return this.apresentacaoUtil.getNomeOrintadorPeloIdDaApresentacao(elementId);

    }

    //Adicionar menbro caso ele tenha registrado uma apresentacao sem menbros

    //Adicionar agenda caso ele não tenha adicionado durante o cadastro de apresentacao

    //Adicionar tcc a apresentação caso ele não tenha adicionado durante o cadastro


}
