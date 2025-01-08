package com.bank;

import com.bank.applogs.CreateLogUseCase;
import com.bank.applogs.PrintLogUseCase;
import com.bank.gateway.IBusMessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BusListener implements IBusMessageListener {
    private final PrintLogUseCase printLogUseCase;
    private final CreateLogUseCase createLogUseCase;

    public BusListener(PrintLogUseCase printLogUseCase, CreateLogUseCase createLogUseCase) {
        this.printLogUseCase = printLogUseCase;
        this.createLogUseCase = createLogUseCase;
    }

    @Override
    @RabbitListener(queues = "bank.queue")
    public void receiveMsg(String message) {
        printLogUseCase.apply(message);
        createLogUseCase.apply(message);
    }
}