package br.gtcc.gtcc.services.impl.mysql;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.mysql.DocenteBanca;
import br.gtcc.gtcc.services.spec.DocenteBancaInterface;
import br.gtcc.gtcc.util.services.DocenteBancaUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocenteBancaService implements DocenteBancaInterface<DocenteBanca , Long>{

    private final DocenteBancaUtil docenteBancaUtil;

    @Override
    public DocenteBanca createDocenteBanca(DocenteBanca docenteBanca) {

        this.docenteBancaUtil.validaIdDocenteBancaParaCriacao(docenteBanca.getId());
        this.docenteBancaUtil.checkExistsDocenteBancaParaCriacao(docenteBanca.getId());

        docenteBanca.setAtivo(1);
        //Verificar se tem conflito entre docente menbro da bancae e a localização.  
        this.docenteBancaUtil.checkConflictsDocenteBanca(docenteBanca.getId());

        return this.docenteBancaUtil.salvar(docenteBanca);
    }

    @Override
    public DocenteBanca updateDocenteBanca(Long id, DocenteBanca docenteBanca) {

        this.docenteBancaUtil.validaId(id);
        this.docenteBancaUtil.checkConflictsDocenteBanca(id);

        this.docenteBancaUtil.validaId(docenteBanca.getId());
        this.docenteBancaUtil.checkExistsDocenteBanca(docenteBanca.getId());

        docenteBanca.setAtivo(1);
        //Verificar se tem conflito entre docente menbro da bancae e a localização.  
        this.docenteBancaUtil.checkConflictsDocenteBanca(docenteBanca.getId());

        return this.docenteBancaUtil.salvar(docenteBanca);
    }

    @Override
    public DocenteBanca deleteUsers(Long id) {
        this.docenteBancaUtil.validaId(id);
        this.docenteBancaUtil.checkExistsDocenteBanca(id);
        DocenteBanca docenteBanca = this.docenteBancaUtil.buscarDocenteBanca(id);
        this.docenteBancaUtil.delete(id);
        return docenteBanca;
    }

    @Override
    public List<DocenteBanca> getAllDocenteBanca() {
        return this.docenteBancaUtil.listaTodasBancas();
    }

    @Override
    public DocenteBanca getDocenteBanca(Long id) {

        this.docenteBancaUtil.validaId(id);
        this.docenteBancaUtil.checkExistsDocenteBanca(id);
        return this.docenteBancaUtil.buscarDocenteBanca(id);
        
    }
    
}
