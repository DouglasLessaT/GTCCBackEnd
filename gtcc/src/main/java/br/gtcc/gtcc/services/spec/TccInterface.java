package br.gtcc.gtcc.services.spec;

import java.util.List;
// import java.util.Optional;


public interface TccInterface<T, E> {
    public T createTcc(T tcc);

    public T updateTCC(T tcc);

    public T deleteTCC(E tcc);

    public List<T> getAllTCC();

    public T getTCC(E tcc);

    // Optional<Tcc> createTcc(Tcc tcc);

    // Optional<Tcc> deleteTCC(String id);

    // Optional<Tcc> getTCC(String title);
}
