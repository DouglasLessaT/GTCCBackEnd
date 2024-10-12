package br.gtcc.gtcc.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.gtcc.gtcc.model.mysql.ResponseMessage;

@Component
public class UtilController {

    public static ResponseEntity<Object> buildResponseFromOptional(Optional<?> opt ,HttpStatus codeSuccess ,HttpStatus codeFailed ,String meesageSucesses ,String messageFailed){
        if (opt.isPresent()) {
            @SuppressWarnings("rawtypes")
            ResponseMessage response = new ResponseMessage<>(meesageSucesses, opt.get());
            return new ResponseEntity<>( response, codeSuccess);
        } else {
            return ResponseEntity.status(codeFailed).body(messageFailed);
        }
    }
}
