package br.gtcc.gtcc.services.spec;

import java.util.List;

import br.gtcc.gtcc.model.nitriteid.ApresentationBanca;


public interface ApresentationBancaInterface {
 
    
   public  ApresentationBanca createApresentationBanca (ApresentationBanca apresentationBanca);
   
   public  ApresentationBanca updateApresentationBanca ( ApresentationBanca apresentationBanca);
   
   public  ApresentationBanca deleteApresentationBanca ( ApresentationBanca apresentationBanca);
   
   public List< ApresentationBanca> getAllApresentationBanca ();
   
   public  ApresentationBanca getApresentationBanca ( ApresentationBanca apresentationBanca);

}
