package br.gtcc.gtcc.util.services;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import br.gtcc.gtcc.util.exceptions.tcc.NaoExisteTccNoBancoException;
import br.gtcc.gtcc.util.exceptions.tcc.NaoExisteTccParaRelacionarComApresentacoesException;
import br.gtcc.gtcc.util.exceptions.tcc.TccExisteException;
import br.gtcc.gtcc.util.exceptions.tcc.TccNaoExisteException;
import br.gtcc.gtcc.util.exceptions.usuario.AlunoTemTccException;
import br.gtcc.gtcc.util.exceptions.usuario.UsuarioNaoAlunoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.TccRepository;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.usuario.AlunoNaoEncontradoException;
import br.gtcc.gtcc.util.exceptions.usuario.OrientadorNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import br.gtcc.gtcc.model.mysql.Usuario;

@Component
@RequiredArgsConstructor
public class TccUtil {

    private final String TYPE_COORDENADOR = "COORDENADOR";
    private final String TYPE_PROFESSOR = "PROFESSOR";
    private final String TYPE_ADMIN = "ADMIN";
    private final String TYPE_ALUNO = "ALUNO";
 
    public final TccRepository tccRepository;

    public final UsuarioRepository usersRepository;

    public Tcc salvarTcc(Tcc tcc){
        return this.tccRepository.save(tcc);
    } 

    public void deleteTcc(Long id){
         this.tccRepository.deleteById(id);
    }

    public Boolean validaIdTccParaCriacao(Long id){
        if (id == null )
            return true;
        throw new IdInvalidoException("O id do Tcc informado é inválido");
    }

    public Boolean validaIdTcc(Long id){
        if (id == null )
            throw new IdInvalidoException("O id do Tcc informado é inválido");        
        return true;
    }

    public Boolean validaIdAluno(Long id){
        if (id == null )
            throw new IdInvalidoException("O id do aluno informado é inválido");

        return true;
    }

    public Boolean validaIdOrientador(Long id){
        if (id == null )
            throw new IdInvalidoException("O id do orientador informado é inválido");

        return true;
    }
    
    public Boolean existsAluno(Long id){

        if(this.usersRepository.existsById(id))
            return true;

        throw new AlunoNaoEncontradoException("O Aluno informado não existe");        
                
    }

    public Boolean existsOrientador(Long id){

        if(this.usersRepository.existsById(id))
            return true;

        throw new OrientadorNaoEncontradoException("O Orientador informado não existe");

    }

    public Tcc adicionarAlunoNoTcc(Tcc tcc ,Usuario aluno){
        tcc.setUsuario(aluno);
        return tcc;
    }

    //Método vai buscar da tela GRUPO ainda inexistente
    //Retorna false
    // public Boolean isEqualsType(Usuario user){
        
    //     Boolean isEqualsAdmin = user.getUserType().equals(TYPE_ADMIN);
    //     Boolean isEqualsProfessor = user.getUserType().equals(TYPE_PROFESSOR);
    //     Boolean isEqualsCoordenador = user.getUserType().equals(TYPE_COORDENADOR);
        
    //     if(isEqualsAdmin || isEqualsProfessor || isEqualsCoordenador)
    //         return true;

    //     throw new RuntimeException("O Usuário é do tipo aluno, ele não pode criar um Tcc");
        
    // }

    public Boolean userTypeIsAluno(Usuario user){
        
        Boolean isAluno = user.getGrupo().getNome().equals(TYPE_ALUNO);
        
        if( isAluno )
            return true;

        throw new UsuarioNaoAlunoException("O Usuário não é do tipo aluno");
        
    }

    public Boolean checkSeAlunoTemTcc(Usuario aluno){
        Boolean isEqualsAZeroTccAluno =  usersRepository.checkSeAlunoTemTcc(aluno.getIdUsuario()) == 0;
        if(isEqualsAZeroTccAluno)
            return true;
        
        throw new AlunoTemTccException("O aluno ja esta em outro tcc.");
        
    }

    public Boolean checkExistsTcc(Long id){

        if(this.tccRepository.existsById(id))
            return true;
        throw new TccNaoExisteException("Tcc não existe no banco");
        
    }

    public Boolean checkExistsTccpParaCriacao(Long id){

        if(this.tccRepository.existsById(id))
            throw new TccExisteException("Tcc já existe no banco");
        return true;
        
    }

    public Tcc buscarTcc(Long id){
        return this.tccRepository.findById(id).get();
    }

    public Tcc moldeBasicoTcc(Tcc oldTcc ,Tcc newTcc){

        oldTcc.setId(newTcc.getId());
        oldTcc.setUsuario(newTcc.getUsuario());
        oldTcc.setTitulo(newTcc.getTitulo());
        oldTcc.setTema(newTcc.getTema());
        oldTcc.setCurso(newTcc.getCurso());
        
        return oldTcc;
    }

    public Tcc trocandoORelacionamentoDeAlunoComTcc(Tcc tcc ,Usuario newAluno ){

        tcc.setUsuario(newAluno);
        this.usersRepository.save(newAluno);
        return tcc;
    }

    public Long countDeTccs(){

        Long countTccs = this.tccRepository.count();
        if( countTccs > 0)
            return countTccs;
        throw new NaoExisteTccNoBancoException("Não existe Tcc no banco");
        
    }   

    public List<Tcc> buscarTodosOsTcc(){
        return tccRepository.findAll();
    }

    public Tcc buscarTccPorTitulo(String title){
        return this.tccRepository.listTccByTitulo(title);
    }

    public Long countDeTccsSemApresentacao(){

        if(this.tccRepository.countTccComApresentcao() > 0)
            return this.tccRepository.countTccComApresentcao();
        throw new NaoExisteTccParaRelacionarComApresentacoesException("Não existe tcc a ser relacionado a apreentaçoes");

    }   

    public List<Tcc> listaDeTccSemApresentacao(){
        return this.tccRepository.listTccSemApresentcao();
    }

    public Boolean checkSeAlunoTemTccSemExecao(Usuario aluno){

        Long idAluno = aluno.getIdUsuario();
        Boolean isEqualsAZeroTccAluno =  this.tccRepository.buscaTccAluno(idAluno) == 0;
        if(isEqualsAZeroTccAluno)
            return true;
        
        return false;
    }

    public Usuario adicionarAlunoEmTcc(Tcc tcc ,Usuario aluno){
        
        tcc.setUsuario(aluno);
        return aluno;
        
    }

    public void removendoAlunoDeUmTcc(Long idAluno){
        this.tccRepository.removerDiscenteTcc(idAluno); 
    }

    public void removerRelacionamentoEntreUsuarioETcc(Long idUsuario, Long idTcc){
        this.tccRepository.removeRelacaoEntreUsuarioTcc(idUsuario, idTcc);
    }

    public void trocaTccDentroApresentacao(Long idTcc ,Apresentacao apresentacao ,Tcc tcc){
        apresentacao.setTcc(tcc);
        this.tccRepository.save(tcc);
    }
} 
