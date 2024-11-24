package br.gtcc.gtcc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Docente Banca Controller", description = "Endpoints para gerenciar docentes de bancas, ao buscar um docente banca ele tras o tcc referente a cada docente registrado e o usuario ")
@RequestMapping("coordenacao/tcc/v1/docente-banca")
@RequiredArgsConstructor
public class DocenteBancaController {
    
    private final DocenteBancaInterface docenteBancaInterface;

    @Operation(summary ="Dado um objeto Docente Banca ele criar um tcc, certifique-se de passar o id como zero ")
    @PostMapping("/")
    public ResponseEntity<Object> createDocenteBanca(@RequestBody DocenteBanca docenteBanca) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) docenteBancaInterface.createDocenteBanca(docenteBanca));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Banca Docente criado com sucesso", "Erro ao criar Banca Docente");
        
    }

    @Operation(summary ="Dado um objeto Docente Banca e o id ele atualiza o banca referente ao id passado com o objeto no parametro")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDocenteBanca(@PathVariable Long idDocenteBanca, @RequestBody DocenteBanca docenteBanca) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) docenteBancaInterface.updateDocenteBanca(idDocenteBanca ,docenteBanca));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Banca Docentes Alterado com sucesso", "Erro ao Alterar Banca Docentes");
        
    }

    @Operation(summary ="Dado um objeto Docente Banca e o id ele atualiza o banca referente ao id passado com o objeto no parametro")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDocenteBanca(@PathVariable Long idDocenteBanca) {
        
        @SuppressWarnings("unchecked")
        Optional<Tcc> createdTcc = Optional.ofNullable((Tcc) docenteBancaInterface.getDocenteBanca(idDocenteBanca));
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, " Banca Docentes encontrado com sucesso", "Erro ao encontrado Docente");
        
    }

    @Operation(summary ="Dado um objeto Docente Banca e o id ele atualiza o Docente Banca referente ao id passado com o objeto no parametro")
    @GetMapping("/")
    public ResponseEntity<Object> getAllDocenteBanca() {
        
        @SuppressWarnings("unchecked")
        Optional<List<Tcc>> createdTcc = Optional.ofNullable((List<Tcc>) docenteBancaInterface.getAllDocenteBanca());
        return UtilController.buildResponseFromOptional( createdTcc, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de Banca Docentes ", "Erro ao buscar  Banca Docentes");
        
    }

}
