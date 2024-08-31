package br.gtcc.gtcc.services.impl.mysql;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.services.spec.CursoInterface;
import br.gtcc.gtcc.util.services.CursoUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CursoService implements CursoInterface<Curso ,Long>{ 

    private final CursoUtil utilCurso;

    @Override
    public Curso criarCurso(Curso curso) {

        this.utilCurso.validId(curso.getId());
        String tituloTrim = curso.getTitulo().toLowerCase().trim();

        curso.setTitulo(tituloTrim);

        return this.utilCurso.salvarCurso(curso);
    }

    @Override
    public Curso buscarCurso(Long id) {
        
        this.utilCurso.validId(id);
        this.utilCurso.existeCurso(id);
        this.utilCurso.buscarCurso(id);
        return this.utilCurso.buscarCurso(id);
    }

    @Override
    public List<Curso> listaCursos() {
        if(this.utilCurso.contagemDeCurso() == 0)
            throw new RuntimeException("Não existe cursos cadastrados");
        return this.utilCurso.listaCursos();
    }

    @Override
    public Curso alterarCurso(Long idCurso, Curso curso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterarCurso'");
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
