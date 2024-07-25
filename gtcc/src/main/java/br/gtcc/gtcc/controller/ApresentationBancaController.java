package br.gtcc.gtcc.controller;

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
import br.gtcc.gtcc.model.neo4j.ApresentationBanca;
import br.gtcc.gtcc.services.spec.ApresentationBancaInterface;
import br.gtcc.gtcc.util.Console;
import br.gtcc.gtcc.util.UtilController;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@ValidaAcesso("ROLE_PROFESSOR")
@RequestMapping("coordenacao/tcc/v1")
public class ApresentationBancaController {

    @SuppressWarnings("rawtypes")
    @Autowired
    private ApresentationBancaInterface interfaceBanca;

    @PostMapping("/apresentacao")
    public ResponseEntity<Object> createApresentantion(@RequestBody ApresentationBanca apresentation) {

        @SuppressWarnings("unchecked")
        Optional<ApresentationBanca> createdApresentationBanca = Optional.ofNullable((ApresentationBanca) interfaceBanca.createApresentationBanca(apresentation));
        return UtilController.buildResponseFromOptional( createdApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação criada com sucesso", "Erro ao criar apresentação");
        
    }

    @DeleteMapping("/apresentacao/{id}")
    public ResponseEntity<Object> deleteApresentationBanca(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<ApresentationBanca> deletedApresentationBanca = Optional.ofNullable((ApresentationBanca) interfaceBanca.deleteApresentationBanca(id));
        return UtilController.buildResponseFromOptional( deletedApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação deletada com sucesso", "Erro ao deletar apresentação");
        
    }

    @PutMapping("/apresentacao/{id}")
    public ResponseEntity<Object> updateApresentationBanca(@PathVariable("id") String id,@RequestBody(required = true) ApresentationBanca apresentation) {

        @SuppressWarnings("unchecked")
        Optional<ApresentationBanca> updatedApresentationBanca = Optional.ofNullable((ApresentationBanca) interfaceBanca.updateApresentationBanca(id, apresentation));
        return UtilController.buildResponseFromOptional( updatedApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação alterada com sucesso", "Erro ao alterar apresentação");

    }

    @GetMapping("/apresentacao/{id}")
    public ResponseEntity<Object> getApresentationBancaById(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<ApresentationBanca> getApresentationBanca = Optional.ofNullable((ApresentationBanca) interfaceBanca.getApresentationBanca(id));
        return UtilController.buildResponseFromOptional( getApresentationBanca, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Apresentação encontrada", "Erro ao buscar apresentação");

    }

    @GetMapping("/apresentacoes")
    public ResponseEntity<Object> getAllApresentationBanca() {

        @SuppressWarnings("unchecked")
        Optional<List<ApresentationBanca>> getApresentationBancaList = Optional.ofNullable((List<ApresentationBanca>) interfaceBanca.getAllApresentationBanca());
        return UtilController.buildResponseFromOptional( getApresentationBancaList, HttpStatus.OK, HttpStatus.BAD_REQUEST, "Lista de apresentação", "Lista Vazia");

    }

    @GetMapping("/apresentacao-titulo-nome/{id}")
    public ResponseEntity<Object> getTituloENomeOrientador(@PathVariable("id") String id) {

        @SuppressWarnings("unchecked")
        Optional<String> nome = Optional.ofNullable((String) interfaceBanca.getNomeOrintadorPeloIdDaApresentacao(id));
        @SuppressWarnings("unchecked")
        Optional<String> titulo = Optional.ofNullable((String) interfaceBanca.getTccTitlePeloIdDaApresentacao(id));

        boolean nomeIsPresent = nome.isPresent() ;
        boolean tituloIsPresent = titulo.isPresent();

        if (nomeIsPresent!= false && tituloIsPresent != false) {

            String resultado = ""+nome.get()+":"+titulo.get();
            Console.log(resultado);
            return new ResponseEntity<>(resultado, HttpStatus.OK);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sem titlo ou nome encontrados");

        }

    }
}
