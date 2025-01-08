package com.bank.config;

import com.bank.IJSONMapper;
import com.bank.JSONMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public IJSONMapper jsonMapper(){
        return new JSONMap();
    }
}
