/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.config;

import br.gtcc.gtcc.model.nitriteid.ApresentationBanca;
import br.gtcc.gtcc.model.nitriteid.Data;
import br.gtcc.gtcc.model.nitriteid.Tcc;
import br.gtcc.gtcc.model.nitriteid.Users;
import br.gtcc.gtcc.services.impl.nitritedb.UserServices;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.common.mapper.JacksonMapperModule;
import org.dizitart.no2.mvstore.MVStoreModule;
import org.dizitart.no2.repository.ObjectRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.neo4j.cypherdsl.core.renderer.Dialect;
// import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;

/**
 *
 * @author mrbee
 * 
 * Classe de Configuração do banco de dados com o NitriteDB
 */

@Order(1)
@Configuration
@EnableNeo4jRepositories
@EnableReactiveNeo4jRepositories
public class DBConfig {
    
    // @Bean
    // public void dataBaseH2(){
    // }
    
    //neo4j DB
    @Bean("neo4j")
    org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
        return org.neo4j.cypherdsl.core.renderer.Configuration
        .newConfig()
        .withDialect(Dialect.NEO4J_5).build();
     }

     //nitrite
    @Bean("nitrite")
    public Nitrite dataBase(){
        MVStoreModule storeModule =MVStoreModule.withConfig()
                .filePath("baseDados.db")
                .build();
      
        Nitrite db = Nitrite.builder()
                .loadModule(storeModule)
                .loadModule(new JacksonMapperModule())
                .openOrCreate("roo", "gtcc");
                
        return db;
    }
    
    
    @Bean("repositorioUsers")
    @DependsOn("nitrite")
    public ObjectRepository<Users> repositoryUsers() {
        
        return dataBase().getRepository(Users.class);
    
    }

    @Bean
    @DependsOn("repositorioUsers")
    public UserServices userService(){
        return new UserServices();
    }

    @Bean 
    public ObjectRepository<Tcc> repositoryTCC() {
        
        return dataBase().getRepository(Tcc.class);
    
    }
    
    @Bean 
    public ObjectRepository<Data> repositoryData() {
        
        return dataBase().getRepository(Data.class);
    
    }
    
    @Bean 
    public ObjectRepository<ApresentationBanca> repositoryApresentation() {
        
        return dataBase().getRepository(ApresentationBanca.class);
    
    }
  
}
