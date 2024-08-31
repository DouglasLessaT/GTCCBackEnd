package br.gtcc.gtcc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import br.gtcc.gtcc.model.mysql.Grupo;
import br.gtcc.gtcc.model.mysql.TipoDocente;
import br.gtcc.gtcc.model.mysql.repository.GrupoRepository;
import br.gtcc.gtcc.services.impl.mysql.TipoDocenteService;

public class DataInitializer {
  private final GrupoRepository grupoRepository;

    public DataInitializer(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Criar instâncias dos grupos
            Grupo admin = new Grupo(null, "Admin Group", null);
            Grupo aluno = new Grupo(null, "Aluno Group", null);
            Grupo coordenador = new Grupo(null, "Coordenador Group", null);
            Grupo professor = new Grupo(null, "Professor Group", null);

            // Salvar grupos no banco de dados
            grupoRepository.save(admin);
            grupoRepository.save(aluno);
            grupoRepository.save(coordenador);
            grupoRepository.save(professor);
        };
    }

    @Bean
    CommandLineRunner initTipoDocentes(TipoDocenteService tipoDocenteService) {
        return args -> {
            // Verifica se o TipoDocente "Membro" já existe, se não, cria um novo
            if (tipoDocenteService.findByTitulo("Membro").isEmpty()) {
                tipoDocenteService.save(new TipoDocente(null, "Membro", 1));
            }
            
            // Verifica se o TipoDocente "Orientador" já existe, se não, cria um novo
            if (tipoDocenteService.findByTitulo("Orientador").isEmpty()) {
                tipoDocenteService.save(new TipoDocente(null, "Orientador", 1));
            }
        };
    }
}
