package br.gtcc.gtcc.services.spec;

import java.util.List;
// import java.util.Optional;


public interface TccInterface<T, E> {
    public T createTcc(T tcc);
   
   public T updateTCC(T tcc , E id);
   
   public T deleteTCC(E tcc);
   
   public List<T> getAllTCC();
   
   public T getTCC(E tcc);
}
