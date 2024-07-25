package br.gtcc.gtcc.util.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.repository.ApresentationBancaRepository;

@Component
public class ApresentacaoUtil {
    
    @Autowired
    public ApresentationBancaRepository repository;

    public ApresentationBanca salvar(ApresentationBanca apr){
        return this.repository.save(apr);
    }

    public void delete(String id){
        this.repository.deleteById(id);
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


}
