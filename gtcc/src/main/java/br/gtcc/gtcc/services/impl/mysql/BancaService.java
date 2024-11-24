package br.gtcc.gtcc.services.impl.mysql;


import br.gtcc.gtcc.model.mysql.Banca;
import br.gtcc.gtcc.model.mysql.DTO.BancaRequest;
import br.gtcc.gtcc.model.mysql.DocenteBanca;
import br.gtcc.gtcc.model.mysql.repository.BancaRepository;
import br.gtcc.gtcc.model.mysql.repository.DocenteBancaRepository;
import br.gtcc.gtcc.model.mysql.repository.TccRepository;
import br.gtcc.gtcc.services.exception.BancaNaoExisteException;
import br.gtcc.gtcc.services.exception.DocenteBancaNaoExisteException;
import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.tcc.TccNaoExisteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BancaService {

    private final BancaRepository bancaRepository;
    private final TccRepository tccRepository;
    private final DocenteBancaRepository docenteBancaRepository;

    public Banca create(BancaRequest bancaRequest) {

        if(bancaRequest.getIdTcc() == null ) {
            throw new IdInvalidoException("O id do banca informado é inválido");
        }
        if(bancaRequest.getIdDocentesBanca() == null){
            throw new IdInvalidoException("A lista de id dos Docente Banca informado é inválida");
        }

        var banca = new Banca();

        if(!bancaRequest.getIdDocentesBanca().isEmpty()){
            var docentesBanca = bancaRequest.getIdDocentesBanca()
                    .stream()
                    .map(
                            (idDocente) -> docenteBancaRepository.findById(idDocente)
                                    .orElseThrow(
                                            ()->new DocenteBancaNaoExisteException("Docente Banca não encontrado")
                                    )
                    ).collect(Collectors.toList());
            banca.setDocentes(docentesBanca);

        }

        var tcc = tccRepository.findById(bancaRequest.getIdTcc())
                .orElseThrow(()->new TccNaoExisteException("Id do tcc informado não existe"));


        banca.setTcc(tcc);
        banca.setAtivo(bancaRequest.getAtivo());

        return bancaRepository.save(banca);

    }

    public Banca update(BancaRequest bancaRequest, Long id) {

        if( id == null || bancaRequest.getIdTcc() == null ) {
            throw new IdInvalidoException("O id do banca informado é inválido");
        }
        if(bancaRequest.getIdDocentesBanca() == null){
            throw new IdInvalidoException("A lista de id dos Docente Banca informado é inválida");
        }

        var banca = bancaRepository.findById(id)
                .orElseThrow(
                        ()->new BancaNaoExisteException("A banca não existe")
                );
        if(!bancaRequest.getIdDocentesBanca().isEmpty()){
            var docentesBanca = bancaRequest.getIdDocentesBanca()
                    .stream()
                    .map(
                            (idDocente) -> docenteBancaRepository.findById(idDocente)
                                    .orElseThrow(
                                            ()->new DocenteBancaNaoExisteException("Docente Banca não encontrado")
                                    )
                    ).collect(Collectors.toList());
            banca.setDocentes(docentesBanca);
        }

        var tcc = tccRepository.findById(bancaRequest.getIdTcc())
                        .orElseThrow(()->new TccNaoExisteException("Tcc informado não existe"));

        banca.setAtivo(bancaRequest.getAtivo());
        banca.setTcc(tcc);

        return bancaRepository.save(banca);
    }

    public Banca delete(Long id) {
        if(id == null)
            throw new IdInvalidoException("O id do banca informado é inválido");
        Banca bancaDeleted = getById(id);
        bancaRepository.delete(bancaDeleted);
        return bancaDeleted;
    }

    public List<Banca> getAll(){
        return bancaRepository.findAll();
    }

    public Banca getById(Long id) {
        if (id == null) {
            throw new IdInvalidoException("O id do banca informado é inválido");
        }
        return bancaRepository.findById(id).orElseThrow(() -> new BancaNaoExisteException("Banca nao existe"));
    }

}
