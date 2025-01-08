package com.bank.gateway;

import com.bank.Log;
import reactor.core.publisher.Mono;

public interface ILogRepository {
    Mono<Log> create(String message);
}
