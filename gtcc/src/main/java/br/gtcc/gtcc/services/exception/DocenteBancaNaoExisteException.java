package br.gtcc.gtcc.services.exception;

public class DocenteBancaNaoExisteException extends RuntimeException {

    public DocenteBancaNaoExisteException(){
        super();
    }

    public DocenteBancaNaoExisteException(String msg){
        super(msg);
    }

    public DocenteBancaNaoExisteException(String msg, Throwable causa){
        super(msg, causa);
    }

}
