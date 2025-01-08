package com.bank;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@EnableAutoConfiguration
@ComponentScan( basePackages = "com.bank",
        includeFilters = {
                @ComponentScan.Filter(type= FilterType.REGEX, pattern = "^.+Tests$")
        })
public class TestConfiguration {
}
