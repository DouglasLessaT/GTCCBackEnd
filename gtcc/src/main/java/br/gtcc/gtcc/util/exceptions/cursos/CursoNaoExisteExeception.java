package br.gtcc.gtcc.util.exceptions.cursos;

public class CursoNaoExisteExeception extends RuntimeException {
    
    public CursoNaoExisteExeception(){
        super();
    }

    public CursoNaoExisteExeception(String msg){
        super(msg);
    }

    public CursoNaoExisteExeception(String msg, Throwable causa){
        super(msg, causa);
    }

}
