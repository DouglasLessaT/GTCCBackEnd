package br.gtcc.gtcc.util.services;

import java.util.List;

import javax.print.Doc;

import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.mysql.TipoDocente;
import br.gtcc.gtcc.model.mysql.DocenteBanca;
import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.model.mysql.Banca;
import br.gtcc.gtcc.model.mysql.repository.BancaRepository;
import br.gtcc.gtcc.model.mysql.repository.DocenteBancaRepository;
import br.gtcc.gtcc.model.mysql.repository.TipoDocenteRepository;
import br.gtcc.gtcc.model.mysql.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocenteBancaUtil {
    
    private final DocenteBancaRepository docenteBancaRepository;

    private final UsuarioRepository usuarioRepository;

    private final BancaRepository bancaRepository;

    private final TipoDocenteRepository tipoDocenteRepository;

    public DocenteBanca salvar(DocenteBanca docenteBanca){
        return this.docenteBancaRepository.save(docenteBanca);
    }

    public void delete(Long id){
        this.docenteBancaRepository.deleteById(id);
    }

    public Boolean validaIdDocenteBancaParaCriacao(Long id){
        if (id == null )
            return true;
        throw new RuntimeException("O id do Docente informado é inválido");
    }

    public Boolean validaId(Long id ){
        if (id == null )
            throw new RuntimeException("O id da Docente informada é inválido");
        return true;
    }

     public DocenteBanca buscarDocenteBanca(Long id){
        return this.docenteBancaRepository.findById(id).get();
    }

    public Boolean checkExistsDocenteBanca(Long id){
        if(this.docenteBancaRepository.existsById(id))
            return true;
        throw new RuntimeException("DocenteBanca não existe");
    }

    public Boolean checkExistsDocenteBancaParaCriacao(Long id){
        if(!this.docenteBancaRepository.existsById(id))
            throw new RuntimeException("DocenteBanca já existe");
        return true;
    }

    public List<DocenteBanca> listaTodasBancas(){
        return this.docenteBancaRepository.findAll();
    }

    public DocenteBanca checkConflictsDocenteBanca(Long idDocente){
        return null;
    }
}
