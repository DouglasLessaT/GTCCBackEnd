/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gtcc.gtcc.config;

import br.gtcc.gtcc.model.ApresentationBanca;
import br.gtcc.gtcc.model.Data;
import br.gtcc.gtcc.model.Tcc;
import br.gtcc.gtcc.model.Users;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.common.mapper.JacksonMapperModule;
import org.dizitart.no2.mvstore.MVStoreModule;
import org.dizitart.no2.repository.ObjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 *
 * @author mrbee
 * 
 * Classe de Configuração do banco de dados com o NitriteDB
 */

@Order(1)
@Configuration
public class DBConfig {
    
    // @Bean
    // public void dataBaseH2(){
    // }
    
    @Bean
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
    
    @Bean 
    public ObjectRepository<Users> repositoryUsers() {
        
        return dataBase().getRepository(Users.class);
    
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
