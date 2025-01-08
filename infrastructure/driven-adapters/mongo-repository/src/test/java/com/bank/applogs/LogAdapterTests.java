package com.bank.applogs;

import com.bank.Log;
import com.bank.TestConfiguration;
import com.bank.gateway.ILogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class)
@DataMongoTest
public class LogAdapterTests {
    private final ILogRepository logRepository;

    @Autowired
    public LogAdapterTests(ILogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Test
    public void CreateLogOK() {
        Mono<Log> save = logRepository.create("test log");

        StepVerifier.create(save)
                .assertNext(l -> {
                    assertThat(l.getId()).isNotNull();
                    assertThat(l.getMessage()).isEqualTo("test log");
                })
                .verifyComplete();
    }
}
