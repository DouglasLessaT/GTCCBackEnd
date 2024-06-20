/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 *
 * @author mrbee
 * 
 * Classe de configuração do Swaager para documentação da API, não é para TESTE !
 */
@Configuration
@Order(2)
public class DocConfig {
    @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .addSecurityItem(new SecurityRequirement().addList("Auth JWT"))
                                .components(new Components()
                                                .addSecuritySchemes("Auth JWT",
                                                                new SecurityScheme()
                                                                                .name("Auth JWT")
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("Bearer")
                                                                                .bearerFormat("JWT")))
                                .info(new Info()
                                                .title("API em NoSQL Neo4j GTCC")
                                                .version("0.0.1")
                                                .contact(new Contact().email("vix.bay.group@gmail.com")
                                                                .name("Arthur, Alessandro Santos, Hugo da Cruz, Douglas Lessa, Pedro Pompermayer, Samuel Paviotti"))
                                                .description("<h1>API GTCC</h1><p>Repositório ainda não esta disponível</p>"));
        }
}
