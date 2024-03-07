package br.gtcc.gtcc.services;

import java.util.List;

import org.dizitart.no2.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.Tcc;

@Service
public class TccService implements TccInterface {

 @Autowired
 ObjectRepository<Tcc> repositoryTCC;

 @Override
 public Tcc createTcc(Tcc tcc) {
     if (tcc != null && tcc.getDateOfApresentation() != null) {
         repositoryTCC.insert(tcc);
         return tcc;
     }
     return null;
 }

 @Override
 public Tcc updateTCC(Tcc tcc) {
     if (tcc != null && tcc.getId() != null) {
         repositoryTCC.update(tcc);
         return tcc;
     }
     return null;
 }

 @Override
 public Tcc deleteTCC(Tcc tcc) {
     if (tcc != null && tcc.getId() != null) {
         repositoryTCC.remove(tcc);
         return tcc;
     }
     return null;
 }

 @Override
 public List<Tcc> getAllTCC() {
     return repositoryTCC.find().toList();
 }

 @Override
 public Tcc getTCC(Tcc tcc) {
     if (tcc != null && tcc.getId() != null) {
         return repositoryTCC.getById(tcc.getId());
     }
     return null;
 }

}
