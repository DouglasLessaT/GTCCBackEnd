package br.gtcc.gtcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Convite;
import br.gtcc.gtcc.services.impl.mysql.ConviteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("coordenacao/tcc/v1")
@ValidaAcesso("ROLE_PROFESSOR")
@RequiredArgsConstructor
public class ConviteController {
        @Autowired
    private ConviteService conviteService;

    @PostMapping
    public ResponseEntity<Convite> enviarConvite(@RequestBody Convite convite) {
        Convite novoConvite = conviteService.enviarConvite(convite);
        return new ResponseEntity<>(novoConvite, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/aceitar")
    public ResponseEntity<Convite> aceitarConvite(@PathVariable Long id) {
        Convite conviteAceito = conviteService.aceitarConvite(id);
        return new ResponseEntity<>(conviteAceito, HttpStatus.OK);
    }

    @PutMapping("/{id}/visualizar")
    public ResponseEntity<Convite> visualizarConvite(@PathVariable Long id) {
        Convite conviteVisualizado = conviteService.visualizarConvite(id);
        return new ResponseEntity<>(conviteVisualizado, HttpStatus.OK);
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<Convite> recusarConvite(@PathVariable Long id) {
        Convite conviteRecusado = conviteService.recusarConvite(id);
        return new ResponseEntity<>(conviteRecusado, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Convite> atualizarConvite(@RequestBody Convite convite, @PathVariable Long id) {
        Convite conviteAtualizado = conviteService.atualizarConvite(convite, id);
        return new ResponseEntity<>(conviteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConvite(@PathVariable Long id) {
        conviteService.deletarConvite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Convite>> getTodosConvites() {
        List<Convite> convites = conviteService.getTodosConvites();
        return new ResponseEntity<>(convites, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Convite> getConvite(@PathVariable Long id) {
        Convite convite = conviteService.getConvite(id);
        return new ResponseEntity<>(convite, HttpStatus.OK);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Convite>> getConvitesPendentes() {
        List<Convite> convitesPendentes = conviteService.getConvitesPendentes();
        return new ResponseEntity<>(convitesPendentes, HttpStatus.OK);
    }

    @GetMapping("/aceitos")
    public ResponseEntity<List<Convite>> getConvitesAceitos() {
        List<Convite> convitesAceitos = conviteService.getConvitesAceitos();
        return new ResponseEntity<>(convitesAceitos, HttpStatus.OK);
    }

    @GetMapping("/recusados")
    public ResponseEntity<List<Convite>> getConvitesRecusados() {
        List<Convite> convitesRecusados = conviteService.getConvitesRecusados();
        return new ResponseEntity<>(convitesRecusados, HttpStatus.OK);
    }

    @GetMapping("/visualizados")
    public ResponseEntity<List<Convite>> getConvitesVisualizados() {
        List<Convite> convitesVisualizados = conviteService.getConvitesVisualizados();
        return new ResponseEntity<>(convitesVisualizados, HttpStatus.OK);
    }


// POST /convites: Envia um novo convite.
// PUT /convites/{id}/aceitar: Aceita um convite pelo id.
// PUT /convites/{id}/visualizar: Marca um convite como visualizado.
// PUT /convites/{id}/recusar: Recusa um convite.
// PUT /convites/{id}: Atualiza um convite existente pelo id.
// DELETE /convites/{id}: Deleta um convite pelo id.
// GET /convites: Obtém todos os convites.
// GET /convites/{id}: Obtém um convite específico pelo id.
// GET /convites/pendentes: Lista convites pendentes.
// GET /convites/aceitos: Lista convites aceitos.
// GET /convites/recusados: Lista convites recusados.
// GET /convites/visualizados: Lista convites visualizados.
}
