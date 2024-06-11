package br.gtcc.gtcc.services.impl.neo4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.Agenda;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.ApresentationBancaRepository;
import br.gtcc.gtcc.model.neo4j.repository.AgendaRepository;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;
import br.gtcc.gtcc.util.Console;

@Service
public class ApresentationBancaServices implements ApresentationBancaInterface<ApresentationBanca, String>{

    @Autowired
    public ApresentationBancaRepository repository;

    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public AgendaRepository agendaRepository;

    @Autowired
    public UsersRepository usersRepository;


    @Override
    public ApresentationBanca createApresentationBanca(ApresentationBanca aB) {
        
        Boolean isNull = aB == null;
        if( isNull == false ){

            if(aB.getId() == null){
                //Verificar se o id do tcc é válido e existe no banco, caso não retorna null -> 3
                if(aB.getIdTcc() != null ){

                    Boolean existsTcc  = this.tccRepository.existsById(aB.getIdTcc());
                    //Verificar se a data mencionanda existe -> 4

                    Agenda agendaApresentacao = this.agendaRepository.findById(aB.getIdAgenda()).orElse(null);
                    ApresentationBanca apresentacaoDentroDaAgenda = agendaApresentacao.getApresentacao();

                    if(existsTcc == true && agendaApresentacao.getDate() != null){
                        
                        Boolean existsConlictTcc = this.repository.countConflictTccs(aB.getIdTcc()) > 0;
                        Console.log("Conflito de tcc "+this.repository.countConflictTccs(aB.getIdTcc()));
                        Console.log("Conflito existe "+existsConlictTcc);
                        if(existsConlictTcc == true){
                            return null; // -> Tcc já esta alocado a uma apresentação
                        }

                        if( aB.getMember1() != null && aB.getMember1() != null ){

                            //Verificar se os membros um e dois ja existem. -> 5
                            Boolean existsMenberI = this.usersRepository.existsById(aB.getMember1().getId());
                            Boolean existsMenberII  = this.usersRepository.existsById(aB.getMember1().getId());

                            if(existsMenberI == true && existsMenberII == true){

                                LocalDateTime date = agendaApresentacao.getDate();
                                LocalTime horasComeco = agendaApresentacao.getHorasComeco();
                                LocalTime horasFim = agendaApresentacao.getHorasFim();
                                String memberI = aB.getMember1().getId();
                                String memberII = aB.getMember2().getId();
                                
                                //Verificar se os menbros 1 e 2 já estão alocados na data entregue pelo cliente(FRONT-END), caso não esteja continuar fluxo ->6
                                //Caso estejam alocados verificar se a hora entregue já esta alocada para os dois menbros -> 7
                                Boolean isLockedMemberOneAndMemberTwo = repository.countConflictingApresentationsByData( date,horasComeco, horasFim ,memberI ,memberII) > 0;
 
                                if (!isLockedMemberOneAndMemberTwo) {

                                    //Verificar se existe conflito de horário na apresentação presente, se ja existe um apresentação alocada no mesmo horário ->8   
                                    Boolean isLock = agendaApresentacao.getIsLock();
                                    Tcc tcc = this.tccRepository.findById(aB.getIdTcc()).get();
                                    
                                    if(isLock == false && apresentacaoDentroDaAgenda == null ){

                                        agendaApresentacao.setApresentacao(aB);
                                        agendaApresentacao.setIsLock(true);
                                        
                                        agendaRepository.save(agendaApresentacao);
                                        
                                        aB.setTcc(tcc);
                                        return repository.save(aB);
    
                                    }else {

                                        return null;//-> esta data ja esta alocada 

                                    }
                                
                                } else {

                                    return null;//-> Os menbros da apresentação ja estão alocado nesta hora de começo e fim 
                                
                                }
                                
                            
                            }else {

                               return null;//-> Um dos menbros não existe 

                            }


                        }else{
                            // Salvar a apresentação caso o tcc não tenha menbros 
                            Boolean isLock = agendaApresentacao.getIsLock();
                            Tcc tcc = this.tccRepository.findById(aB.getIdTcc()).get();
                            
                            if(isLock == false && apresentacaoDentroDaAgenda == null ){

                                agendaApresentacao.setApresentacao(aB);
                                agendaApresentacao.setIsLock(true);
                                
                                agendaRepository.save(agendaApresentacao);
                                
                                aB.setTcc(tcc);
                                return repository.save(aB);

                            }else {

                                return null;

                            }

                        }

                    }else{

                        return null;//tcc ou agenda informada não existe 

                    }

                } else {
                    
                    return null;//->tcc nulo

                }

            
            } else {

                return null;//-> id não passado 
           
            }

        }

        return null;//-> apresentação e banca é nula
    }

    @Override
    public ApresentationBanca updateApresentationBanca(String id,ApresentationBanca apresentationBanca) {
       
        if( id == null){
            return null;
        }

        ApresentationBanca repoApresentacao = this.getApresentationBanca(id);
        
        if(repoApresentacao == null ){
            return null;
        }

        String agendaIdRepo = apresentationBanca.getIdAgenda();
        String tccIdRepo = apresentationBanca.getIdTcc();


        Agenda agendaRepo = this.agendaRepository.findById(agendaIdRepo).orElse(null);

        Tcc tcc = this.tccRepository.findById(tccIdRepo).orElse(null);
        Tcc tccRepo =this.tccRepository.findById(repoApresentacao.getIdTcc()).orElse(null);

        Boolean existsConlictTcc = this.repository.countConflictTccs(tccIdRepo) > 0;

        if( apresentationBanca.getMember1() != null || apresentationBanca.getMember2() != null){
            
            String memberIdOneRepo = apresentationBanca.getMember1().getId();
            String memberIdTwoRepo = apresentationBanca.getMember2().getId();

            Boolean isLockedMemberOneAndMemberTwo = repository.countConflictingApresentationsByData( agendaRepo.getDate() ,agendaRepo.getHorasComeco() ,agendaRepo.getHorasFim() , memberIdOneRepo ,memberIdTwoRepo) > 0;

            ApresentationBanca apresentacaoInData = agendaRepo.getApresentacao();

            if(existsConlictTcc == false || isLockedMemberOneAndMemberTwo == false){

                Boolean isLock = agendaRepo.getIsLock();
                

                if(isLock == false && apresentacaoInData == null ){

                    if( agendaIdRepo == null){
                        agendaRepo.setApresentacao(repoApresentacao);
                        apresentationBanca.setIdAgenda(agendaIdRepo);
                    } else {
                        agendaRepo.setApresentacao(apresentationBanca);
                    }
                    
                    if( tccIdRepo == null){
                        apresentationBanca.setTcc(tccRepo);
                    }else{
                        apresentationBanca.setTcc(tcc);
                    }

                    agendaRepo.setIsLock(true);
                    
                    agendaRepository.save(agendaRepo);
                    
                    return repository.save(apresentationBanca);

                }else {

                    return null;

                }

            } else {

                Boolean isLock = agendaRepo.getIsLock();
                
                if(!existsConlictTcc){
                    return null;
                }
                
                if(isLock == false && apresentacaoInData == null ){

                    if( agendaIdRepo == null){
                        agendaRepo.setApresentacao(repoApresentacao);
                        apresentationBanca.setIdAgenda(agendaIdRepo);
                    } else {
                        agendaRepo.setApresentacao(apresentationBanca);
                    }
                    
                    if( tccIdRepo == null){
                        apresentationBanca.setTcc(tccRepo);
                    }else{
                        apresentationBanca.setTcc(tcc);
                    }

                    agendaRepo.setIsLock(true);
                    
                    agendaRepository.save(agendaRepo);
                    
                    apresentationBanca.setTcc(tcc);
                    return repository.save(apresentationBanca);

                }else {

                    return null;

                }

            }
            
        } 

        return null;
    }

    @Override
    public ApresentationBanca deleteApresentationBanca(String id) {
       
        if(id != null){

            ApresentationBanca apresentationBancaRepo = this.repository.existsById(id)==true? repository.findById(id).get() : null;

            if(apresentationBancaRepo != null){

                this.repository.deleteById(id);
                return apresentationBancaRepo;

            }

            return null;

        }

        return null;

    }

    @Override
    public ApresentationBanca getApresentationBanca(String id) {

        if(id != null || id != " "){

            return this.repository.existsById(id)==true? repository.findById(id).get() : null;

        }

        return null;

    }

    @Override
    public List<ApresentationBanca> getAllApresentationBanca() {
       
        Long listApresentacoes = this.repository.count();

        if(listApresentacoes > 0){

            return this.repository.findAll();

        }

        return null;
   
    }
 
}
