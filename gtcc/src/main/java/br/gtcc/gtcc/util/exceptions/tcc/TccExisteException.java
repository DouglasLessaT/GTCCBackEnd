package br.gtcc.gtcc.util.exceptions.tcc;

public class TccExisteException extends RuntimeException {
    public TccExisteException(){
        super();
    }

    public TccExisteException(String msg){
        super(msg);
    }

    public TccExisteException(String msg, Throwable causa){
        super(msg, causa);
    }
}
