package br.gtcc.gtcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class GtccApplication {
	public static void main(String[] args) {
		
		SpringApplication.run(GtccApplication.class, args);
	}

}
