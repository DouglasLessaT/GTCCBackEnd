package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.repository.ApresentationBancaRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApresentationBancaServices implements ApresentationBancaInterface<ApresentationBanca, String>{

    @Autowired
    public ApresentationBancaRepository repository;

    @Override
    public ApresentationBanca createApresentationBanca(ApresentationBanca aB) {
        //Verificar se a apresentação é nula caso sim retornar null  -> 1
        
        if(aB != null ){
            if(aB.getId() != null){

                ApresentationBanca apreentacao = this.getApresentationBanca(aB.getId());
                 
                if(apreentacao != null){

                } else {

                    return null;
                
                }

            }

        }
        //Verificar se a apresentação existe caso sim retornar null -> 2
        //Verificar se o id do tcc é válido e existe no banco, caso não retorna null -> 3
        //Verificar se a data mencionanda existe -> 4
        //Verificar se o menbro um e dois ja existe. -> 5
        //Verificar se os menbros 1 e 2 já estão alocados na data entregue pelo cliente, caso não esteja continuar fluxo ->6
        //Caso estejam alocados verificar se a hora entregue já esta alocada para os dois menbros -> 7
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
