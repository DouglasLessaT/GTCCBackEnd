package br.gtcc.gtcc.util.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.AgendaRepository;
import br.gtcc.gtcc.model.neo4j.repository.ApresentationBancaRepository;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;

@Component
public class ApresentacaoUtil {
    
    @Autowired
    public ApresentationBancaRepository repository;

    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public AgendaRepository agendaRepository;

    @Autowired
    public UsersRepository usersRepository;


    public ApresentationBanca salvar(ApresentationBanca apr){
        return this.repository.save(apr);
    }

    public void delete(String id){
        this.repository.deleteById(id);
    }

    public Boolean apresentationIsNull(ApresentationBanca ap){
        if(ap != null)
            return true;
        throw new RuntimeException("Apresentação é nula");
    }

    public Boolean validaIdApresentacaoParaCriacao(String id){
        if (id == null || id == "" || id == " ")
            return true;
        throw new RuntimeException("O id da Apresentação informado é inválido");
    }

    public Boolean validaId(String id){
        if (id == null || id == "" || id == " ")
            throw new RuntimeException("O id da Apresentação informada é inválido");
        return true;
    }

    public Boolean apresentacaoDentroAgenda(ApresentationBanca ap){
        if(ap == null)
            return true;
        throw new RuntimeException("Apresentação dentro da agenda infromada não é nula");
    }

    public ApresentationBanca buscarApresentacao(String id){
        return this.repository.findById(id).get();
    }

    public ApresentationBanca buscarApresentacaoSemAgenda(String id){
        return this.repository.buscarApresentacaoSemAgenda(id);
    }

    public Boolean checkExistsApresentacao(String id){
        if(this.repository.existsById(id))
            return true;
        throw new RuntimeException("Apresentacao não existe");
    }

    public Boolean checkExistsApresentacaoParaCriacao(String id){
        if(!this.repository.existsById(id))
            return true;
        throw new RuntimeException("Apresentacao não existe");
    }

    public Boolean countConflitosDentroDeTcc(String id){
        Boolean existsConlictTcc = this.repository.countConflictTccs(id) > 0;
        
        if(existsConlictTcc == true){

            throw new IllegalArgumentException("O tcc já esta em outra apresentação");

        }

        return false;
    }

    public Boolean countConflitosDentroDeTccUpdate(String id){
        Boolean existsConlictTcc = this.repository.countConflictTccs(id) > 1;
        
        if(existsConlictTcc == true){

            throw new IllegalArgumentException("O tcc já esta em outra apresentação");

        }

        return false;
    }

    public Boolean isLockedMemberOneAndMemberTwo(LocalDateTime date ,LocalTime horasComeco,LocalTime horasFim ,String memberIId , String memberIIId){
        Boolean isLockedMemberOneAndMemberTwo = this.repository.countConflictingApresentationsByData( date,horasComeco, horasFim ,memberIId ,memberIIId) > 0;

        if(isLockedMemberOneAndMemberTwo)
            throw new RuntimeException("Os membros da apresentação ja estão alocado nesta hora de começo e fim");
        return false;

    }

    public Boolean isLockedMemberOneAndMemberTwoParaUpdate(LocalDateTime date ,LocalTime horasComeco,LocalTime horasFim ,String memberIId , String memberIIId){
        Boolean isLockedMemberOneAndMemberTwo = this.repository.countConflictingApresentationsByData( date,horasComeco, horasFim ,memberIId ,memberIIId) > 1;

        if(isLockedMemberOneAndMemberTwo)
            throw new RuntimeException("Os membros da apresentação ja estão alocado nesta hora de começo e fim");
        return false;

    }

    public ApresentationBanca adicionarTccDentroDeApresentacao(ApresentationBanca ap ,Tcc tcc){
        ap.setTcc(tcc);
        ap.setIdTcc(tcc.getId());
        return this.repository.save(ap);
    }

    public ApresentationBanca adicionarMembroVazioDentroDaApresentacao(ApresentationBanca aB ,Boolean isOne){
        if(aB.getMember1().getId() == null && isOne == true){
            aB.setMember1(null);
        }
        if(aB.getMember2().getId() == null && isOne == false){
            aB.setMember2(null);
        }
        return aB;
    }

    public Boolean countDeApresentacoes(){

        Long listApresentacoes = this.repository.count();
        if(listApresentacoes > 0)
            return true;
        throw new RuntimeException("Não existe Apresentações cadastradas");
    }

    public List<ApresentationBanca> listaTodasApresentacoes(){
        return this.repository.findAll();
    }

    public String getTccTitlePeloIdDaApresentacao(String id){
        String titulo = repository.findTccTitleByApresentationId(id);

        if(titulo == null){
            throw new IllegalArgumentException("O titulo do tcc não existe");
        }
        return titulo;
    }

    public String getNomeOrintadorPeloIdDaApresentacao(String id) {
        String nomeOrientador = repository.findTccNomeOrientadorByApresentationId(id);

        if(nomeOrientador == null){
            throw new IllegalArgumentException("O Nome do orientador não existe");
        }
        return nomeOrientador;
    }
    
}
