package br.gtcc.gtcc.util.exceptions.usuario;

public class AlunoTemTccException extends RuntimeException {

    public  AlunoTemTccException(){
        super();
    }

    public  AlunoTemTccException(String msg){
        super(msg);
    }

    public  AlunoTemTccException(String msg, Throwable causa){
        super(msg, causa);
    }

}
