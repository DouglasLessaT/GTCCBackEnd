package br.gtcc.gtcc.model.mysql;

public enum DocenteEnum {
    ORIENTADOR("Orientador"),
    AVALIADOR_INTERNO("Avaliador Interno"),
    AVALIADOR_EXTERNO("Avaliador Externo"),
    PROFESSOR_ADJUNTO("Professor Adjunto");

    private final String descricao;

    DocenteEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
