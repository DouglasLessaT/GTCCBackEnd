package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface ApresentacaoInterface<T , E > {
 
    
   public  T createApresentacao ( T apresentacao);
   
   public  T updateApresentacao ( E id ,T apresentacao);
   
   public  T deleteApresentacao ( E id);
   
   public  T getApresentacao ( E id);
   
   public List< T > getAllApresentacao ();

}
