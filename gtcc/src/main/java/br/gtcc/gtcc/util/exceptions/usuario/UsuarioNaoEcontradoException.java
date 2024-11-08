package br.gtcc.gtcc.util.exceptions.usuario;

public class UsuarioNaoEcontradoException extends RuntimeException{

    public UsuarioNaoEcontradoException(){
        super();
    }

    public UsuarioNaoEcontradoException(String msg){
        super(msg);
    }

    public UsuarioNaoEcontradoException(String msg, Throwable causa){
        super(msg, causa);
    }

}