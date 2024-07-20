    package br.gtcc.gtcc.services.impl.neo4j;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.util.services.TccUtil;
import br.gtcc.gtcc.util.services.UserUtil;
import br.gtcc.gtcc.util.Console;

@Service
public class TccServices implements TccInterface<Tcc, String> {

    @Autowired
    public TccUtil tccUtil;

    @Autowired
    public UserUtil userUtil;

    @Override
    public Tcc createTcc(Tcc tcc) {
       
        String idAluno = tcc.getIdAluno();
        String idOrientador = tcc.getIdOrientador();
        String idTcc = tcc.getId();

        this.tccUtil.validaIdOrientador(idOrientador);
        this.tccUtil.validaIdAluno(idAluno);
        this.tccUtil.validaIdTccParaCriacao(idTcc);

        this.tccUtil.checkExistsTccpParaCriacao(idTcc);
        this.tccUtil.existsAluno(idAluno);
        this.tccUtil.existsOrientador(idOrientador); 

        Users aluno = this.userUtil.buscaUsersById(idAluno);
        Users orientador = this.userUtil.buscaUsersById(idOrientador);

        tcc = this.tccUtil.adicionarAlunoNoTcc(tcc ,aluno);
        tcc = this.tccUtil.adicionarOrientadorNoTcc(tcc ,orientador);

        this.tccUtil.isEqualsType(orientador);

        this.tccUtil.checkSeAlunoTemTcc(aluno);

        orientador = this.tccUtil.adicionarTccNoOrientador(tcc, orientador);
        aluno = this.tccUtil.adicionarTccNoAluno(tcc, aluno);

        this.userUtil.salvarUser(orientador);
        this.userUtil.salvarUser(aluno);
                    
        return this.tccUtil.salvarTcc(tcc);

    }

    @Override
    public Tcc updateTCC(Tcc tcc, String id) {

        if (tcc != null && id != null) {

            Tcc existingTcc = getTCC(id);
            
            if (existingTcc != null) {
                existingTcc.setIdAluno(tcc.getIdAluno());
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

    @Override
    public Tcc getTCCByTitle(String title) {
        
        String trimmedTitle = title.trim();
        
        // String normalizedTitle = trimmedTitle.toLowerCase();
        
        return tccRepository.findByTitle(trimmedTitle);
    }

    @Override
    public List<Tcc> getTccSemApresentacao() {
        
        Long countTccSemApresentacao = this.tccRepository.countTccInApresentation();

        if(countTccSemApresentacao < 0){
            throw new IllegalArgumentException("Não existe tcc a ser relacionado a apreentaçoes");
        }

        List<Tcc> listTccSemApresentacao = this.tccRepository.getTccInApresentation();
        
        return listTccSemApresentacao;
    }

}