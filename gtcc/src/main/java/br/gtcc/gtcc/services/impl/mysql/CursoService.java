package br.gtcc.gtcc.services.impl.mysql;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.services.exception.CursosNaoCadastradosException;
import br.gtcc.gtcc.services.spec.CursoInterface;
import br.gtcc.gtcc.util.services.CursoUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CursoService implements CursoInterface<Curso ,Long>{ 

    private final CursoUtil utilCurso;

    @Override
    public Curso criarCurso(Curso curso) {

        this.utilCurso.isAtivo(curso);
        this.utilCurso.validId(curso.getId());
        String tituloTrim = curso.getTitulo().toLowerCase().trim();

        curso.setTitulo(tituloTrim);

        return this.utilCurso.salvarCurso(curso);
    }

    @Override
    public Curso buscarCurso(Long id) {
        
        this.utilCurso.validId(id);
        this.utilCurso.existeCurso(id);
        Curso curso = this.utilCurso.buscarCurso(id);
        return this.utilCurso.transferenciaDeObjeto(curso);
    }

    @Override
    public List<Curso> listaCursos() {
        if(this.utilCurso.contagemDeCurso() == 0)
            throw new CursosNaoCadastradosException("Não existe cursos cadastrados");
        return this.utilCurso.listaCursos();
    }

    @Override
    public Curso alterarCurso(Long idCurso, Curso curso) {
        
        Long idOld = idCurso;
        Long newId = curso.getId();
        this.utilCurso.validIdForUpdate(newId);
        Curso curso_ = this.utilCurso.moldeCurso(curso);
        curso_.setId(idOld);
        this.utilCurso.salvarCurso(curso_);
        return curso_;
    }

    @Override
    public Curso deletarCurso(Long idCurso) {

        this.utilCurso.validId(idCurso);
        this.utilCurso.existeCurso(idCurso);
        Curso cursoDeletado = this.utilCurso.buscarCurso(idCurso);
        this.utilCurso.deleteCurso(idCurso);

        return cursoDeletado;

    }

}
