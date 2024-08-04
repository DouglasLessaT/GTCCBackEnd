package br.gtcc.gtcc.util.services;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.model.neo4j.Tcc;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.TccRepository;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;

@Component
public class TccUtil {

    private final EnumSet<UserType> TYPE_COORDENADOR = EnumSet.of(UserType.COORDENADOR);
    private final EnumSet<UserType> TYPE_PROFESSOR = EnumSet.of(UserType.PROFESSOR); 
    private final EnumSet<UserType> TYPE_ADMIN = EnumSet.of(UserType.ADMIN);
    private final EnumSet<UserType> TYPE_ALUNO = EnumSet.of(UserType.ALUNO);
 
    @Autowired
    public TccRepository tccRepository;

    @Autowired
    public UsersRepository usersRepository;

    public Tcc salvarTcc(Tcc tcc){
        return this.tccRepository.save(tcc);
    } 

    public void deleteTcc(String id){
         this.tccRepository.deleteById(id);
    }

    public Boolean validaIdTccParaCriacao(String id){
        if (id == null || id == "" || id == " ")
            return true;
        throw new RuntimeException("O id do Tcc informado é inválido");
    }

    public Boolean validaIdTcc(String id){
        if (id == null || id == "" || id == " ")
            throw new RuntimeException("O id do Tcc informado é inválido");        
        return true;
    }

    public Boolean validaIdAluno(String id){
        if (id == null || id == "" || id == " ")
            throw new RuntimeException("O id do aluno informado é inválido");

        return true;
    }

    public Boolean validaIdOrientador(String id){
        if (id == null || id == "" || id == " ")
            throw new RuntimeException("O id do orientador informado é inválido");

        return true;
    }
    
    public Boolean existsAluno(String id){

        if(this.usersRepository.existsById(id))
            return true;

        throw new RuntimeException("O Aluno informado não existe");        
                
    }

    public Boolean existsOrientador(String id){

        if(this.usersRepository.existsById(id))
            return true;

        throw new RuntimeException("O Orientador informado não existe");

    }

    public Tcc adicionarAlunoNoTcc(Tcc tcc ,Users aluno){
        tcc.setAluno(aluno);
        return tcc;
    }

    public Tcc adicionarOrientadorNoTcc(Tcc tcc ,Users orientador){
        tcc.setOrientador(orientador);
        return tcc;
    }

    public Boolean isEqualsType(Users user){
        
        Boolean isEqualsAdmin = user.getUserType().equals(TYPE_ADMIN);
        Boolean isEqualsProfessor = user.getUserType().equals(TYPE_PROFESSOR);
        Boolean isEqualsCoordenador = user.getUserType().equals(TYPE_COORDENADOR);
        
        if(isEqualsAdmin || isEqualsProfessor || isEqualsCoordenador)
            return true;

        throw new RuntimeException("O Usuário é do tipo aluno, ele não pode criar um Tcc");
        
    }

    public Boolean userTypeIsAluno(Users user){
        
        Boolean isAluno = user.getUserType().equals(TYPE_ALUNO);
        
        if( isAluno )
            return true;

        throw new RuntimeException("O Usuário não é do tipo aluno");
        
    }

    public Boolean checkSeAlunoTemTcc(Users aluno){
        Boolean isEqualsAZeroTccAluno =  aluno.getTccsGerenciados().size() == 0;
        if(isEqualsAZeroTccAluno)
            return true;
        
        throw new RuntimeException("O aluno ja esta em outro tcc.");
        
    }

    public Users adicionarTccNoOrientador(Tcc tcc ,Users orientador){

        orientador.getTccsGerenciados().add(tcc);
        return orientador;

    }

    public Users adicionarTccNoAluno(Tcc tcc ,Users aluno){

        aluno.getTccsGerenciados().add(tcc);
        return aluno;

    }

    public Boolean checkExistsTcc(String id){

        if(this.tccRepository.existsById(id))
            return true;
        throw new RuntimeException("Tcc não existe no banco");
        
    }

    public Boolean checkExistsTccpParaCriacao(String id){

        if(this.tccRepository.existsById(id))
            throw new RuntimeException("Tcc já existe no banco");
        return true;
        
    }

    public Tcc buscarTcc(String id){
        return this.tccRepository.findById(id).get();
    }

    public Tcc moldeBasicoTcc(Tcc oldTcc ,Tcc newTcc){

        oldTcc.setId(newTcc.getId());
        oldTcc.setIdAluno(newTcc.getIdAluno());
        oldTcc.setIdOrientador(newTcc.getIdOrientador());
        oldTcc.setTitle(newTcc.getTitle());
        oldTcc.setTheme(newTcc.getTheme());
        oldTcc.setCurse(newTcc.getCurse());
        oldTcc.setDateOfApresentation(newTcc.getDateOfApresentation());
        
        return oldTcc;
    }

    public Tcc trocandoORelacionamentoDeProfessorComTcc(Tcc tcc ,Users newOrientador ,Users oldOrientador ,String idTcc){
        
        this.removerRelacionamentoOrienta( oldOrientador.getId(), idTcc);
        tcc.setOrientador(newOrientador);
        tcc.setIdOrientador(newOrientador.getId());
        this.removeRelacionamentoDeTccsGerenciados( oldOrientador.getId(), idTcc);
        oldOrientador.getTccsGerenciados().removeIf( tccItem -> tccItem.getId().equals(idTcc));
        newOrientador.getTccsGerenciados().add(tcc);
        
        this.usersRepository.save(newOrientador);

        return tcc;
        
    }

    public Tcc trocandoORelacionamentoDeAlunoComTcc(Tcc tcc ,Users newAluno ,Users oldAluno ,String idTcc){

        this.removerRelacionamentoRealiza(oldAluno.getId() , idTcc);
        tcc.setAluno (newAluno);
        tcc.setIdAluno(newAluno.getId());
        
        this.removeRelacionamentoDeTccsGerenciados(oldAluno.getId(), idTcc);
        oldAluno.getTccsGerenciados().clear();
        newAluno.getTccsGerenciados().add(tcc);
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

    public Tcc buscarTccPorTitulo(String title){
        return this.tccRepository.buscarTccPorTitulo(title);
    }

    public Long countDeTccsSemApresentacao(){

        if(this.tccRepository.countTccInApresentation() > 0)
            return this.tccRepository.countTccInApresentation();
        throw new RuntimeException("Não existe tcc a ser relacionado a apreentaçoes");

    }   

    public List<Tcc> listaDeTccSemApresentacao(){
        return this.tccRepository.getTccInApresentation();
    }

    public Boolean _checkSeAlunoTemTccSemExecao(Users aluno){

        Boolean isEqualsAZeroTccAluno =  aluno.getTccsGerenciados().size() == 0;
        if(isEqualsAZeroTccAluno)
            return true;
        
        return false;
    }

    public void removendoAlunoDeUmTcc(String idAluno ,Users aluno ){

        Iterator<Tcc> iterator = aluno.getTccsGerenciados().iterator();
        Tcc oldTcc = iterator.next();

        String idTcc = oldTcc.getId();

        this.tccRepository.removeRelacaoEntreUsuarioTcc(idAluno, idTcc);
        aluno.getTccsGerenciados().clear();

        this.tccRepository.removeRelacaoRealizaaEntreUsuarioTcc(idAluno ,idTcc);
        oldTcc.setAluno(null);
        oldTcc.setIdAluno(null);

        this.usersRepository.save(aluno);
        this.tccRepository.save(oldTcc);
        
    }

    public void removendoOrientadorDeUmTcc(String idOrientador ,Users orientador ){

        Iterator<Tcc> iterator = orientador.getTccsGerenciados().iterator();
        Tcc oldTcc = iterator.next();

        String idTcc = oldTcc.getId();

        this.tccRepository.removeRelacaoEntreUsuarioTcc(idOrientador, idTcc);
        orientador.getTccsGerenciados().clear();

        this.tccRepository.removeRelacaoRealizaaEntreUsuarioTcc(idOrientador ,idTcc);
        oldTcc.setOrientador(null);
        oldTcc.setIdOrientador(null);

        this.usersRepository.save(orientador);
        this.tccRepository.save(oldTcc);
        
    }

    public void adicionarAlunoEmTcc(Tcc tcc ,Users aluno){
        
        tcc.setAluno(aluno);
        tcc.setIdAluno(aluno.getId());
        aluno.getTccsGerenciados().add(tcc);

        this.usersRepository.save(aluno);
        this.tccRepository.save(tcc);
        
    }

    public void adicionarOrientadorEmTcc(Tcc tcc ,Users orientador){

        tcc.setOrientador(orientador);
        tcc.setIdOrientador( orientador.getId());
        orientador.getTccsGerenciados().add(tcc);

        this.usersRepository.save(orientador);
        this.tccRepository.save(tcc);
        
    }

    public void removeRelacionamentoDeTccsGerenciados(String idUsuario, String idTcc) {
        this.tccRepository.removeRelacaoEntreUsuarioTcc(idUsuario, idTcc);
    }

    public void removerRelacionamentoOrienta(String idUsuario, String idTcc){
        this.tccRepository.removeRelacaoOrientaEntreUsuarioTcc(idUsuario, idTcc);
    }

    public void removerRelacionamentoRealiza(String idUsuario, String idTcc){
        this.tccRepository.removeRelacaoRealizaaEntreUsuarioTcc(idUsuario, idTcc);
    }

    public void trocaTccDentroApresentacao(String idTcc ,ApresentationBanca apresentationBanca ,Tcc tcc){
        apresentationBanca.setIdTcc(idTcc);
        apresentationBanca.setTcc(tcc);
    }
} 
