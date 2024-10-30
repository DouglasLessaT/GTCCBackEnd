package br.gtcc.gtcc.util.exceptions.cursos;

public class CursoInativoException extends RuntimeException {
    
    public CursoInativoException(){
        super();
    }

    public CursoInativoException(String msg){
        super(msg);
    }

    public CursoInativoException(String msg, Throwable causa){
        super(msg, causa);
    }


}
