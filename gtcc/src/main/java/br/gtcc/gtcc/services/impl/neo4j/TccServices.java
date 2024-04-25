package br.gtcc.gtcc.services.impl.neo4j;

import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TccServices implements TccInterface<Tcc, String> {

    @Autowired
    public final TccRepository tccRepository;

    public TccServices(TccRepository tccRepository) {
        this.tccRepository = tccRepository;
    }

    private Tcc getById(String id) {
        return tccRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Moradia não encontrada para o ID fornecido: " + id));

    @Override
    public Tcc createTcc(Tcc tcc) {
        if (tcc != null && tcc.getId() == null) {
            return tccRepository.save(tcc);
        } else {
            throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
        }
    }

    @Override
    public Tcc deleteTCC(String id) {
        Tcc delTcc = this.getById(id);
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tcc updateTCC(Tcc tcc) {
        // TODO Auto-generated method stub
        return null;
    }
}