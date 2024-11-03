package br.gtcc.gtcc.util.exceptions.tcc;

public class NaoExisteTccParaRelacionarComApresentacoesException extends RuntimeException {
  public NaoExisteTccParaRelacionarComApresentacoesException(){
    super();
  }

  public NaoExisteTccParaRelacionarComApresentacoesException(String msg){
    super(msg);
  }

  public NaoExisteTccParaRelacionarComApresentacoesException(String msg, Throwable causa){
    super(msg, causa);
  }

}
