package br.gtcc.gtcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // desativa a tela de login no inicio
// @EnableAutoConfiguration
public class GtccApplication {

    public static void main(String[] args) {

        SpringApplication.run(GtccApplication.class, args);
    }

}
