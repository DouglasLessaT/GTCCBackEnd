package br.gtcc.gtcc.services.impl.neo4j;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.cypherdsl.core.Use;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;
import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.util.Console;

@Service
public class TccServices implements TccInterface<Tcc, String> {

    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public UsersRepository usersRepository;

    @Override
    public Tcc createTcc(Tcc tcc) {
       
        String idAluno = tcc.getIdAluno();

        String idOrientador = tcc.getIdOrientador();

        if((idAluno != " " || idAluno != null) && (idOrientador != " " || idOrientador != null) ){
            
            Boolean existsAluno = this.usersRepository.existsById(idAluno);
            Boolean existsOrientador = this.usersRepository.existsById(idOrientador); 

            if ((tcc.getId() != null || tcc.getId() != " ") && (existsAluno == true && existsOrientador == true)) {
                
                Users aluno = this.usersRepository.findById(idAluno).get();
                Users orientador = this.usersRepository.findById(idOrientador).get();

                tcc.setAluno(aluno);
                tcc.setOrientador(orientador);

                EnumSet<UserType> userTypeCoordenador = EnumSet.of(UserType.COORDENADOR);
                EnumSet<UserType> userTypeProfessor = EnumSet.of(UserType.PROFESSOR);
                
                boolean isCoordenador = orientador.getUserType().equals(userTypeCoordenador);
                boolean isProfessor = orientador.getUserType().equals(userTypeProfessor);
                
                if(isCoordenador == true || isProfessor == true){
                    
                    if((aluno.getTccsGerenciados().size() == 0)){

                        orientador.getTccsGerenciados().add(tcc);  
                        aluno.getTccsGerenciados().add(tcc);

                        this.usersRepository.save(orientador);
                        this.usersRepository.save(aluno);
                    
                    }else{

                        
                        throw new IllegalArgumentException("Os funcionários não são professores ou coordenadores.");
                    
                    }

                } else {
                
                    throw new IllegalArgumentException("O orietador não é um funcionário.");
                }
                
                return tccRepository.save(tcc);

            } else {
                
                throw new IllegalArgumentException("O Tcc já existe ou o orietador ou aluno não existe.");
            }

        }
        throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
    }

    @Override
    public Tcc updateTCC(Tcc tcc, String id) {

        if (tcc != null && id != null) {


            Tcc existingTcc = getTCC(id);
            
            if (existingTcc != null) {


                existingTcc.setTitle(tcc.getTitle());
                existingTcc.setTheme(tcc.getTheme());
                existingTcc.setCurse(tcc.getCurse());
                existingTcc.setDateOfApresentation(tcc.getDateOfApresentation());

                if(tcc.getIdAluno() != null && tcc.getIdOrientador() != null){


                    Users orientador = this.usersRepository.findById(tcc.getIdOrientador()).get();
                    Users aluno = this.usersRepository.findById(tcc.getIdAluno()).get();

                    if( orientador != null && aluno != null){


                        EnumSet<UserType> userTypeCoordenador = EnumSet.of(UserType.COORDENADOR);
                        EnumSet<UserType> userTypeProfessor = EnumSet.of(UserType.PROFESSOR);
                        
                        boolean isCoordenador = orientador.getUserType().equals(userTypeCoordenador);
                        boolean isProfessor = orientador.getUserType().equals(userTypeProfessor);
         
                        if(isCoordenador == true || isProfessor == true){

                            Users orientadorRepo = existingTcc.getOrientador();
                            Users alunoRepo = existingTcc.getAluno();

                            Boolean isEqualsAlunos = aluno.getId().equals(alunoRepo.getId());
                            Boolean isEqualsOrientadores = orientador.getId().equals(orientadorRepo.getId());
                            
                            if( !isEqualsOrientadores  ){

                                removeRelacionamento(orientadorRepo.getId(), id);
                                existingTcc.setIdOrientador(tcc.getIdOrientador());
                                orientador.getTccsGerenciados().add(existingTcc);
                                existingTcc.setOrientador(orientador);
                                this.usersRepository.save(orientador);

                            }
                           
                            if( !isEqualsAlunos ){
                                    
                                removeRelacionamento(alunoRepo.getId(), id);
                                existingTcc.setIdAluno(tcc.getIdAluno());
                                aluno.getTccsGerenciados().add(existingTcc);
                                existingTcc.setAluno(aluno);
                                this.usersRepository.save(aluno);
                            
                            }


                            } else {

                            throw new IllegalArgumentException("Os funcionários não são professores ou coordenadores.");
                        }

                    } else {
                        
                        throw new IllegalArgumentException("O aluno ou orintador não existem.");
                    }

                } else {

                    throw new IllegalArgumentException("O aluno ou orientador são inválidos.");
                }

                return tccRepository.save(existingTcc);

            } else {

                throw new IllegalArgumentException("O Tcc não existe.");
            
            }
        } else {

            throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
        }
    }
    
    public void removeRelacionamento(String idUsuario, String idTcc) {
        this.tccRepository.removeRelacaoEntreUsuarioTcc(idUsuario, idTcc);
    }

    @SuppressWarnings("unused")
    @Override
    public Tcc deleteTCC(String id) {
        
        if( id != " " || id != null || id != "")  {

            Tcc delTcc = this.getTCC(id);
            if (delTcc != null) {    

                this.tccRepository.deleteById(id);
                return delTcc;

            } else {
                
                return null;
            
            }
        }

        throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
    }

    @Override
    public List<Tcc> getAllTCC() {
        
        if(this.tccRepository.count() > 0){

            return tccRepository.findAll();
    
        }

        throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
    }

    @SuppressWarnings("unused")
    @Override
    public Tcc getTCC(String id) {
        if(id != null || id != " "){

            return this.tccRepository.existsById(id)==true? tccRepository.findById(id).get() : null;
        }
        throw new IllegalArgumentException("O Tcc fornecido é inválido ou já possui um ID.");
    }

    
}