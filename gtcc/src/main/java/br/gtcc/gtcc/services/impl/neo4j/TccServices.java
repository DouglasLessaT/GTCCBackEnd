package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import br.gtcc.gtcc.services.spec.TccInterface;

@Service
public class TccServices implements TccInterface<Tcc, String> {

    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public UsersRepository usersRepository;

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public Tcc createTcc(Tcc tcc) {
        if (tcc != null && tcc.getId() == null) {
            return tccRepository.save(tcc);
        } 
        return null;
    }

    @Override
    public Tcc updateTCC(Tcc tcc) {
        if (tcc != null && tcc.getId() != null) {

            Tcc existingTcc = getTCC(tcc.getId());
            
            if (existingTcc != null) {
                existingTcc.setTitle(tcc.getTitle());
                existingTcc.setTheme(tcc.getTheme());
                existingTcc.setCurse(tcc.getCurse());
                existingTcc.setDateOfApresentation(tcc.getDateOfApresentation());
                
                return tccRepository.save(existingTcc);
            } else {
                throw new IllegalArgumentException("Tcc não encontrado para o ID fornecido: " + tcc.getId());
            }
        } else {
            throw new IllegalArgumentException("O Tcc fornecido é inválido ou não possui um ID.");
        }
    }

    @Override
    public Tcc deleteTCC(String id) {
        Tcc delTcc = this.getTCC(id);
        if (delTcc != null) {
            tccRepository.delete(delTcc);
        } else {
            throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
        }
        return null;
    }

    @Override
    public List<Tcc> getAllTCC() {
        return tccRepository.findAll();
    }

    @Override
    public Tcc getTCC(String id) {
        if(id != null || id != " "){

            return this.tccRepository.existsById(id)==true? tccRepository.findById(id).get() : null;
        }
        return null;
    }

    
}