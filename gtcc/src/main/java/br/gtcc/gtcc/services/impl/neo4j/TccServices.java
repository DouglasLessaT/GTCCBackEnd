package br.gtcc.gtcc.services.impl.neo4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import br.gtcc.gtcc.services.spec.TccInterface;

@Service
public class TccServices implements TccInterface<Tcc, String> {

    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public UsersRepository usersRepository;

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public Tcc createTcc(Tcc tcc) {
       
        System.out.println( "\n Tcc na service I " + tcc.toString());

        String idAluno = tcc.getIdAluno();

        String idOrientador = tcc.getIdOrientador();

        if((idAluno != " " || idAluno != null) && (idOrientador != " " || idOrientador != null) ){

            
            Boolean existsAluno = this.usersRepository.existsById(idAluno);
            Boolean existsOrientador = this.usersRepository.existsById(idOrientador); 
            

            if ((tcc.getId() != null || tcc.getId() != " ") && (existsAluno == true && existsOrientador == true)) {
                
                System.out.println( "\nTcc na service III" + tcc.toString());
                
                Users aluno = this.usersRepository.findById(idAluno).get();
                Users orientador = this.usersRepository.findById(idOrientador).get();

                tcc.setAluno(aluno);
                tcc.setOrientador(orientador);
                
                if(orientador.getUserType().equals("COORDENADOR") || orientador.getUserType().equals("PROFESSOR")){
    
                    orientador.getTccsGerenciados().add(tcc);
                    usersRepository.save(orientador);

                }else {

                    return null;

                }
                
                return tccRepository.save(tcc);

            }

            if( (this.tccRepository.existsById(tcc.getId())) && (existsAluno == true && existsOrientador == true)){
            
                System.out.println( "\n Tcc na service IV" + tcc.toString());
                return tccRepository.save(tcc);
            }
        }
 
        return null;
    }

    @Override
    public Tcc updateTCC(Tcc tcc) {
        if (tcc != null && tcc.getId() != null) {

            Tcc existingTcc = getTCC(tcc.getId());
            
            if (existingTcc != null) {
                existingTcc.setTitle(tcc.getTitle());
                existingTcc.setTheme(tcc.getTheme());
                existingTcc.setCurse(tcc.getCurse());
                existingTcc.setDateOfApresentation(tcc.getDateOfApresentation());
                
                return tccRepository.save(existingTcc);
            } else {
                throw new IllegalArgumentException("Tcc não encontrado para o ID fornecido: " + tcc.getId());
            }
        } else {
            throw new IllegalArgumentException("O Tcc fornecido é inválido ou não possui um ID.");
        }
    }

    @Override
    public Tcc deleteTCC(String id) {
        
        if( id != " " || id != null || id != "")  {

            Tcc delTcc = this.getTCC(id);
            if (delTcc != null) {
                tccRepository.delete(delTcc);
            } else {
                throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
            }
        }
        
        return null;
    }

    @Override
    public List<Tcc> getAllTCC() {
        
        if(this.tccRepository.count() > 0){

            return tccRepository.findAll();
    
        }

        return null;
    }

    @SuppressWarnings("unused")
    @Override
    public Tcc getTCC(String id) {
        if(id != null || id != " "){

            return this.tccRepository.existsById(id)==true? tccRepository.findById(id).get() : null;
        }
        return null;
    }

    
}