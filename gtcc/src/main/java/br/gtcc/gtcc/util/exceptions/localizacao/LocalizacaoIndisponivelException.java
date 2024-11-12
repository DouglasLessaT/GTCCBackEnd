package br.gtcc.gtcc.util.exceptions.localizacao;

public class LocalizacaoIndisponivelException extends RuntimeException{
    public LocalizacaoIndisponivelException(){
        super();
    }

    public LocalizacaoIndisponivelException(String msg){
        super(msg);
    }

    public LocalizacaoIndisponivelException(String msg, Throwable causa){
        super(msg, causa);
    }
}
