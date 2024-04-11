package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TccServices implements TccInterface<Tcc, String>{

    @Autowired
    public TccRepository repository;

    @Override
    public Tcc createTcc(Tcc tcc) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tcc deleteTCC(String id) { 
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tcc> getAllTCC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tcc getTCC(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tcc updateTCC(Tcc tcc) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
