package br.gtcc.gtcc.apresentacao;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.BDDMockito.times;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.apresentacao.ApresentacaoExisteException;
import br.gtcc.gtcc.util.exceptions.apresentacao.ConflitoEntreDatasException;
import br.gtcc.gtcc.util.exceptions.apresentacao.NaoExisteApresentacoesCadastradasException;
import br.gtcc.gtcc.util.exceptions.localizacao.LocalizacaoIndisponivelException;
import br.gtcc.gtcc.util.exceptions.tcc.TccJaTemApresentacaoException;
import br.gtcc.gtcc.util.services.ApresentacaoUtil;
import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.Grupo;
import br.gtcc.gtcc.model.mysql.Localizacao;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.services.impl.mysql.ApresentacaoService;

public class ApresentacaoTest {

    private ApresentacaoService aprtService;

    private ApresentacaoUtil aprtUtil;

    private Apresentacao aprtCriada;
    
    private Apresentacao aptrSemLocCriada;

    private Apresentacao criarApresentacao(){
        return new Apresentacao(
            1L,                                    
            LocalDateTime.of(2024, 11, 15, 10, 0),             
            LocalTime.of(10, 30),                  
            LocalTime.of(12, 0),                   
            LocalDateTime.now(),                   
            1,                                     
            criarTcc(),                           
            criarLocalizacao()                    
        );
    } 
    private Tcc criarTcc(){
        return new Tcc(
            1L,
            "Aplicação de Machine Learning em Análise de Dados Educacionais",
            "Machine Learning",
            "Este trabalho propõe o uso de algoritmos de aprendizado de máquina para análise de dados em ambientes educacionais...",
            1,
            criaUsuario(),
            criarCurso()
        );
    }

    private Curso criarCurso(){
        return new Curso(1L ,"S.I", 1);
    }

    private Usuario criaUsuario(){
        return new Usuario(
             1L,
             "João da Silva",
             "2021001",
             "joaosilva",
             "joao.silva@exemplo.com",
             "2000-05-10",
             "senhaSegura123",
             "(12) 00000-0000",
             List.of("ALUNO"),
             new Grupo(1L,"ALUNO",1),
             1
         );
    }

    public Localizacao criarLocalizacao() {
        return new Localizacao(
            1L,              
            "Prédio Central", 
            "Sala 101",      
            "1º Andar",      
            1                
        );
    }

    @BeforeEach
    public void setUp(){

        aprtUtil = mock(ApresentacaoUtil.class);
        aprtService = new ApresentacaoService(aprtUtil);
        MockitoAnnotations.openMocks(this);

        aprtCriada = criarApresentacao();
    
    }

    @Test
    @DisplayName("Deve lancar uma excecao caso nao tenha apresentacoes cadastradas")
    public void deveLancarExcecaoCasoNaoTenhaAprtCadastradas(){
        
        willThrow(new NaoExisteApresentacoesCadastradasException("Não existe Apresentações cadastradas"))
            .given(aprtUtil).countDeApresentacoes();

        assertThrows(NaoExisteApresentacoesCadastradasException.class ,
            ()-> aprtService.getAllApresentacao()
        );

        then(aprtUtil).should(times(1)).countDeApresentacoes();
    }

    @Test
    @DisplayName("Deve retornar duas excecoes caso o id seja invalido e caso a apresentacao nao existe ")
    public void deceRetornarDuasExcecoesCasoIdInvalidoCursoInexistente(){
    
        given(aprtUtil.validaId(null)).willThrow(new IdInvalidoException("O id da Apresentação informada é inválido") );
    
        assertThrows(IdInvalidoException.class , 
            ()-> aprtService.getApresentacao(null)
        );

        then(aprtUtil).should(times(1)).validaId(null);

        reset(aprtUtil);

        given(aprtUtil.validaId(aprtCriada.getId())).willReturn(true);
        given(aprtUtil.checkExistsApresentacao(aprtCriada.getId()))
            .willThrow(new ApresentacaoExisteException("Apresentacao já existe"));

        assertThrows(ApresentacaoExisteException.class , 
            ()-> aprtService.getApresentacao(aprtCriada.getId())
        );

        then(aprtUtil).should(times(1)).validaId(aprtCriada.getId());
        then(aprtUtil).should(times(1)).checkExistsApresentacao(aprtCriada.getId());


    }

    @Test
    @DisplayName("Deve retornar uma excecao caso apresentacao nao exista durate a exclusao ")
    public void deveLancarExcecaoCasoAprtNaoExistaExclusao(){
        
        given(aprtUtil.validaId(aprtCriada.getId())).willReturn(true);
        given(aprtUtil.checkExistsApresentacao(aprtCriada.getId()))
            .willThrow(new ApresentacaoExisteException("Apresentacao já existe"));

        assertThrows(ApresentacaoExisteException.class , 
            ()-> aprtService.deleteApresentacao(aprtCriada.getId())
        );

        then(aprtUtil).should(times(1)).validaId(aprtCriada.getId());
        then(aprtUtil).should(times(1)).checkExistsApresentacao(aprtCriada.getId());

    }

    @Test
    @DisplayName("Teste deve retornar duas excecoes caso a apresentacao tenha id invalido e se ela ja exista ")
    public void deveRetornarDuasAprtIdInvalidoAprtJaExiste(){

        given(aprtUtil.validaIdApresentacaoParaCriacao(aprtCriada.getId()))
            .willThrow(new IdInvalidoException("O id da Apresentação informado é inválido"));

        assertThrows(IdInvalidoException.class , 
            ()->aprtService.createApresentacao(aprtCriada)
        );
        
        then(aprtUtil).should(times(1)).validaIdApresentacaoParaCriacao(aprtCriada.getId());
   
        reset(aprtUtil);

        aprtCriada.setId(null);
        given(aprtUtil.validaIdApresentacaoParaCriacao(aprtCriada.getId()))
            .willReturn(true);

        given(aprtUtil.checkExistsApresentacaoParaCriacao(aprtCriada.getId()))
            .willThrow(new ApresentacaoExisteException("Apresentacao já existe"));

        assertThrows(ApresentacaoExisteException.class , 
            ()->aprtService.createApresentacao(aprtCriada)
        );
        
        then(aprtUtil).should(times(1)).validaIdApresentacaoParaCriacao(aprtCriada.getId());
        then(aprtUtil).should(times(1)).checkExistsApresentacaoParaCriacao(aprtCriada.getId());

    }

    @Test
    @DisplayName("Teste deve lancar excecao caso haja conflito de datas")
    public void deveLancarExcecaoCasoHajaConflitoDeDatas(){
    
        willThrow(new ConflitoEntreDatasException("Existe conflito de agendas"))
            .given(aprtUtil).checkConflictsDates( aprtCriada.getData(), aprtCriada.getHoraInicio() , aprtCriada.getHoraFim() );
    
        assertThrows(ConflitoEntreDatasException.class, 
            ()-> aprtService.createApresentacao(aprtCriada)
        );

        then(aprtUtil).should(times(1)).checkConflictsDates( aprtCriada.getData(), aprtCriada.getHoraInicio() , aprtCriada.getHoraFim() );
    }

    @Test
    @DisplayName("Teste deve retornar duas excecoes checando se exisete conflito de tcc e conflito de apresentacao")
    public void deveLancarDuasExcecoesChecandoConflitoDeTccEApresentacao(){

        given(aprtUtil.countConflitosDentroDeTcc(aprtCriada.getTcc().getId()))
            .willThrow(new TccJaTemApresentacaoException("O tcc já esta em outra apresentação"));

        assertThrows(TccJaTemApresentacaoException.class, 
            ()->aprtService.createApresentacao(aprtCriada)
        );

        then(aprtUtil).should(times(1)).countConflitosDentroDeTcc(aprtCriada.getTcc().getId());
        
        reset(aprtUtil);
        
        willThrow(new LocalizacaoIndisponivelException("Esta localização esta indisponível"))
            .given(aprtUtil).checkConflictsApresentacao(aprtCriada.getLocalizacao().getId());

        assertThrows(LocalizacaoIndisponivelException.class, 
            ()->aprtService.createApresentacao(aprtCriada)
        );

        then(aprtUtil).should(times(1)).checkConflictsApresentacao(aprtCriada.getLocalizacao().getId());
        
    }


    @Test
    @DisplayName("Teste desve retornar excecoes caso os id`s da apresentacao e se ele existe")
    public void deveRetornarExececoesCasoIdsSejaminvalidosEApresentacaoNaoExiste(){
    
        Long idOld = 2L;

        given(aprtUtil.validaId(aprtCriada.getId()))
            .willThrow(new IdInvalidoException("O id da Apresentação informada é inválido"));
    
        assertThrows(IdInvalidoException.class, 
            ()-> aprtService.updateApresentacao(idOld, aprtCriada)
        );
        
        then(aprtUtil).should(times(1)).validaId(aprtCriada.getId());
        
        reset(aprtUtil);
        
        given(aprtUtil.checkExistsApresentacaoParaCriacao(aprtCriada.getId()))
            .willThrow(new ApresentacaoExisteException("Apresentacao já existe"));

        assertThrows(ApresentacaoExisteException.class, 
            ()->aprtService.updateApresentacao(idOld, aprtCriada)
        );

        then(aprtUtil).should(times(1)).checkExistsApresentacaoParaCriacao(aprtCriada.getId());
   
        reset(aprtUtil);

        given(aprtUtil.validaId(idOld))
            .willThrow(new IdInvalidoException("O id da Apresentação informada é inválido"));
    
        assertThrows(IdInvalidoException.class, 
            ()-> aprtService.updateApresentacao(idOld, aprtCriada)
        );
        
        then(aprtUtil).should(times(1)).validaId(idOld);
        
        reset(aprtUtil);
        
        given(aprtUtil.checkExistsApresentacaoParaCriacao(idOld))
            .willThrow(new ApresentacaoExisteException("Apresentacao já existe"));

        assertThrows(ApresentacaoExisteException.class, 
            ()->aprtService.updateApresentacao(idOld, aprtCriada)
        );

        then(aprtUtil).should(times(1)).checkExistsApresentacaoParaCriacao(idOld);
   
    }

    @Test
    @DisplayName("Teste deve retornar uma excecao caso exista conflito dentro de tcc ")
    public void deveRetornarUmaExcecaoCasoTenhaConflitoTcc(){

        var idOld = 2L;

        given(aprtUtil.countConflitosDentroDeTcc(aprtCriada.getTcc().getId()))
            .willThrow(new TccJaTemApresentacaoException("O tcc já esta em outra apresentação"));
        
        assertThrows(TccJaTemApresentacaoException.class, 
            ()-> aprtService.updateApresentacao(idOld, aprtCriada)
        );

        then(aprtUtil).should(times(1)).countConflitosDentroDeTcc(aprtCriada.getTcc().getId());

    }

    @Test
    @DisplayName("Teste verificar se existe conflito de datas na atualizacao da apresentacao")
    public void verificaSeExisteConfitoEntreDatasAtuaizacaoDaAprt(){
     
        var idOld = 2L;
        LocalDateTime data = aprtCriada.getData();
        LocalTime horaComeco = aprtCriada.getHoraInicio();
        LocalTime horasFim = aprtCriada.getHoraFim();

        willThrow(new ConflitoEntreDatasException("Existe conflito de agendas")).
        given(aprtUtil).checkConflictsDates(data, horaComeco, horasFim);

        assertThrows(ConflitoEntreDatasException.class, 
            ()->aprtService.updateApresentacao(idOld, aprtCriada)
        );

        then(aprtUtil).should(times(1)).checkConflictsDates(data, horaComeco, horasFim);
        
    }

    @Test
    @DisplayName("Teste deve retornar excecao caso o tenha conflito de apresentacao")
    public void deveRetornarExcecaoCasoTenhaConflitoDeAprt(){

        var idOld = 2L;

        willThrow(new LocalizacaoIndisponivelException("Esta localização esta indisponível"))
            .given(aprtUtil).checkConflictsApresentacao(aprtCriada.getLocalizacao().getId());

        assertThrows(LocalizacaoIndisponivelException.class, 
            ()->aprtService.updateApresentacao(idOld, aprtCriada) 
        );

        then(aprtUtil).should(times(1)).checkConflictsApresentacao(aprtCriada.getLocalizacao().getId());

    }

}
