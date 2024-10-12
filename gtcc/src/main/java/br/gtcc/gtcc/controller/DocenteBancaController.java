package br.gtcc.gtcc.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.DocenteBanca;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.services.spec.DocenteBancaInterface;
import br.gtcc.gtcc.services.spec.TccInterface;
import br.gtcc.gtcc.util.Console;
import br.gtcc.gtcc.util.UtilController;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@ValidaAcesso("ROLE_COORDENADOR")
@RequestMapping("coordenacao/tcc/v1/docente-banca")
@RequiredArgsConstructor
public class DocenteBancaController {
    
    private final DocenteBancaInterface docenteBancaInterface;

    @PostMapping("/")
    public ResponseEntity<Object> createDocenteBanca(@RequestBody DocenteBanca docenteBanca) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) docenteBancaInterface.createDocenteBanca(docenteBanca));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Docente criado com sucesso", "Erro ao criar Docente");
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDocenteBanca(@PathVariable Long idDocenteBanca, @RequestBody DocenteBanca docenteBanca) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) docenteBancaInterface.updateDocenteBanca(idDocenteBanca ,docenteBanca));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Docente Alterado com sucesso", "Erro ao Alterar Docente");
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDocenteBanca(@PathVariable Long idDocenteBanca) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) docenteBancaInterface.getDocenteBanca(idDocenteBanca));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Docente encontrado com sucesso", "Erro ao encontrado Docente");
        
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllDocenteBanca() {
        
        @SuppressWarnings("unchecked")
        Optional<List<Tcc>> createdTcc = Optional.ofNullable((List<Tcc>) docenteBancaInterface.getAllDocenteBanca());
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Docentes ", "Erro ao buscar Docentes");
        
    }

}
