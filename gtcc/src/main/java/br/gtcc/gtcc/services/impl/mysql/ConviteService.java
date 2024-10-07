package br.gtcc.gtcc.services.impl.mysql;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gtcc.gtcc.model.mysql.Convite;
import br.gtcc.gtcc.model.mysql.StatusConvite;
import br.gtcc.gtcc.model.mysql.repository.ConviteRepository;
import br.gtcc.gtcc.services.spec.ConviteInterface;

@Service
public class ConviteService implements ConviteInterface<Convite, Long> {

    @Autowired
    private ConviteRepository conviteRepository;

    @Override
    public Convite enviarConvite(Convite convite) {
        convite.setStatus(StatusConvite.INVITED);
        convite.setCriacaoConvite(LocalDateTime.now());
        return conviteRepository.save(convite);
    }

    @Override
    public Convite aceitarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
        convite.setStatus(StatusConvite.ACCEPTED);
        convite.setAcceptedDate(LocalDateTime.now());
        return conviteRepository.save(convite);
    }

    @Override
    public Convite visualizarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
        convite.setStatus(StatusConvite.VIEWED);
        convite.setValidateDate(LocalDateTime.now());
        return conviteRepository.save(convite);
    }

    @Override
    public Convite recusarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
        convite.setStatus(StatusConvite.DENIED);
        convite.setValidateDate(LocalDateTime.now());
        return conviteRepository.save(convite);
    }

    @Override
    public Convite atualizarConvite(Convite convite, Long conviteId) {
        Convite conviteExistente = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
        conviteExistente.setStatus(convite.getStatus());
        conviteExistente.setValidateDate(LocalDateTime.now());
        return conviteRepository.save(conviteExistente);
    }

    @Override
    public Convite deletarConvite(Long conviteId) {
        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));
        conviteRepository.delete(convite);
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
