package br.gtcc.gtcc.util.exceptions.tcc;

public class TccJaTemApresentacaoException extends RuntimeException{
    public TccJaTemApresentacaoException(){
        super();
    }

    public TccJaTemApresentacaoException(String msg){
        super(msg);
    }

    public TccJaTemApresentacaoException(String msg, Throwable causa){
        super(msg, causa);
    }
}