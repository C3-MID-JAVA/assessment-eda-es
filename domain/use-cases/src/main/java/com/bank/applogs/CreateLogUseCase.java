package com.bank.applogs;

import com.bank.gateway.ILogRepository;

public class CreateLogUseCase {
    private final ILogRepository logRepository;

    public CreateLogUseCase(ILogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void apply(String message) {
        logRepository.create(message).subscribe();
    }
}
