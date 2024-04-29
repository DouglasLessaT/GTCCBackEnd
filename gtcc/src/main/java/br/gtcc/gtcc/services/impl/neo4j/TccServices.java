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
    }

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
    public Tcc getTCC(String title) {
        return tccRepository.findByTitle(title);
    }

    @Override
    public Tcc updateTCC(Tcc tcc) {
        if (tcc != null && tcc.getId() != null) {
            Tcc existingTcc = getById(tcc.getId());
            if (existingTcc != null) {
                existingTcc.setTitle(tcc.getTitle());
                existingTcc.setTheme(tcc.getTheme());
                existingTcc.setCurse(tcc.getCurse());
                existingTcc.setDateOfApresentation(tcc.getDateOfApresentation());
                // Aqui você pode adicionar mais campos a serem atualizados, se necessário
                
                return tccRepository.save(existingTcc);
            } else {
                throw new IllegalArgumentException("Tcc não encontrado para o ID fornecido: " + tcc.getId());
            }
        } else {
            throw new IllegalArgumentException("O Tcc fornecido é inválido ou não possui um ID.");
        }
    }
}