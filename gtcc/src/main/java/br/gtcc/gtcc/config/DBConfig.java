/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


/**
 *
 * @author mrbee
 * 
 * Classe de Configuração do banco de dados com o NitriteDB
 */

@Order(0)
@Configuration
public class DBConfig {
    
    //neo4j DB
//    @Bean("neo4j");
//    org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
//        return org.neo4j.cypherdsl.core.renderer.Configuration
//        .newConfig()
//        .withDialect(Dialect.NEO4J_5).build();
//    }

}
