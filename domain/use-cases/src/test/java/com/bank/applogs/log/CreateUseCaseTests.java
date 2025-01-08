package com.bank.applogs.log;

import com.bank.Log;
import com.bank.applogs.CreateLogUseCase;
import com.bank.gateway.ILogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUseCaseTests {
    @Test
    void createLogOK() {
        Log log = new Log(UUID.randomUUID().toString(), "test log", LocalDateTime.now());

        ILogRepository logRepositoryGateway = mock(ILogRepository.class);

        CreateLogUseCase createLogUseCase = new CreateLogUseCase(logRepositoryGateway);

        when(logRepositoryGateway.create(log.getMessage())).thenReturn(Mono.just(log));

        createLogUseCase.apply(log.getMessage());

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verify(logRepositoryGateway, times(1)).create(logCaptor.capture());

        String capturedLog = logCaptor.getValue();
        assertNotNull(capturedLog);
        assertEquals("test log", capturedLog);


    }
}
