package br.gtcc.gtcc.controller;


import br.gtcc.gtcc.model.mysql.Banca;
import br.gtcc.gtcc.model.mysql.DTO.BancaRequest;
import br.gtcc.gtcc.model.mysql.Tcc;
import br.gtcc.gtcc.services.impl.mysql.BancaService;
import br.gtcc.gtcc.util.UtilController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name="Banca Controller",description = "Essa entidade abstrai a entidade tcc para complemnetar a entidade doencente banca")
@RequestMapping("coordenacao/tcc/v1/banca")
public class BancaController {

    private final BancaService bancaService;

    @Operation(summary ="Dado um objeto banca ele criar um tcc, certifique-se de passar o id como zero ")
    @PostMapping("/")
    public ResponseEntity<Object> createTcc(@RequestBody BancaRequest banca) {
        log.info("Teste model "+ banca.toString());
        @SuppressWarnings("unchecked")
        Optional<Banca> createBanca = Optional.ofNullable((Banca) bancaService.create(banca));
        return UtilController.buildResponseFromOptional( createBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Banca criado com sucesso", "Erro ao criar banca");

    }

    @Operation(summary ="Dado um objeto banca e o id ele atualiza o banca referente ao id passado com o objeto no parametro")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatBanca(@PathVariable("id") Long id, @RequestBody  BancaRequest banca) {

        @SuppressWarnings("unchecked")
        Optional<Banca> updatedBanca = Optional.ofNullable((Banca) bancaService.update(banca, id));
        return UtilController.buildResponseFromOptional( updatedBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Banca alterado com sucesso", "Erro ao alterar banca");

    }

    @Operation(summary ="Dado o id do Banca ele deleta o tcc referente ao id passado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBanca(@PathVariable("id") Long id) {

        @SuppressWarnings("unchecked")
        Optional<Banca> deletedBanca = Optional.ofNullable((Banca) bancaService.delete(id));
        return UtilController.buildResponseFromOptional( deletedBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Banca deletado com sucesso", "Erro ao deletar tcc");

    }

    @Operation(summary ="Retornar todos os Banca's")
    @GetMapping("/")
    public ResponseEntity<Object> getAllTccs() {

        @SuppressWarnings("unchecked")
        Optional<List<Banca>> list = Optional.ofNullable( bancaService.getAll());
        return UtilController.buildResponseFromOptional( list, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de banca", "Lista Vazia");

    }

    @Operation(summary = "Busca um Banca por ID", description = "Retorna um Banca baseado no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {

        @SuppressWarnings("unchecked")
        Optional<Banca> foundBanca = Optional.ofNullable( (Banca) bancaService.getById(id) ) ;
        return UtilController.buildResponseFromOptional( foundBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Banca econtrado", "Banca não encontrada");

    }


}

