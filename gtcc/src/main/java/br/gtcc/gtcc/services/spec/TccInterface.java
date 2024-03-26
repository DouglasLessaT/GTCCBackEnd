package br.gtcc.gtcc.services.spec;

import java.util.List;

import br.gtcc.gtcc.model.nitriteid.Tcc;


public interface TccInterface<T, E> {
    public T createTcc(T tcc);
   
   public T updateTCC(T tcc);
   
   public T deleteTCC(E tcc);
   
   public List<T> getAllTCC();
   
   public T getTCC(E tcc);
}
