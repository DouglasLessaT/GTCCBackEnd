package br.gtcc.gtcc.controller;

import br.gtcc.gtcc.model.mysql.Grupo;
import br.gtcc.gtcc.services.impl.mysql.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public List<Grupo> getAllGrupos() {
        return grupoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable Long id) {
        return grupoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Grupo createGrupo(@RequestBody Grupo grupo) {
        return grupoService.save(grupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable Long id, @RequestBody Grupo grupo) {
        return grupoService.findById(id)
                .map(existingGrupo -> {
                    grupo.setId(existingGrupo.getId());
                    return ResponseEntity.ok(grupoService.save(grupo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        if (grupoService.findById(id).isPresent()) {
            grupoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}