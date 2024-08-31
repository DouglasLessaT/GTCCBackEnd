package br.gtcc.gtcc.services.impl.mysql;

import br.gtcc.gtcc.model.mysql.TipoDocente;
import br.gtcc.gtcc.model.mysql.repository.TipoDocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDocenteService {

    @Autowired
    private TipoDocenteRepository tipoDocenteRepository;

    public List<TipoDocente> findAll() {
        return tipoDocenteRepository.findAll();
    }

    public Optional<TipoDocente> findById(Long id) {
        return tipoDocenteRepository.findById(id);
    }

    public TipoDocente save(TipoDocente tipoDocente) {
        return tipoDocenteRepository.save(tipoDocente);
    }

    public void deleteById(Long id) {
        tipoDocenteRepository.deleteById(id);
    }

    // Novo método para verificar a existência de TipoDocente por título
    public Optional<TipoDocente> findByTitulo(String titulo) {
        return tipoDocenteRepository.findByTitulo(titulo);
    }
}
