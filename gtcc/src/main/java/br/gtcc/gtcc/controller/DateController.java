package br.gtcc.gtcc.controller;

import java.util.List;

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
import br.gtcc.gtcc.model.neo4j.Date;
import br.gtcc.gtcc.services.spec.DateInterface;
import java.util.Optional;

@CrossOrigin
@RestController
@ValidaAcesso("ROLE_COORDENADOR")
@RequestMapping("coordenacao/tcc/v1")
public class DateController {

    @Autowired
    private DateInterface interfaceDate;

    @PostMapping("/date")
    public ResponseEntity<Object> createDate(@RequestBody Date date) {

        Optional<Date> createdDate = (Optional<Date>) interfaceDate.createDate(date);

        if (createdDate.isPresent()) {

            return new ResponseEntity<>(createdDate, HttpStatus.CREATED);

        } else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/date/{id}")
    public ResponseEntity<Object> updateDate(@PathVariable("id") String id, @RequestBody Date date) {

        Optional<Date> updatedDate = (Optional<Date>) interfaceDate.updateDate(date);

        if (updatedDate.isPresent()) {

            return new ResponseEntity<>(updatedDate, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/date/{id}")
    public ResponseEntity<Object> deleteDate(@PathVariable("id") String id, @RequestBody Date date) {

        Date DateToDelete = new Date();

        Optional<Date> deletedDate = (Optional<Date>) interfaceDate.deleteDate(DateToDelete);

        if (deletedDate.isPresent()) {

            return new ResponseEntity<>(HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("/dates")
    public ResponseEntity<Object> getAllDates() {

        List<Optional<Date>> dates = (List<Optional<Date>>) interfaceDate.getAllDate();

        if (dates.isEmpty() != true) {

            return new ResponseEntity<>(dates, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/date/{id}")
    public ResponseEntity<Object> getDateById(@PathVariable("id") String id, @RequestBody Date date) {

        Date _date = new Date();

        Optional<Date> foundDate = (Optional<Date>) interfaceDate.getDate(_date);

        if (foundDate.isPresent()) {

            return new ResponseEntity<>(foundDate, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
}
