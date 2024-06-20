package br.gtcc.gtcc;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // desativa a tela de login no inicio
//@EnableAutoConfiguration
@EnableNeo4jRepositories(basePackages="br.gtcc.gtcc")
public class GtccApplication {

    public static void main(String[] args) {

        SpringApplication.run(GtccApplication.class, args);
    }

    @Bean("neo4j")
    org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
        return org.neo4j.cypherdsl.core.renderer.Configuration
                .newConfig()
                .withDialect(Dialect.NEO4J_5).build();
    }

}
