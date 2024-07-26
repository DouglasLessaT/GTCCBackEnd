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
    
        this.tccUtil.countDeTccs();
        return this.tccUtil.buscarTodosOsTcc();
    
    }

    @Override
    public Tcc getTCC(String id) {
                
        this.tccUtil.validaIdTcc(id);
        this.tccUtil.checkExistsTcc(id);
        Tcc tccEncontrado = this.tccUtil.buscarTcc(id);
        return tccEncontrado;

    }

    @Override
    public Tcc getTCCByTitle(String title) {
        
        String trimmedTitle = title.trim();
        return this.tccUtil.buscarTccPorTitulo(trimmedTitle);
    } 

    @Override
    public List<Tcc> getTccSemApresentacao() {

        this.tccUtil.countDeTccsSemApresentacao();
        return this.tccUtil.listaDeTccSemApresentacao();
    }

   @Override
   public Tcc adicionarOrientadorEmTcc(String idTcc ,String idOrientador){
        
        this.tccUtil.validaIdOrientador(idOrientador);
        this.tccUtil.validaIdTcc(idOrientador);
        
        this.tccUtil.checkExistsTcc(idTcc);
        this.userUtil.checkExistsUser(idOrientador);
        
        Tcc tcc = this.tccUtil.buscarTcc(idTcc);
        Users orientador = this.userUtil.buscaUsersById(idOrientador);
        this.tccUtil.isEqualsType(orientador);

        this.tccUtil.adicionarOrientadorEmTcc(tcc, orientador);
        
        return tcc;
   }

   @Override 
   public Tcc adicionarAlunoEmTcc(String idTcc ,String idAluno){
 
        this.tccUtil.validaIdOrientador(idAluno);
        this.tccUtil.validaIdTcc(idAluno);

        this.tccUtil.checkExistsTcc(idTcc);
        this.userUtil.checkExistsUser(idAluno);

        
        Tcc tcc = this.tccUtil.buscarTcc(idTcc);
        Users aluno = this.userUtil.buscaUsersById(idAluno);
        this.tccUtil.userTypeIsAluno(aluno);

        this.tccUtil.adicionarAlunoEmTcc(tcc, aluno);

        return tcc;
   }

}