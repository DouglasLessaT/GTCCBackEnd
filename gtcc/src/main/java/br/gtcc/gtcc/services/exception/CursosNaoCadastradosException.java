package br.gtcc.gtcc.services.exception;

public class CursosNaoCadastradosException extends RuntimeException{

    public CursosNaoCadastradosException(){
        super();
    }

    public CursosNaoCadastradosException(String msg){
        super(msg);
    }

    public CursosNaoCadastradosException(String msg, Throwable causa){
        super(msg, causa);
    }
    
}
