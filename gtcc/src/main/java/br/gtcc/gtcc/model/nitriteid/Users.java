package br.gtcc.gtcc.model.nitriteid;

import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.index.IndexType;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;
import org.dizitart.no2.repository.annotations.Indices;

import br.gtcc.gtcc.model.UserType;

import java.util.List;
// import br.gtcc.gtcc.model.UserType;
import java.util.Date;

/*
    Entidade de Usuarios com seus atributos. O tipo de usuário aluno não   
*/

@Entity
@Indices({
    @Index(fields = "registration", type = IndexType.UNIQUE)
})
public class Users {
    
    @Id
    private NitriteId id ;
    private String name;                // Nome do usuário
    private String email;               // Email do usuário
    private String registration;        // Matricula do usuário
    private List<UserType> userType;    // Tipo de usuário podendo ser PROFESSOR, ALUNO, COORDENADOR, e tambem COORDENADOR/PROFESSOR
    private Date birthdate;             // Data de aniversário do usuário
    private int cellphone;              // Telefone do usuário

    public Users(NitriteId id, String name, String email,
     String registration, List<UserType> userType, 
     Date birthdate, int cellphone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registration = registration;
        this.userType = userType;
        this.birthdate = birthdate;
        this.cellphone = cellphone;
    }

    public Users(){
    
    }

    public String getId() {
        return id.getIdValue();
    }

    public void setId(String id) {
        this.id = NitriteId.createId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public List<UserType> getUserType() {
        return userType;
    }

    public void setUserType(List<UserType> userType) {
        this.userType = userType;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getCellphone() {
        return cellphone;
    }

    public void setCellphone(int cellphone) {
        this.cellphone = cellphone;
    }

    
    
}
