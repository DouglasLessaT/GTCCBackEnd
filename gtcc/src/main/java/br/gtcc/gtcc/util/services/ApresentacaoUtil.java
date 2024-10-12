package br.gtcc.gtcc.util.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.model.mysql.Usuario;
//import br.gtcc.gtcc.model.mysql.repository.AgendaRepository;
import br.gtcc.gtcc.model.mysql.repository.ApresentacaoRepository;
import br.gtcc.gtcc.model.mysql.repository.ConviteRepository;
import br.gtcc.gtcc.model.mysql.repository.TccRepository;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApresentacaoUtil {
    
    private final ApresentacaoRepository repository;

    private final TccRepository tccRepository;

    private final UsuarioRepository usersRepository;

    private final ConviteRepository  conviteRepository;

    public Apresentacao salvar(Apresentacao apr){
        return this.repository.save(apr);
    }

    public void delete(Long id){
        this.repository.deleteById(id);
    }

    public Boolean apresentationIsNull(Apresentacao ap){
        if(ap != null)
            return true;
        throw new RuntimeException("Apresentação é nula");
    }

    public Boolean validaIdApresentacaoParaCriacao(Long id){

        if (id == null )
            return true;
        throw new RuntimeException("O id da Apresentação informado é inválido");
    }

    public Boolean validaId(Long id ){

        if (id == null )
            throw new RuntimeException("O id da Apresentação informada é inválido");
        return true;
    }

    public Boolean apresentacaoDentroAgenda(Apresentacao ap){
        if(ap == null)
            return true;
        throw new RuntimeException("Apresentação dentro da agenda infromada não é nula");
    }

    public Apresentacao buscarApresentacao(Long id){
        return this.repository.findById(id).get();
    }

    public List<Apresentacao> buscarApresentacaoSemLocalizacao(){
        return this.repository.buscarApresentacaoSemLocalizacao();
    }

    public Boolean checkExistsApresentacao(Long id){
        if(this.repository.existsById(id))
            return true;
        throw new RuntimeException("Apresentacao não existe");
    }

    public Boolean checkExistsApresentacaoParaCriacao(Long id){
        if(!this.repository.existsById(id))
            throw new RuntimeException("Apresentacao já existe");
        return true;
    }

    public Boolean countConflitosDentroDeTcc(Long id){
        Boolean existsConlictTcc = this.repository.countConflictTccs(id) > 0;
        if(existsConlictTcc == true){
            throw new IllegalArgumentException("O tcc já esta em outra apresentação");
        }
        return false;
    }

    public Boolean countDeApresentacoes(){

        Long listApresentacoes = this.repository.count();
        if(listApresentacoes > 0)
            return true;
        throw new RuntimeException("Não existe Apresentações cadastradas");
    }

    public List<Apresentacao> listaTodasApresentacoes(){
        return this.repository.findAll();
    }

    public void checkConflictsDates(LocalDateTime date , LocalTime horasComeco , LocalTime horasFim){
        boolean countDatas = this.repository.countDates(date, horasComeco, horasFim) > 0;
        if(countDatas == false)
            return ;
        throw new RuntimeException("Existe conflito de agendas");
    }

    public void checkConflictsApresentacao(Long idLocalizacao ){
        
        if( idLocalizacao!= null){

            Long apresentacoes = this.repository.checkConflictLocalizacao(idLocalizacao);
            if(apresentacoes > 0){
                throw new RuntimeException("Esta localização esta indisponível");
            }
        }
        return;
    }


    

    //Talves não tenha necessidade desse método 
    // public Boolean countConflitosDentroDeTccUpdate(Long id){
    //     Boolean existsConlictTcc = this.repository.countConflictTccs(id) > 1;
        
    //     if(existsConlictTcc == true){

    //         throw new IllegalArgumentException("O tcc já esta em outra apresentação");

    //     }

    //     return false;
    // }

    //Esse métodos de manipulção de menbros vão ser feitso em outro lugar na parte de banca ou docente banca
    // public Boolean isLockedMemberOneAndMemberTwo(LocalDateTime date ,LocalTime horasComeco,LocalTime horasFim ,Long memberIId , Long memberIIId){
    //     Boolean isLockedMemberOneAndMemberTwo = this.repository.countConflictingApresentationsByData( date,horasComeco, horasFim ,memberIId ,memberIIId) > 0;

    //     if(isLockedMemberOneAndMemberTwo)
    //         throw new RuntimeException("Os membros da apresentação ja estão alocado nesta hora de começo e fim");
    //     return false;

    // }

    // public Boolean isLockedMemberOneAndMemberTwoParaUpdate(LocalDateTime date ,LocalTime horasComeco,LocalTime horasFim ,Long memberIId , Long memberIIId){
    //     Boolean isLockedMemberOneAndMemberTwo = this.repository.countConflictingApresentationsByData( date,horasComeco, horasFim ,memberIId ,memberIIId) > 1;

    //     if(isLockedMemberOneAndMemberTwo)
    //         throw new RuntimeException("Os membros da apresentação ja estão alocado nesta hora de começo e fim");
    //     return false;

    // }

    //Isso é feito no tabela de banca 
    // public Apresentacao adicionarMembroVazioDentroDaApresentacao(Apresentacao aB ,Boolean isOne){
    //     if(aB.getMember1().getId() == null && isOne == true){
    //         aB.setMember1(null);
    //     }
    //     if(aB.getMember2().getId() == null && isOne == false){
    //         aB.setMember2(null);
    //     }
    //     return aB;
    // }

    // public Long getTccTitlePeloIdDaApresentacao(Long id){
    //     Long titulo = repository.findTccTitleByApresentationId(id);

    //     if(titulo == null){
    //         throw new IllegalArgumentException("O titulo do tcc não existe");
    //     }
    //     return titulo;
    // }

    // public Long getNomeOrintadorPeloIdDaApresentacao(Long id) {
    //     Long nomeOrientador = repository.findTccNomeOrientadorByApresentationId(id);

    //     if(nomeOrientador == null){
    //         throw new IllegalArgumentException("O Nome do orientador não existe");
    //     }
    //     return nomeOrientador;
    // }
    
}
