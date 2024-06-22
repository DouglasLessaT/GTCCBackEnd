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
                //Verificar se o id do tccInNewTcc é válido e existe no banco, caso não retorna null -> 3
                if(aB.getIdTcc() != null ){


                    Boolean existsTcc  = this.tccRepository.existsById(aB.getIdTcc());
                    //Verificar se a data mencionanda existe -> 4

                    Agenda agendaApresentacao = this.agendaRepository.findById(aB.getIdAgenda()).orElse(null);
                    ApresentationBanca apresentacaoDentroDaAgenda = agendaApresentacao.getApresentacao();

                    if(existsTcc == true && agendaApresentacao.getDate() != null){

                        
                        Boolean existsConlictTcc = this.repository.countConflictTccs(aB.getIdTcc()) > 0;
                        
                        if(existsConlictTcc == true){

                            throw new IllegalArgumentException("O tcc já esta em outra apresentação");

                        }

                        if( aB.getMember1() != null || aB.getMember1() != null ){
                            

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
                                        
                                        Users usersNull = null;

                                        if(aB.getMember1() == null){
                                            aB.setMember1(usersNull);
                                        }
                                        
                                        if(aB.getMember2() == null){
                                            aB.setMember2(usersNull);
                                        }

                                        agendaRepository.save(agendaApresentacao);
                                        
                                        aB.setTcc(tcc);
                                        return repository.save(aB);
    
                                    }else {

                                        throw new IllegalArgumentException("A apresentação já esta alocada");

                                    }
                                
                                } else {

                                    throw new IllegalArgumentException("Os membros da apresentação ja estão alocado nesta hora de começo e fim ");
                                
                                }
                                
                            
                            }else {

                                throw new IllegalArgumentException("Um dos membros não existem");

                            }


                        }else{
                            
                            Boolean isLock = agendaApresentacao.getIsLock();
                            Tcc tcc = this.tccRepository.findById(aB.getIdTcc()).get();
                            
                            if(isLock == false && apresentacaoDentroDaAgenda == null ){

                                agendaApresentacao.setApresentacao(aB);
                                agendaApresentacao.setIsLock(true);
                                
                                agendaRepository.save(agendaApresentacao);
                                
                                aB.setTcc(tcc);
                                return repository.save(aB);

                            }else {

                                throw new IllegalArgumentException("A apresentação já esta alocada");

                            }

                        }

                    }else{

                        throw new IllegalArgumentException("O tcc não existe e a data da agenda é nulo ");
                    }

                } else {
                    
                    throw new IllegalArgumentException("O id da apresentação é nulo ");
                }

            
            } else {
       
                throw new IllegalArgumentException("O id da apresentação já existe");
           
            }

        }

        throw new IllegalArgumentException("A apresentação é nula");
    }

    @SuppressWarnings("unused")
    @Override
    public ApresentationBanca updateApresentationBanca(String id,ApresentationBanca apresentationBanca) {
       
        if( id == null){
            throw new IllegalArgumentException("A apresentação é nula");
        }

        ApresentationBanca repoApresentacao = this.getApresentationBanca(id);
        
        if(repoApresentacao == null ){

            throw new IllegalArgumentException("Apresentação não informada");
        
        }

        String newAgendaId = apresentationBanca.getIdAgenda();
        String newTccId = apresentationBanca.getIdTcc();

        Agenda newAgendaRepo = this.agendaRepository.findById(newAgendaId).orElse(null);

        Tcc newTcc = this.tccRepository.findById(newTccId).orElse(null);
        Tcc oldTccRepo  =this.tccRepository.findById(repoApresentacao.getIdTcc()).orElse(null);

        Boolean existsConlictTcc = this.repository.countConflictTccs(newTccId) > 1;
        ApresentationBanca apresentacaoDentroDaAgenda = newAgendaRepo.getApresentacao();     

        if( apresentationBanca.getMember1() != null || apresentationBanca.getMember2() != null){
            
            String newMemberIdOneRepo = apresentationBanca.getMember1().getId();
            String newMemberIdTwoRepo = apresentationBanca.getMember2().getId();

            Boolean isLockedMemberOneAndMemberTwo = repository.countConflictingApresentationsByData( newAgendaRepo.getDate() ,newAgendaRepo.getHorasComeco() ,newAgendaRepo.getHorasFim() , newMemberIdOneRepo ,newMemberIdTwoRepo) > 1;

            if(existsConlictTcc == false && isLockedMemberOneAndMemberTwo == false){
                
                Boolean isEqualsAgendas = newAgendaId.equals(repoApresentacao.getIdAgenda());

                if( isEqualsAgendas==false ){

                    if( newAgendaId == null){
                        newAgendaRepo.setApresentacao(repoApresentacao);
                        apresentationBanca.setIdAgenda(newAgendaId);
                    } else {
                    
                        Boolean isLock = newAgendaRepo.getIsLock();
                        if(isLock == false && apresentacaoDentroDaAgenda == null){
                            
                            Agenda oldAgenda = this.agendaRepository.findById(repoApresentacao.getIdAgenda()).orElse(null);
                            oldAgenda.setIsLock(false);
                            oldAgenda.setApresentacao(null);
                            
                            this.agendaRepository.save(oldAgenda);

                            newAgendaRepo.setApresentacao(apresentationBanca);
                            newAgendaRepo.setIsLock(true);
                            apresentationBanca.setIdAgenda(newAgendaId);
                        }
                    }
                    
                }

                Boolean isEqualsTcc = newTccId.equals(repoApresentacao.getIdTcc());
                   
                if(isEqualsTcc == false){
                    
                    if( newTccId == null){
                        apresentationBanca.setIdTcc(oldTccRepo.getId());
                        apresentationBanca.setTcc(oldTccRepo);
                    }else{
    
                        if(isEqualsTcc == false){
                            apresentationBanca.setIdTcc(newTccId);
                            apresentationBanca.setTcc(newTcc);
                        }
                    }

                }  else {
                    apresentationBanca.setIdTcc(oldTccRepo.getId());
                    apresentationBanca.setTcc(oldTccRepo);
                }


                Users usersNull = null;

                if(apresentationBanca.getMember1() == null){
                    apresentationBanca.setMember1(usersNull);
                }
                
                if(apresentationBanca.getMember2() == null){
                    apresentationBanca.setMember2(usersNull);
                }

                this.tccRepository.save(newTcc);
                this.agendaRepository.save(newAgendaRepo);
                

                return repository.save(apresentationBanca);

            } else {

                throw new IllegalArgumentException(" Existe conflito entre as datas ou o tcc");
               
            }
            
        } else {

            Boolean isLock = newAgendaRepo.getIsLock();
                
            if(!existsConlictTcc){

                throw new IllegalArgumentException("Existe conflito no tcc informado, ele esta em outra apresentação");
            
            }
            
            if(isLock == false && apresentacaoDentroDaAgenda == null ){

                Boolean isEqualsAgendas = newAgendaId.equals(repoApresentacao.getIdAgenda());

                if( isEqualsAgendas==false ){

                    if( newAgendaId == null){
                        newAgendaRepo.setApresentacao(repoApresentacao);
                        apresentationBanca.setIdAgenda(newAgendaId);
                    } else {
                
                        Agenda oldAgenda = this.agendaRepository.findById(repoApresentacao.getIdAgenda()).orElse(null);
                        oldAgenda.setIsLock(false);
                        oldAgenda.setApresentacao(null);
                        
                        this.agendaRepository.save(oldAgenda);

                        newAgendaRepo.setApresentacao(apresentationBanca);
                        newAgendaRepo.setIsLock(true);
                        apresentationBanca.setIdAgenda(newAgendaId);
                    
                    }
                    
                }

                Boolean isEqualsTcc = newTccId.equals(repoApresentacao.getIdTcc());
                   
                if(isEqualsTcc == false){
                    
                    if( newTccId == null){
                        apresentationBanca.setIdTcc(oldTccRepo.getId());
                        apresentationBanca.setTcc(oldTccRepo);
                    }else{
    
                        if(isEqualsTcc == false){
                            apresentationBanca.setIdTcc(newTccId);
                            apresentationBanca.setTcc(newTcc);
                        }
                    }

                }  else {
                    apresentationBanca.setIdTcc(oldTccRepo.getId());
                    apresentationBanca.setTcc(oldTccRepo);
                }
                
                agendaRepository.save(newAgendaRepo);
                
                apresentationBanca.setTcc(newTcc);
                return repository.save(apresentationBanca);

            }else {

                throw new IllegalArgumentException("A apresentação já esta alocada");

            }

        }

    }

    @Override
    public ApresentationBanca deleteApresentationBanca(String id) {
       
        if(id != null){

            ApresentationBanca apresentationBancaRepo = this.repository.existsById(id)==true? repository.findById(id).get() : null;

            if(apresentationBancaRepo != null){

                this.repository.deleteById(id);
                return apresentationBancaRepo;

            }

            throw new IllegalArgumentException("A apresentação não existe");
        }

        throw new IllegalArgumentException("ID nulo");

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
