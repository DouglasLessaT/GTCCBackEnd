package br.gtcc.gtcc.util.exceptions.cursos;

public class IdInvalidoException extends RuntimeException{
    
    public IdInvalidoException(){
        super();
    }

    public IdInvalidoException(String msg){
        super(msg);
    }

    public IdInvalidoException(String msg, Throwable causa){
        super(msg, causa);
    }

}
