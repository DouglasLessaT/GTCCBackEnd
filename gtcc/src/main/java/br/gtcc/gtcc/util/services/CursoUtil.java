package br.gtcc.gtcc.util.services;

import java.util.List;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.cursos.CursoInativoException;
import br.gtcc.gtcc.util.exceptions.cursos.CursoNaoExisteExeception;
import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.repository.CursoRepository;
import br.gtcc.gtcc.services.spec.CursoInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CursoUtil {
    

    private final CursoRepository cursoRepository;

    public Curso salvarCurso(Curso curso) {
        return this.cursoRepository.save(curso);
    }

    public boolean validId(Long id){
        if (id != null)
            throw new IdInvalidoException("O id do Curso informado é inválido");
        return true;
    }

    public boolean validIdForUpdate(Long id){
        if (id != null)
            throw new IdInvalidoException("O id é invalido");
        return true;
    }

    public boolean existeCurso(Long id){
        if(this.cursoRepository.existsById(id) == false)
           throw new CursoNaoExisteExeception("O Curso não existe");
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

    public boolean isAtivo(Curso curso) {
        if (curso.getAtivo() != 1) {
            throw new CursoInativoException("Não pode criar curso inativo");
        }
        return true;
    }   
}
