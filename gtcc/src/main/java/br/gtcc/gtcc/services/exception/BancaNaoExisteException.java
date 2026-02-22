package br.gtcc.gtcc.services.exception;

public class BancaNaoExisteException extends RuntimeException {

    public BancaNaoExisteException(){
        super();
    }

    public BancaNaoExisteException(String msg){
        super(msg);
    }

    public BancaNaoExisteException(String msg, Throwable causa){
        super(msg, causa);
    }

}
