package br.gtcc.gtcc.services.exception;

public class LocalizacaoNaoExisteException extends RuntimeException {

    public LocalizacaoNaoExisteException(){
        super();
    }

    public LocalizacaoNaoExisteException(String msg){
        super(msg);
    }

    public LocalizacaoNaoExisteException(String msg, Throwable causa){
        super(msg, causa);
    }
}
