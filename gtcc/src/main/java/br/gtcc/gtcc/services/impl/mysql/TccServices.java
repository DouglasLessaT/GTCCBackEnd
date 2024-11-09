package br.gtcc.gtcc.services.impl.mysql;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.util.services.TccUtil;
import br.gtcc.gtcc.util.services.UsuarioUtil;
import br.gtcc.gtcc.util.Console;

@Service
public class TccServices implements TccInterface<Tcc, Long> {

    @Autowired
    public TccUtil tccUtil;

    @Autowired
    public UsuarioUtil userUtil;

    @Override
    public Tcc createTcc(Tcc tcc) {
       
        Long idAluno = tcc.getUsuario().getIdUsuario();
        Long idTcc = tcc.getId();

        this.tccUtil.validaIdAluno(idAluno);
        this.tccUtil.validaIdTccParaCriacao(idTcc);

        this.tccUtil.checkExistsTccpParaCriacao(idTcc);
        this.tccUtil.existsAluno(idAluno);

        Usuario aluno = this.userUtil.buscaUsersById(idAluno);

        tcc = this.tccUtil.adicionarAlunoNoTcc(tcc ,aluno);

        this.tccUtil.checkSeAlunoTemTcc(aluno);

        this.userUtil.salvarUser(aluno);
    
        return this.tccUtil.salvarTcc(tcc);

    }

    @Override
    public Tcc updateTCC(Tcc tcc, Long id) {
        
        Long idAluno = tcc.getUsuario().getIdUsuario();

        this.tccUtil.validaIdAluno(idAluno);
        this.tccUtil.validaIdTcc(id);

        this.tccUtil.checkExistsTcc(id);
        this.tccUtil.existsAluno(idAluno);
        
        Usuario aluno = this.userUtil.buscaUsersById(idAluno);

        this.tccUtil.userTypeIsAluno(aluno);

        Tcc tccRepo = this.tccUtil.buscarTcc(id);
        tccRepo = this.tccUtil.moldeBasicoTcc(tccRepo ,tcc);

        Usuario alunoRepo = tccRepo.getUsuario();

        Boolean isEqualsAlunos = aluno.getIdUsuario().equals(alunoRepo.getIdUsuario());
    
        if( !isEqualsAlunos ){
            
            Boolean checkSeAlunoTemTcc = this.tccUtil.checkSeAlunoTemTccSemExecao(aluno);
            
            if( !checkSeAlunoTemTcc )

                this.tccUtil.removendoAlunoDeUmTcc(idAluno);
            
            tccRepo = this.tccUtil.trocandoORelacionamentoDeAlunoComTcc(tccRepo, aluno);
        
        }

        return this.tccUtil.salvarTcc(tccRepo);
   
    }

    @Override
    public Tcc deleteTCC(Long id) {
        
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
    public Tcc getTCC(Long id) {
                
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

   @Override //Esse servico vai para banca -> Migrar para outro servico 
   public Tcc adicionarOrientadorEmTcc(Long idTcc ,Long idOrientador){
        
        this.tccUtil.validaIdOrientador(idOrientador);
        this.tccUtil.validaIdTcc(idOrientador);
        
        this.tccUtil.checkExistsTcc(idTcc);
        this.userUtil.checkExistsUser(idOrientador);
        
        Tcc tcc = this.tccUtil.buscarTcc(idTcc);
        Usuario orientador = this.userUtil.buscaUsersById(idOrientador);
        // this.tccUtil.isEqualsType(orientador);

        // this.tccUtil.adicionarOrientadorEmTcc(tcc, orientador);
        
        return tcc;
   }

   @Override 
   public Tcc adicionarAlunoEmTcc(Long idTcc ,Long idAluno){

        this.tccUtil.validaIdTcc(idAluno);
        this.tccUtil.checkExistsTcc(idTcc);
        this.userUtil.checkExistsUser(idAluno);

        Tcc tcc = this.tccUtil.buscarTcc(idTcc);
        Usuario aluno = this.userUtil.buscaUsersById(idAluno);
        this.tccUtil.userTypeIsAluno(aluno);

        this.tccUtil.adicionarAlunoEmTcc(tcc, aluno);
        this.tccUtil.salvarTcc(tcc);
        return tcc;
   }

}
