package br.gtcc.gtcc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Localizacao;
import br.gtcc.gtcc.services.spec.LocalizacaoInterface;
import lombok.RequiredArgsConstructor;

@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@Tag(name = "Localização Controller", description = "Endpoints para gerenciar localização, ao buscar uma localização ele tras todas as informações sobre andar, predio e sala")
@RequestMapping("coordenacao/tcc/v1/localizacoes")
@RequiredArgsConstructor
public class LocalizacaoController {
    private final LocalizacaoInterface localizacaoService;

    @Operation(summary ="Dado um objeto localização e o id ele cria uma localização no banco")
    @PostMapping
    public ResponseEntity<Localizacao> criarLocalizacao(@RequestBody Localizacao localizacao) {
        try {
            Localizacao novaLocalizacao = localizacaoService.salvar(localizacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaLocalizacao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary ="ELe lista todas as localizações do banco")
    @GetMapping
    public ResponseEntity<List<Localizacao>> listarLocalizacoes() {
        List<Localizacao> localizacoes = localizacaoService.listar();
        return ResponseEntity.ok(localizacoes);
    }

    @Operation(summary ="Dado um id ele busca a localização referente a esse id ")
    @GetMapping("/{id}")
    public ResponseEntity<Localizacao> buscarPorId(@PathVariable Long id) {
        try {
            Localizacao localizacao = localizacaoService.buscarPorId(id);
            return ResponseEntity.ok(localizacao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary ="Dado um id e o objeto com as alterações ele buscar o objeto referente ao id e aplica as alterações do objeto que esta sendo passado")
    @PutMapping("/{id}")
    public ResponseEntity<Localizacao> atualizarLocalizacao(@PathVariable Long id, @RequestBody Localizacao localizacao) {
        try {
            Localizacao localizacaoAtualizada = localizacaoService.updateLocalizacao(id, localizacao);
            return ResponseEntity.ok(localizacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary ="Dado um id ele exclui a localiuzação do banco  ")
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
