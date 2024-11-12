package br.gtcc.gtcc.util.exceptions.apresentacao;

public class ApresentacaoInvalidaException extends RuntimeException {
    
    public ApresentacaoInvalidaException(){
        super();
    }

    public ApresentacaoInvalidaException(String msg){
        super(msg);
    }

    public ApresentacaoInvalidaException(String msg, Throwable causa){
        super(msg, causa);
    }

}
