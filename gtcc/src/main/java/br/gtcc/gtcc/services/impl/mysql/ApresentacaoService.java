package br.gtcc.gtcc.services.impl.mysql;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.model.mysql.repository.ApresentacaoRepository;
import br.gtcc.gtcc.services.spec.ApresentacaoInterface;
import br.gtcc.gtcc.util.services.ApresentacaoUtil;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApresentacaoService implements ApresentacaoInterface<Apresentacao,Long >{

    private final ApresentacaoUtil apresentacaoUtil;

    @Override
    public  Apresentacao createApresentacao ( Apresentacao apresentacao){
     
        Long id = apresentacao.getId();
        this.apresentacaoUtil.validaIdApresentacaoParaCriacao(id);
        this.apresentacaoUtil.checkExistsApresentacaoParaCriacao(id);

        apresentacao.setAtivo(1);
        apresentacao.setDataCriacaoApresentacao(LocalDateTime.now());
        
        LocalDateTime data = apresentacao.getData();
        LocalTime horaComeco = apresentacao.getHoraInicio();
        LocalTime horasFim = apresentacao.getHoraFim();
        this.apresentacaoUtil.checkConflictsDates(data, horaComeco, horasFim);
        
        this.apresentacaoUtil.countConflitosDentroDeTcc(apresentacao.getTcc().getId());
        this.apresentacaoUtil.checkConflictsApresentacao(apresentacao.getLocalizacao().getId());

        return this.apresentacaoUtil.salvar(apresentacao);
    }

    @Override
    public  Apresentacao updateApresentacao ( Long id ,Apresentacao apresentacao){

        
        Long idNewApresntacao = apresentacao.getId();
        this.apresentacaoUtil.validaId(idNewApresntacao);
        this.apresentacaoUtil.checkExistsApresentacaoParaCriacao(idNewApresntacao);

        this.apresentacaoUtil.validaId(id);
        this.apresentacaoUtil.checkExistsApresentacaoParaCriacao(id);

        this.apresentacaoUtil.countConflitosDentroDeTcc(apresentacao.getTcc().getId());

        LocalDateTime data = apresentacao.getData();
        LocalTime horaComeco = apresentacao.getHoraInicio();
        LocalTime horasFim = apresentacao.getHoraFim();
        this.apresentacaoUtil.checkConflictsDates(data, horaComeco, horasFim);

        this.apresentacaoUtil.checkConflictsApresentacao(apresentacao.getLocalizacao().getId());

        apresentacao.setId(id);

        return this.apresentacaoUtil.salvar(apresentacao);
    }

    @Override
    public  Apresentacao deleteApresentacao ( Long id){
        
        this.apresentacaoUtil.validaId(id);
        this.apresentacaoUtil.checkExistsApresentacao(id);
        Apresentacao apr = this.apresentacaoUtil.buscarApresentacao(id);

        this.apresentacaoUtil.delete(id);
        
        return apr;
    }

    @Override
    public  Apresentacao getApresentacao ( Long id){
        
        this.apresentacaoUtil.validaId(id);
        this.apresentacaoUtil.checkExistsApresentacao(id);
        return this.apresentacaoUtil.buscarApresentacao(id);
    }

    @Override
    public List< Apresentacao > getAllApresentacao (){

        this.apresentacaoUtil.countDeApresentacoes();        
        return this.apresentacaoUtil.listaTodasApresentacoes();

    }
    
}
