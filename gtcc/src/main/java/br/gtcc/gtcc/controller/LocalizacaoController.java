package br.gtcc.gtcc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Localizacao;
import br.gtcc.gtcc.services.spec.LocalizacaoInterface;
import lombok.RequiredArgsConstructor;

@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@RequestMapping("coordenacao/tcc/v1/localizacoes")
@RequiredArgsConstructor
public class LocalizacaoController {
    private final LocalizacaoInterface localizacaoService;

    // Criar uma nova localização
    @PostMapping
    public ResponseEntity<Localizacao> criarLocalizacao(@RequestBody Localizacao localizacao) {
        try {
            Localizacao novaLocalizacao = localizacaoService.salvar(localizacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaLocalizacao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Listar todas as localizações
    @GetMapping
    public ResponseEntity<List<Localizacao>> listarLocalizacoes() {
        List<Localizacao> localizacoes = localizacaoService.listar();
        return ResponseEntity.ok(localizacoes);
    }

    // Buscar localização por ID
    @GetMapping("/{id}")
    public ResponseEntity<Localizacao> buscarPorId(@PathVariable Long id) {
        try {
            Localizacao localizacao = localizacaoService.buscarPorId(id);
            return ResponseEntity.ok(localizacao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Atualizar uma localização existente
    @PutMapping("/{id}")
    public ResponseEntity<Localizacao> atualizarLocalizacao(@PathVariable Long id, @RequestBody Localizacao localizacao) {
        try {
            Localizacao localizacaoAtualizada = localizacaoService.updateLocalizacao(id, localizacao);
            return ResponseEntity.ok(localizacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Deletar uma localização
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLocalizacao(@PathVariable Long id) {
        try {
            localizacaoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
