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

        String idOrientador = tcc.getIdOrientador();
        String idAluno = tcc.getIdAluno();

        this.tccUtil.validaIdOrientador(idOrientador);
        this.tccUtil.validaIdAluno(idAluno);
        this.tccUtil.validaIdTcc(id);

        this.tccUtil.checkExistsTcc(id);
        this.tccUtil.existsAluno(idAluno);
        this.tccUtil.existsOrientador(idOrientador);

        Users orientador = this.userUtil.buscaUsersById(idOrientador);
        Users aluno = this.userUtil.buscaUsersById(idAluno);

        this.tccUtil.isEqualsType(orientador); 
        this.tccUtil.userTypeIsAluno(aluno);

        Tcc tccRepo = this.tccUtil.buscarTcc(id);
        tccRepo = this.tccUtil.moldeBasicoTcc(tccRepo ,tcc);

        Users orientadorRepo = tccRepo.getOrientador();
        Users alunoRepo = tccRepo.getAluno();

                            Boolean isEqualsAlunos = aluno.getId().equals(alunoRepo.getId());
                            Boolean isEqualsOrientadores = orientador.getId().equals(orientadorRepo.getId());
                            
                            if( !isEqualsOrientadores  ){

            tccRepo = this.tccUtil.trocandoORelacionamentoDeProfessorComTcc(tccRepo, orientador, orientadorRepo, id);

                            }
                           
                            if( !isEqualsAlunos ){
                                    
            Boolean checkSeAlunoTemTcc = this.tccUtil._checkSeAlunoTemTccSemExecao(aluno);
            
            if( !checkSeAlunoTemTcc)

                this.tccUtil.removendoAlunoDeUmTcc(idAluno ,aluno );
            
            tccRepo = this.tccUtil.trocandoORelacionamentoDeAlunoComTcc(tccRepo, aluno, alunoRepo, id);
        
        }

        return this.tccUtil.salvarTcc(tccRepo);
   
    }

    @Override
    public Tcc deleteTCC(String id) {
        
        this.tccUtil.validaIdTcc(id);
        this.tccUtil.checkExistsTcc(id);
        Tcc tccDeletado = this.tccUtil.buscarTcc(id);

        this.tccUtil.deleteTcc(id);
        return tccDeletado;

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