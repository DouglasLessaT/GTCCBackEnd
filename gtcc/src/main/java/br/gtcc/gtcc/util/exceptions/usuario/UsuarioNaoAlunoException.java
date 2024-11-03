package br.gtcc.gtcc.util.exceptions.usuario;

public class UsuarioNaoAlunoException extends RuntimeException {
    public UsuarioNaoAlunoException(){
        super();
    }

    public UsuarioNaoAlunoException(String msg){
        super(msg);
    }

    public UsuarioNaoAlunoException(String msg, Throwable causa){
        super(msg, causa);
    }

}
