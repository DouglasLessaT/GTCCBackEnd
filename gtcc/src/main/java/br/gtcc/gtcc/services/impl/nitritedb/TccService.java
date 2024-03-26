package br.gtcc.gtcc.services.impl.nitritedb;

import static org.dizitart.no2.filters.FluentFilter.where;

import java.util.List;

import org.dizitart.no2.common.WriteResult;
import org.dizitart.no2.filters.FluentFilter;
import org.dizitart.no2.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.nitriteid.Tcc;
import br.gtcc.gtcc.model.nitriteid.Users;
import br.gtcc.gtcc.services.spec.TccInterface;

@Service
public class TccService implements TccInterface<Tcc, String> {

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
 public Tcc deleteTCC(String id) {
    
    Tcc tcc = this.getTCC(id);

     if (tcc != null) {
        
        WriteResult result = repositoryTCC.remove(where("id").eq(id));
        tcc = (Tcc) result;
        return tcc;

     }

     return null;
 
    }

 @Override
 public List<Tcc> getAllTCC() {
     return repositoryTCC.find().toList();
 }

 @Override
 public Tcc getTCC(String id) {

     if (id != null) {

        Tcc tcc = repositoryTCC.find(FluentFilter.where("id").eq(id)).firstOrNull();
                    
        if( tcc != null ){
        
            return tcc ;
            
        }else{
        
            //Exeção caso o usuário não é encontrado
            return null;
        
        }

    }
     return null;
 }

}
