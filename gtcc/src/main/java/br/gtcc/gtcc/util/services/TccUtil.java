package br.gtcc.gtcc.util.services;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.TccRepository;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.Users;

@Component
public class TccUtil {

    private final EnumSet<UserType> TYPE_COORDENADOR = EnumSet.of(UserType.COORDENADOR);
    private final EnumSet<UserType> TYPE_PROFESSOR = EnumSet.of(UserType.PROFESSOR); 
    private final EnumSet<UserType> TYPE_ADMIN = EnumSet.of(UserType.ADMIN);
    private final EnumSet<UserType> TYPE_ALUNO = EnumSet.of(UserType.ALUNO);
 
    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public UsuarioRepository usersRepository;

    public Tcc salvarTcc(Tcc tcc){
        return this.tccRepository.save(tcc);
    } 

    public void deleteTcc(Long id){
         this.tccRepository.deleteById(id);
    }

    public Boolean validaIdTccParaCriacao(Long id){
        if (id == null )
            return true;
        throw new RuntimeException("O id do Tcc informado é inválido");
    }

    public Boolean validaIdTcc(Long id){
        if (id == null )
            throw new RuntimeException("O id do Tcc informado é inválido");        
        return true;
    }

    public Boolean validaIdAluno(Long id){
        if (id == null )
            throw new RuntimeException("O id do aluno informado é inválido");

        return true;
    }

    public Boolean validaIdOrientador(Long id){
        if (id == null )
            throw new RuntimeException("O id do orientador informado é inválido");

        return true;
    }
    
    public Boolean existsAluno(Long id){

        if(this.usersRepository.existsById(id))
            return true;

        throw new RuntimeException("O Aluno informado não existe");        
                
    }

    public Boolean existsOrientador(Long id){

        if(this.usersRepository.existsById(id))
            return true;

        throw new RuntimeException("O Orientador informado não existe");

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
        
        Boolean isAluno = user.getGrupo().equals(TYPE_ALUNO);
        
        if( isAluno )
            return true;

        throw new RuntimeException("O Usuário não é do tipo aluno");
        
    }

    public Boolean checkSeAlunoTemTcc(Usuario aluno){
        Boolean isEqualsAZeroTccAluno =  usersRepository.countUsersSemTccRelacionado() == 0;
        if(isEqualsAZeroTccAluno)
            return true;
        
        throw new RuntimeException("O aluno ja esta em outro tcc.");
        
    }

    public Boolean checkExistsTcc(Long id){

        if(this.tccRepository.existsById(id))
            return true;
        throw new RuntimeException("Tcc não existe no banco");
        
    }

    public Boolean checkExistsTccpParaCriacao(Long id){

        if(this.tccRepository.existsById(id))
            throw new RuntimeException("Tcc já existe no banco");
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

    //Isso aqui vai ser feito em outro tabela não nessa tabela banca 
    // public Tcc trocandoORelacionamentoDeProfessorComTcc(Tcc tcc ,Users newOrientador ,Users oldOrientador ,String idTcc){
        
    //     this.removerRelacionamentoOrienta( oldOrientador.getId(), idTcc);
    //     this.removeRelacionamentoDeTccsGerenciados( oldOrientador.getId(), idTcc);
    //     oldOrientador.getTccsGerenciados().removeIf( tccItem -> tccItem.getId().equals(idTcc));
    //     newOrientador.getTccsGerenciados().add(tcc);
        
    //     this.usersRepository.save(newOrientador);

    //     return tcc;
        
    // }

    public Tcc trocandoORelacionamentoDeAlunoComTcc(Tcc tcc ,Usuario newAluno ,Usuario oldAluno ,Long idTcc){

        //Alterar essa remocao de relacionamento
        //this.removerRelacionamento(oldAluno.getId() , idTcc);
        //Adicionar tcc ao usuario  
        tcc.setUsuario(newAluno);
        //Atualizar tabela de tcc
        //Atualizar tabela de aluno
        this.usersRepository.save(newAluno);
        return tcc;
    }

    public Long countDeTccs(){

        if(this.tccRepository.count() > 0)
            return this.tccRepository.count();
        throw new RuntimeException("Não existe Tcc no banco");
        
    }   

    public List<Tcc> buscarTodosOsTcc(){
        return tccRepository.findAll();
    }

    public List<Tcc> buscarTccPorTitulo(String title){
        return this.tccRepository.listTccByTitulo(title);
    }

    //Essa query pode ser feita com a tabela tb_banca ou pode usar a tcc.
    public Long countDeTccsSemApresentacao(){

        if(this.tccRepository.countTccComApresentcao() > 0)
            return this.tccRepository.countTccComApresentcao();
        throw new RuntimeException("Não existe tcc a ser relacionado a apreentaçoes");

    }   

    public List<Tcc> listaDeTccSemApresentacao(){
        return this.tccRepository.listTccComApresentcao();
    }

    public Boolean _checkSeAlunoTemTccSemExecao(Users aluno){

        Boolean isEqualsAZeroTccAluno =  aluno.getTccsGerenciados().size() == 0;
        if(isEqualsAZeroTccAluno)
            return true;
        
        return false;
    }

    public void adicionarAlunoEmTcc(Tcc tcc ,Usuario aluno){
        
        tcc.setUsuario(aluno);
        this.tccRepository.save(tcc);
        
    }

    public void removerRelacionamentoEntreUsuarioETcc(Long idUsuario, Long idTcc){
        this.tccRepository.removeRelacaoEntreUsuarioTcc(idUsuario, idTcc);
    }

    public void trocaTccDentroApresentacao(Long idTcc ,Apresentacao apresentacao ,Tcc tcc){
        apresentacao.setTcc(tcc);
        this.tccRepository.save(tcc);
    }
} 
