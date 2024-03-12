/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("API em NoSQL Nitrite GTCC")
                .version("0.0.1")
                .contact(new Contact().email("vix.bay.group@gmail.com")
                .name("Arthur, Alessandro Santos, Hugo Alves, Douglas Lessa, Pedro Pompermayer, Samuel Paviotti"))
                .description("<h1>API GTCC</h1><p>Repositório ainda não esta disponível</p>"
                ));
    }
}
