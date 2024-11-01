package br.gtcc.gtcc.util.exceptions.usuario;

public class OrientadorNaoEncontradoException extends RuntimeException{
    public OrientadorNaoEncontradoException(){
        super();
    }

    public OrientadorNaoEncontradoException(String msg){
        super(msg);
    }

    public OrientadorNaoEncontradoException(String msg, Throwable causa){
        super(msg, causa);
    }    
}
