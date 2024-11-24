package br.gtcc.gtcc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;
import br.gtcc.gtcc.annotations.ValidaAcesso;
import br.gtcc.gtcc.model.mysql.Apresentacao;
import br.gtcc.gtcc.services.spec.ApresentacaoInterface;
import br.gtcc.gtcc.util.Console; 
import br.gtcc.gtcc.util.UtilController;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@Tag(name = "Apresentacão Controller", description = "Rotas para gerenciamento de apresentação um objeto apresentação conten hora fim, hora começo, " +
        " data, data de criação, tcc e localização")
@RequestMapping("coordenacao/tcc/v1")
@RequiredArgsConstructor
public class ApresentacaoController {

    @SuppressWarnings("rawtypes")
    private final ApresentacaoInterface interfaceBanca;

    @Operation(summary ="Dado um objeto apresentacao ele criar um regitros no banco da apresentação ")
    @PostMapping("/apresentacao")
    public ResponseEntity<Object> createApresentantion(@RequestBody Apresentacao apresentation) {

        @SuppressWarnings("unchecked")
        Optional<Apresentacao> createdApresentationBanca = Optional.ofNullable((Apresentacao) interfaceBanca.createApresentacao(apresentation));
        return UtilController.buildResponseFromOptional( createdApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação criada com sucesso", "Erro ao criar apresentação");
        
    }

    @Operation(summary ="Dado um id da apresentacao ele deleta a apresentacao da base")
    @DeleteMapping("/apresentacao/{id}")
    public ResponseEntity<Object> deleteApresentationBanca(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<Apresentacao> deletedApresentationBanca = Optional.ofNullable((Apresentacao) interfaceBanca.deleteApresentacao(id));
        return UtilController.buildResponseFromOptional( deletedApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação deletada com sucesso", "Erro ao deletar apresentação");
        
    }

    @Operation(summary ="Dado um id da apresentacao que sera modificada e o objeto que com as informações novas essa rota pega o objeto da base a atuliza ela com as informações passadas")
    @PutMapping("/apresentacao/{id}")
    public ResponseEntity<Object> updateApresentationBanca(@PathVariable("id") String id,@RequestBody(required = true) Apresentacao apresentation) {

        @SuppressWarnings("unchecked")
        Optional<Apresentacao> updatedApresentationBanca = Optional.ofNullable((Apresentacao) interfaceBanca.updateApresentacao(id, apresentation));
        return UtilController.buildResponseFromOptional( updatedApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação alterada com sucesso", "Erro ao alterar apresentação");

    }

    @Operation(summary ="Dado um id da apresentacao ele recupear esse valor do banco")
    @GetMapping("/apresentacao/{id}")
    public ResponseEntity<Object> getApresentationBancaById(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<Apresentacao> getApresentationBanca = Optional.ofNullable((Apresentacao) interfaceBanca.getApresentacao(id));
        return UtilController.buildResponseFromOptional( getApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação encontrada", "Erro ao buscar apresentação");

    }

    @Operation(summary ="Dado um id da apresentacao ele recupear esse valor do banco")
    @GetMapping("/apresentacoes")
    public ResponseEntity<Object> getAllApresentationBanca() {

        @SuppressWarnings("unchecked")
        Optional<List<Apresentacao>> getApresentationBancaList = Optional.ofNullable((List<Apresentacao>) interfaceBanca.getAllApresentacao());
        return UtilController.buildResponseFromOptional( getApresentationBancaList, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de apresentação", "Lista Vazia");

    }
    
}
