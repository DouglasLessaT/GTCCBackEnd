package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface ApresentationBancaInterface<T , E > {
 
    
   public  T createApresentationBanca ( T apresentationBanca);
   
   public  T updateApresentationBanca ( E id ,T apresentationBanca);
   
   public  T deleteApresentationBanca ( E id);
   
   public  T getApresentationBanca ( E id);
   
   public List< T > getAllApresentationBanca ();

}
