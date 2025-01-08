package com.bank.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.bank.database.bank",
        reactiveMongoTemplateRef = "bankMongoTemplate")
public class BankMongoConfig {
    @Primary
    @Bean(name = "bankDatabaseFactory")
    public ReactiveMongoDatabaseFactory bankDatabaseFactory(
            @Value("${spring.data.mongodb.bank-uri}") String uri
    ) {
        return new SimpleReactiveMongoDatabaseFactory(new ConnectionString(uri));
    }

    @Primary
    @Bean(name = "bankMongoTemplate")
    public ReactiveMongoTemplate bankMongoTemplate(
            @Qualifier("bankDatabaseFactory") ReactiveMongoDatabaseFactory bankDatabaseFactory
    ) {
        return new ReactiveMongoTemplate(bankDatabaseFactory);
    }
}