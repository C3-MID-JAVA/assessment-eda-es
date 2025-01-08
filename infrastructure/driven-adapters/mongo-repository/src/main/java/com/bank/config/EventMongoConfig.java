package com.bank.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.bank.database.events",
        reactiveMongoTemplateRef = "eventMongoTemplate")
public class EventMongoConfig {
    @Bean(name = "eventDatabaseFactory")
    public ReactiveMongoDatabaseFactory eventsDatabaseFactory(
            @Value("${spring.data.mongodb.events-uri}") String uri
    ) {
        return new SimpleReactiveMongoDatabaseFactory(new ConnectionString(uri));
    }

    @Bean(name = "eventMongoTemplate")
    public ReactiveMongoTemplate eventsMongoTemplate(
            @Qualifier("eventDatabaseFactory") ReactiveMongoDatabaseFactory eventDatabaseFactory
    ) {
        return new ReactiveMongoTemplate(eventDatabaseFactory);
    }
}