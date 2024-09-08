package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface DocenteBancaInterface<T ,E> {
    
    public T createDocenteBanca(T docenteBanca);

    public T updateDocenteBanca(E id ,T docenteBanca);

    public T deleteUsers(E id);

    public List<T> getAllDocenteBanca();

    public T getDocenteBanca(E id);

}
