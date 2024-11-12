package br.gtcc.gtcc.util.exceptions.apresentacao;

public class NaoExisteApresentacoesCadastradasException extends RuntimeException{
    public NaoExisteApresentacoesCadastradasException(){
        super();
    }

    public NaoExisteApresentacoesCadastradasException(String msg){
        super(msg);
    }

    public NaoExisteApresentacoesCadastradasException(String msg, Throwable causa){
        super(msg, causa);
    }
}
