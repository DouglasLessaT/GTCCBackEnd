package br.gtcc.gtcc.services.impl.mysql;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.mysql.Localizacao;
import br.gtcc.gtcc.model.mysql.repository.LocalizacaoRepository;
import br.gtcc.gtcc.services.spec.LocalizacaoInterface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalizacaoService implements LocalizacaoInterface {

    private final LocalizacaoRepository localizacaoRepository;

    @Override
    public Localizacao salvar(Localizacao localizacao) {
        boolean conflito = localizacaoRepository.existsByPredioAndSalaAndAndar(
            localizacao.getPredio(), 
            localizacao.getSala(), 
            localizacao.getAndar()
        );
        if (conflito) {
            throw new RuntimeException("Conflito: Já existe uma localização com essas especificações.");
        }
        return localizacaoRepository.save(localizacao);
    }

    @Override
    public void deletar(Long id) {
        if (!localizacaoRepository.existsById(id)) {
            throw new RuntimeException("Erro: Localização não encontrada para o ID: " + id);
        }
        localizacaoRepository.deleteById(id);
    }

    @Override
    public List<Localizacao> listar() {
        return localizacaoRepository.findAll();
    }

    @Override
    public Localizacao buscarPorId(Long id) {
       Optional<Localizacao> localizacao = localizacaoRepository.findById(id);
        return localizacao.orElseThrow(() -> new RuntimeException("Erro: Localização não encontrada para o ID: " + id));
    }

    @Override
    public Localizacao updateLocalizacao(Long id, Localizacao localizacao) {
       if (id == null || localizacao == null) {
            throw new IllegalArgumentException("Erro: ID e Localização são obrigatórios.");
        }
         if (!localizacaoRepository.existsById(id)) {
            throw new RuntimeException("Erro: Localização não encontrada para o ID: " + id);
        }
       boolean conflito = localizacaoRepository.existsByPredioAndSalaAndAndar(
            localizacao.getPredio(), 
            localizacao.getSala(), 
            localizacao.getAndar()
        );
        if (conflito) {
            throw new RuntimeException("Conflito: Já existe uma localização cadastrada com essas especificações.");
        }
       localizacao.setId(id);
        return localizacaoRepository.save(localizacao);
    }
}
