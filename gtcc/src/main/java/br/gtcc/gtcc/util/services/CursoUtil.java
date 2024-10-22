package br.gtcc.gtcc.util.services;

import java.util.List;

import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.repository.CursoRepository;
import br.gtcc.gtcc.services.spec.CursoInterface;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CursoUtil {
    

    private final CursoRepository cursoRepository;

    public Curso salvarCurso(Curso  cruso){
        return this.cursoRepository.save(cruso);
    }

    public boolean validId(Long id){
        if (id == null)
            throw new RuntimeException("O id do Curso informado é inválido");
        return true;
    }

    public boolean validIdForUpdate(Long id){
        if (id != null)
            throw new RuntimeException("O id é invalido");
        return true;
    }

    public boolean existeCurso(Long id){
        if(this.cursoRepository.existsById(id) == false)
           throw new RuntimeException("O id é invalido");
        return true;
    }

    public Long contagemDeCurso(){
        return this.cursoRepository.count();
    }

    public Curso buscarCurso(Long id){
        return this.cursoRepository.findById(id).get();
    }

    public List<Curso> listaCursos(){
        return this.cursoRepository.findAll();
    }

    public void deleteCurso(Long id){
        this.cursoRepository.deleteById(id);
    }

    public void inativarCurso(Long id){
        this.cursoRepository.inativar(id);
    }

    public Curso transferenciaDeObjeto(Curso curso){
        curso.setTitulo(curso.getTitulo().toUpperCase());
        return curso;
    }

    public Curso moldeCurso(Curso curso){
        Curso newCurso = new Curso();
        newCurso.setAtivo(curso.getAtivo());
        newCurso.setTitulo(curso.getTitulo());
        return newCurso;
    }

    public boolean isAtivo(Curso curso){
        if(curso.getAtivo() == 1)
            return true;
        throw new RuntimeException("Não pode criar curso inativo");
    }   
}
