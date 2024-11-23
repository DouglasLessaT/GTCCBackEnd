package br.gtcc.gtcc.services.impl.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.mysql.Banca;
import br.gtcc.gtcc.model.mysql.Convite;
import br.gtcc.gtcc.model.mysql.DocenteBanca;
import br.gtcc.gtcc.model.mysql.DocenteEnum;
import br.gtcc.gtcc.model.mysql.StatusConvite;
import br.gtcc.gtcc.model.mysql.repository.ConviteRepository;
import br.gtcc.gtcc.model.mysql.repository.DocenteBancaRepository; // Importando o repositório
import br.gtcc.gtcc.services.spec.ConviteInterface;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConviteService implements ConviteInterface<Convite, Long> {

    @Autowired
    private ConviteRepository conviteRepository;

    @Autowired
    private DocenteBancaRepository docenteBancaRepository; // Injetando o repositório

    @Override
    public Convite enviarConvite(Convite convite) {
        convite.setCriacaoConvite(LocalDateTime.now());
        return conviteRepository.save(convite);
    }

    @Override
    public Convite aceitarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
            .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
    
        validarDatas(convite.getCriacaoConvite(), LocalDateTime.now(), convite.getValidateDate());
    
        convite.setStatus(StatusConvite.ACCEPTED);
        convite.setAcceptedDate(LocalDateTime.now());
    
        // Adiciona o docente à banca
        DocenteBanca docenteBanca = new DocenteBanca();
        docenteBanca.setUsuario(convite.getDestino()); // Definindo o usuário do docente
        docenteBanca.setTipoDocente(DocenteEnum.AVALIADOR_INTERNO); // Tipo do docente
        docenteBanca.setAtivo(1); // Define o status do docente
    
        // Acessando a banca da apresentação
        Banca banca = convite.getDestinoDocente().getBanca();
        if (banca != null) {
            banca.adicionarDocente(docenteBanca); // Adiciona o docente à banca
        } else {
            throw new RuntimeException("Banca não encontrada na apresentação.");
        }
    
        // Salva o docente na banca e o convite
        docenteBancaRepository.save(docenteBanca);
        conviteRepository.save(convite);
    
        return convite;
    }

    @Override
    public Convite visualizarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        validarDatas(convite.getCriacaoConvite(), LocalDateTime.now(), null);

        convite.setStatus(StatusConvite.VIEWED);
        convite.setValidateDate(LocalDateTime.now());
        return conviteRepository.save(convite);
    }

    @Override
    public Convite recusarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        validarDatas(convite.getCriacaoConvite(), LocalDateTime.now(), convite.getValidateDate());

        convite.setStatus(StatusConvite.DENIED);
        convite.setValidateDate(LocalDateTime.now());
        return conviteRepository.save(convite);
    }

    @Override
    public Convite atualizarConvite(Convite convite, Long conviteId) {
        Convite conviteExistente = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        validarDatas(conviteExistente.getCriacaoConvite(), convite.getValidateDate(), convite.getAcceptedDate());

        conviteExistente.setStatus(convite.getStatus());
        conviteExistente.setValidateDate(LocalDateTime.now());
        return conviteRepository.save(conviteExistente);
        
    }

    private void validarDatas(LocalDateTime criacaoConvite, LocalDateTime validateDate, LocalDateTime acceptedDate) {
        if (validateDate != null && validateDate.isBefore(criacaoConvite)) {
            throw new IllegalArgumentException("A data de visualização deve ser após a data de criação.");
        }
        if (acceptedDate != null && (acceptedDate.isBefore(criacaoConvite) || acceptedDate.isBefore(validateDate))) {
            throw new IllegalArgumentException("A data de aceitação deve ser após a data de criação e a data de visualização.");
        }
    }

    @Override
    public Convite deletarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
        .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
        conviteRepository.deleteById(conviteId);
        return convite;
    }

    @Override
    public List<Convite> getTodosConvites() {
        return conviteRepository.findAll();
    }

    @Override
    public Convite getConvite(Long conviteId) {
        return conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
    }

    @Override
    public List<Convite> getConvitesPendentes() {
        return conviteRepository.findByStatus(StatusConvite.INVITED);
    }

    @Override
    public List<Convite> getConvitesAceitos() {
        return conviteRepository.findByStatus(StatusConvite.ACCEPTED);
    }

    @Override
    public List<Convite> getConvitesRecusados() {
        return conviteRepository.findByStatus(StatusConvite.DENIED);
    }

    @Override
    public List<Convite> getConvitesVisualizados() {
        return conviteRepository.findByStatus(StatusConvite.VIEWED);
    }
    
}