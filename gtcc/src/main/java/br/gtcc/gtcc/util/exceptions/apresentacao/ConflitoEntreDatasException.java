package br.gtcc.gtcc.util.exceptions.apresentacao;

public class ConflitoEntreDatasException extends RuntimeException {
    public ConflitoEntreDatasException(){
        super();
    }

    public ConflitoEntreDatasException(String msg){
        super(msg);
    }

    public ConflitoEntreDatasException(String msg, Throwable causa){
        super(msg, causa);
    }
}
