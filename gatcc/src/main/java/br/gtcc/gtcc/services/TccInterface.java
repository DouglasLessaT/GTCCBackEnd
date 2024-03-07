package br.gtcc.gtcc.services;

import java.util.List;

import br.gtcc.gtcc.model.Tcc;


public interface TccInterface {
    public Tcc createTcc(Tcc tcc);
   
   public Tcc updateTCC(Tcc tcc);
   
   public Tcc deleteTCC(Tcc tcc);
   
   public List<Tcc> getAllTCC();
   
   public Tcc getTCC(Tcc tcc);
}
