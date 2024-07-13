package br.gtcc.gtcc.util.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.UserType;
import br.gtcc.gtcc.model.neo4j.Users;
import br.gtcc.gtcc.model.neo4j.repository.UsersRepository;

@Component
public class UserUtil {

    public static final List<String> PERMISSOES =  new ArrayList<>(Arrays.asList(
        "ROLE_USER","ROLE_ADMIN","ROLE_PROFESSOR","ROLE_COORDENADOR","ROLE_ALUNO"
    ));

    public static final Set<UserType> TYPES = EnumSet.allOf(UserType.class);

    @Autowired
    public UsersRepository repository;

    @Autowired
    @Lazy
    public PasswordEncoder passwordEncoder;

    public Users salvarUser(Users user){
        
        return repository.save(user);

    }

    public String enconder(String senha){
        
        senha = this.passwordEncoder.encode(senha);
        return senha;
    }

    public Users validaUser(Users user){
        
        Users userValid = user;

        String id = userValid.getId();
        String login = userValid.getLogin();
        List<String> permissoes = userValid.getPermissoes();
        Set<UserType> tipos = userValid.getUserType();

        validaIdForCreate(id);
        validaLogin(login);
        validaPermissao(permissoes);
        validaTipoDeUsuario(tipos);

        return userValid;
    
    }   

    public Boolean validaIdForCreate(String id){
        
        if (id == null || id == "" || id == " ")
            return true;

        throw new RuntimeException("O id informado é inválido");
    }

    public Boolean validaLogin(String login){
        
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Boolean isValidLogin = pattern.matcher(login).matches();

        if(isValidLogin == false)

            throw new RuntimeException("Login inválido, tem que ser números");        
        
        return true;
    
    }

    public Boolean validaPermissao(List<String> permissoes){

        List<String> permissaoInvalida = new ArrayList<>();

        for(String permissao : permissoes){ 

            if(!PERMISSOES.contains(permissao)){
                permissaoInvalida.add(permissao);
            }

        }

        int permissaoInvalidaTam = permissaoInvalida.size();

        if(permissaoInvalidaTam != 0){

            throw new RuntimeException("Existe alguma permissão inválida");        
        
        }

        return true;

    }

    public Boolean validaTipoDeUsuario(Set<UserType> tipos){
        
        for(UserType tipo : tipos){

            if(!TYPES.contains(tipo)){
                throw new RuntimeException("Existe algum tipo atribuido a esse usuário inválida");    
            }

        }

        return true;

    }

    public Boolean checkExistsUser(String id){

        Boolean existsUser = this.repository.existsById(id);      

        if(!existsUser){
            throw new RuntimeException("Usuário não existe no banco");  
        }

        return true;
   
    }

    public Boolean validaId(String id){
        
        if (id == null || id == "" || id == " ")
            throw new RuntimeException("O id informado é inválido");;

        return true;
    }

}
