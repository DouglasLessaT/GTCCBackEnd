package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface TccInterface<T, E> {
    public T createTcc(T tcc);
   
   public T updateTCC(T tcc , E id);
   
   public T deleteTCC(E tcc);
   
   public List<T> getAllTCC();
   
   public T getTCC(E tcc);

   public T getTCCByTitle(String title);

   public List<T> getTccSemApresentacao();

   public T adicionarOrientadorEmTcc(E idTcc ,E idAluno);

   public T adicionarAlunoEmTcc(E idTcc ,E idAluno );

}
