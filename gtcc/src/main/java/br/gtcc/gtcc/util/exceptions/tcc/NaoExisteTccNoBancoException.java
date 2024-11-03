package br.gtcc.gtcc.util.exceptions.tcc;

public class NaoExisteTccNoBancoException extends RuntimeException {

    public NaoExisteTccNoBancoException(){
        super();
    }

    public NaoExisteTccNoBancoException(String msg){
        super(msg);
    }

    public NaoExisteTccNoBancoException(String msg, Throwable causa){
        super(msg, causa);
    }
}

