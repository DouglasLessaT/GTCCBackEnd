package br.gtcc.gtcc.services.spec;

import java.util.List;

public interface ApresentationBancaInterface<T , E > {
 
    
   public  T createApresentationBanca ( T apresentationBanca);
   
   public  T updateApresentationBanca ( T apresentationBanca);
   
   public  T deleteApresentationBanca ( E apresentationBanca);
   
   public  T getApresentationBanca ( E apresentationBanca);
   
   public List< T > getAllApresentationBanca ();

}
