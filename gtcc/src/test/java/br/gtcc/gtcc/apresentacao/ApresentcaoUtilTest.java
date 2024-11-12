package br.gtcc.gtcc.apresentacao;

import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.model.mysql.Curso;
import br.gtcc.gtcc.model.mysql.Grupo;
import br.gtcc.gtcc.model.mysql.Localizacao;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.model.mysql.Usuario;
import br.gtcc.gtcc.model.mysql.repository.ApresentacaoRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.gtcc.gtcc.util.services.ApresentacaoUtil;
import lombok.RequiredArgsConstructor;
import br.gtcc.gtcc.util.exceptions.IdInvalidoException;
import br.gtcc.gtcc.util.exceptions.apresentacao.ApresentacaoExisteException;
import br.gtcc.gtcc.util.exceptions.apresentacao.ApresentacaoInvalidaException;
import br.gtcc.gtcc.util.exceptions.apresentacao.ApresentacaoNaoException;
import br.gtcc.gtcc.util.exceptions.apresentacao.ConflitoEntreDatasException;
import br.gtcc.gtcc.util.exceptions.apresentacao.NaoExisteApresentacoesCadastradasException;
import br.gtcc.gtcc.util.exceptions.localizacao.LocalizacaoIndisponivelException;
import br.gtcc.gtcc.util.exceptions.tcc.TccJaTemApresentacaoException;
import br.gtcc.gtcc.model.mysql.repository.ApresentacaoRepository;

//@ExtendWith(MockitoExtension.class)
public class ApresentcaoUtilTest {

    //@InjectMocks
    private ApresentacaoUtil aprtUtil;

    //@Mock
    private ApresentacaoRepository aprtRepository;

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

        aprtRepository = mock(ApresentacaoRepository.class);
        aprtUtil = new ApresentacaoUtil(
            aprtRepository, null ,null ,null
        );
        MockitoAnnotations.openMocks(this);
        
        aprtCriada = criarApresentacao();
        aptrSemLocCriada = criarApresentacao();

    }

    @Test
    @DisplayName("Teste")
    public void deveRetornarUmaExcecaoCasoSejaPassadoUmaApresentacaoNula(){
        
        given(aprtRepository.save(aprtCriada)).willThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class , 
            () ->  aprtUtil.salvar(aprtCriada)
        );

        then(aprtRepository).should(times(1)).save(aprtCriada);

    }

    @Test
    @DisplayName("Deve lancar excecao caso seja passar um id invalido ao deletar aprentacao")
    public void deveExcecaoCasoIdSejaInvalido(){
        
        willThrow(new IllegalArgumentException()).given(aprtRepository).deleteById(aprtCriada.getId());

        assertThrows(IllegalArgumentException.class , 
            () ->  aprtUtil.delete(aprtCriada.getId())
        );
        
        then(aprtRepository).should(times(1)).deleteById(aprtCriada.getId());

    }

    @Test
    @DisplayName("Deve lancar execao caso seja passado uma apresentacao nula ")
    public void deveLancarExcecaoCasoAprSejaNula(){

        assertThrows(ApresentacaoInvalidaException.class , 
            ()-> aprtUtil.apresentationIsNull(null)
        );
    } 

    @Test 
    @DisplayName("Deve lancar uma excecao caso o id seja diferente de nulo")
    public void deveLancarExcecaoCasoIdSejaDifenrenteNulo(){
        
        assertThrows(IdInvalidoException.class,
        ()-> aprtUtil.validaIdApresentacaoParaCriacao(aprtCriada.getId())
        );
        
    }

    @Test 
    @DisplayName("Nao deve lancar uma excecao caso o id seja igual a nulo")
    public void deveNaoLancarExcecaoCasoIdSejaIgualNulo(){
        
        assertDoesNotThrow(()-> aprtUtil.validaIdApresentacaoParaCriacao(null));
        Boolean isValid = aprtUtil.validaIdApresentacaoParaCriacao(null);
        assertTrue(isValid);

    }

    @Test
    @DisplayName("Deve lancar uma excecao caso o id seja igual nulo metodo validaId()")
    public void deveLancarExcecaoCasoIdSejaIgualNulo(){
        assertThrows( IdInvalidoException.class, 
            ()->aprtUtil.validaId(null)
        );
    }

    @Test
    @DisplayName("Deve retornar uma lista com um objeto localizacao sem apresentacaio")
    public void deveRetornarUmaListaComUmObjetoLocalizacao(){
    
        aptrSemLocCriada.setLocalizacao(null);

        given(aprtRepository.buscarApresentacaoSemLocalizacao()).willReturn(Collections.singletonList(aptrSemLocCriada));

        List<Apresentacao> listAptrSemLoc = aprtUtil.buscarApresentacaoSemLocalizacao();
        assertTrue(listAptrSemLoc.size() > 0);

        then(aprtRepository).should(times(1)).buscarApresentacaoSemLocalizacao();
    }

    @Test
    @DisplayName("Deve retornar uma excecao caso apresentcao nao exista ")
    public void deveLancarExcecaoCasoAptrNaoExista(){

        given(aprtRepository.existsById(aprtCriada.getId()))
            .willReturn(false);


        assertThrows(ApresentacaoNaoException.class , 
            ()-> aprtUtil.checkExistsApresentacao(aprtCriada.getId())
        );
        
        then(aprtRepository).should(times(1))
            .existsById(aprtCriada.getId());

    }

    @Test
    @DisplayName("Deve retornar excecao caso apresentcao ja exista ")
    public void deveLancarExcecaoCasoApresentacaoExista(){
    
        given(aprtRepository.existsById(aprtCriada.getId())).willReturn(false);
    
        assertThrows(ApresentacaoExisteException.class, 
            ()->aprtUtil.checkExistsApresentacaoParaCriacao(aprtCriada.getId())
        );

        then(aprtRepository).should(times(1)).existsById(aprtCriada.getId());
    }

    @Test
    @DisplayName("Deve retornar uma excecao caso o tcc jas esteja em outra apresentacao")
    public void deveLancarExcecaoCasoTccEstejaEmOutraAprt(){
        
        given(aprtRepository.countConflictTccs(aprtCriada.getId())).willReturn(1L);

        assertThrows( TccJaTemApresentacaoException.class, 
            ()->aprtUtil.countConflitosDentroDeTcc(aprtCriada.getId())
        );
        
        then(aprtRepository).should(times(1)).countConflictTccs(aprtCriada.getId());
    }
    
    @Test
    @DisplayName("Deve lancar excecao caso o nao exista apresentcoes cadastradas")
    public void deveLancarExcecaoCasoNaoExistaAprtCadastradas(){
        given(aprtRepository.count()).willReturn(0L);

        assertThrows(NaoExisteApresentacoesCadastradasException.class,
            ()->aprtUtil.countDeApresentacoes()
        );

        then(aprtRepository).should(times(1)).count();
    }

    @Test
    @DisplayName("Deve lancar excecao caso o exista conflito de datas")
    public void deveLancarExcecaoCasoTenhaConflitoDatas(){

        LocalDateTime date = LocalDateTime.of(2024, 11, 12, 10, 0); 
        LocalTime horasComeco = LocalTime.of(10, 0);
        LocalTime horasFim = LocalTime.of(12, 0);

        given(aprtRepository.countDates(date, horasComeco, horasFim)).willReturn(1L);

        assertThrows(ConflitoEntreDatasException.class,
            ()->aprtUtil.checkConflictsDates(date, horasComeco, horasFim)
        );

        then(aprtRepository).should(times(1))
            .countDates(date, horasComeco, horasFim);
    
    }

    @Test
    @DisplayName("Deve lancar excecao caso tenha conflito de localizacao indisponivel")
    public void deveLancarExcecaoCasoLocalizacaoEstejaIndisponível(){
        given(aprtRepository.checkConflictLocalizacao(aprtCriada.getId()))
            .willReturn(1L);

        assertThrows( LocalizacaoIndisponivelException.class, 
            ()-> aprtUtil.checkConflictsApresentacao(aprtCriada.getId())
        );        

        then(aprtRepository).should(times(1))
            .checkConflictLocalizacao(aprtCriada.getId());
    }


    @Test
    @DisplayName("Deve lancar nada caso o id da localizacao seja nulo")
    public void deveLancarNadaCasoIdLocalizacaoEstejaNulo(){

        assertDoesNotThrow(  
            ()-> aprtUtil.checkConflictsApresentacao(null)
        );        

    }
}
