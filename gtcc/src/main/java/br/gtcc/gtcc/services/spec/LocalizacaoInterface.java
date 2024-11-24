package br.gtcc.gtcc.services.spec;

import java.util.List;

import br.gtcc.gtcc.model.mysql.Localizacao;

public interface LocalizacaoInterface {
    Localizacao salvar(Localizacao localizacao);

    void deletar(Long id);

    List<Localizacao> listar();

    Localizacao buscarPorId(Long id);

    Localizacao updateLocalizacao(Long id, Localizacao localizacao);
}
