package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface CursoInterface<T ,E> {
    
    public T criarCurso(T curso);

    public T buscarCurso(E id);

    public List<T> listaCursos();

    public T alterarCurso(E idCurso , T curso);

    public T deletarCurso(E idCurso);
}
