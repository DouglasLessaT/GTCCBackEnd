package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.Data;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.ApresentationBancaRepository;
import br.gtcc.gtcc.model.neo4j.repository.DataRepository;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApresentationBancaServices implements ApresentationBancaInterface<ApresentationBanca, String>{

    @Autowired
    public ApresentationBancaRepository repository;

    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public DataRepository dataRepository;

    @Autowired
    public UsersRepository usersRepository;


    @Override
    public ApresentationBanca createApresentationBanca(ApresentationBanca aB) {
        //Verificar se a apresentação é nula caso sim retornar null  -> 1
        
        if(aB != null ){

            if(aB.getId() != null){

                //Verificar se a apresentação existe caso sim retornar null -> 2
                ApresentationBanca apresentacao = this.getApresentationBanca(aB.getId());
                 
                if(apresentacao != null){
                    
                    //Verificar se o id do tcc é válido e existe no banco, caso não retorna null -> 3
                    if(aB.getIdTcc() != null ){

                        Boolean existsTcc  = this.tccRepository.existsById(aB.getIdTcc());
                        
                        if(existsTcc == true && aB.getDate().getId() != null){
                            
                            //Verificar se a data mencionanda existe -> 4
                            Boolean existsData = this.dataRepository.existsById(aB.getDate().getId());
                            
                            if(existsData == true){
                                                        
                                if(aB.getMember1().getId() != null && aB.getMember1() != null ){

                                    //Verificar se os membros um e dois ja existem. -> 5
                                    Boolean existsMenberI = this.usersRepository.existsById(aB.getMember1().getId());
                                    Boolean existsMenberII  = this.usersRepository.existsById(aB.getMember1().getId());;
                                    if(existsMenberI == true && existsMenberII == true){

                                        //Verificar se os menbros 1 e 2 já estão alocados na data entregue pelo cliente(FRONT-END), caso não esteja continuar fluxo ->6
                                        //Buscar a data 
                                        //Obter o array de apresentações dentro da data
                                        //Busca os dois menbros I e II
                                        //Busca se dentro de cada apresentaçção existe os menbros 1 e 2 
                                        Users menberI = this.usersRepository.findById(aB.getMember1().getId()).get();
                                        Users menberII = this.usersRepository.findById(aB.getMember2().getId()).get();
                                        Data data = this.dataRepository.findById(aB.getDate().getId()).get();
                                        ApresentationBanca apresentacaoData = data.getApresentacao();
                                        int count = 0; Boolean isLockedMenberI = false; Boolean isLockedMenberII = false; 

                                        //Caso estejam alocados verificar se a hora entregue já esta alocada para os dois menbros -> 7
                                        

                                   


                                    }else {

                                        return null;

                                    }


                                }else{

                                    return null;

                                }

                            } else {

                                return null;

                            }

                        }else{

                            return null;

                        }

                    } else {
                        
                        return null;

                    }

                } else {

                    return null;
                
                }

            }

        }
      
        //Verificar se existe conflito de horário na apresentação presente, se ja existe um apresentação alocada no mesmo horário ->8

        return null;
    }

    @Override
    public ApresentationBanca updateApresentationBanca(String id,ApresentationBanca apresentationBanca) {
       
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
