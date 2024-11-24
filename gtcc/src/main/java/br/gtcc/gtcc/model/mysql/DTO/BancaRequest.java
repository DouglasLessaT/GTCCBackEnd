package br.gtcc.gtcc.model.mysql.DTO;

import br.gtcc.gtcc.model.mysql.DocenteBanca;
import br.gtcc.gtcc.model.mysql.Tcc;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BancaRequest {

    private Integer ativo;
    private Long idTcc;
    private List<Long> idDocentesBanca = new ArrayList<>();

}
