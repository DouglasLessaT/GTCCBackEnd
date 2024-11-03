package br.gtcc.gtcc.util.exceptions.tcc;

public class TccNaoExisteException extends RuntimeException {

    public TccNaoExisteException(){
    super();
  }

    public TccNaoExisteException(String msg){
    super(msg);
  }

    public TccNaoExisteException(String msg, Throwable causa){
    super(msg, causa);
  }

}
