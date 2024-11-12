package br.gtcc.gtcc.util.exceptions.apresentacao;

public class ApresentacaoNaoException extends RuntimeException {

    public ApresentacaoNaoException(){
        super();
    }

    public ApresentacaoNaoException(String msg){
        super(msg);
    }

    public ApresentacaoNaoException(String msg, Throwable causa){
        super(msg, causa);
    }


}