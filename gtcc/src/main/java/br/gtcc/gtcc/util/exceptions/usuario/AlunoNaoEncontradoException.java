package br.gtcc.gtcc.util.exceptions.usuario;

public class AlunoNaoEncontradoException extends RuntimeException{
    public AlunoNaoEncontradoException(){
        super();
    }

    public AlunoNaoEncontradoException(String msg){
        super(msg);
    }

    public AlunoNaoEncontradoException(String msg, Throwable causa){
        super(msg, causa);
    }    
}


