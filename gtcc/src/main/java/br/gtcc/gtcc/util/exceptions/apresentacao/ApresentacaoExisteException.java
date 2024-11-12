package br.gtcc.gtcc.util.exceptions.apresentacao;

public class ApresentacaoExisteException extends RuntimeException {
    public ApresentacaoExisteException(){
        super();
    }

    public ApresentacaoExisteException(String msg){
        super(msg);
    }

    public ApresentacaoExisteException(String msg, Throwable causa){
        super(msg, causa);
    }
}
