package com.bank;

import com.bank.gateway.IBusMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BusAdapter implements IBusMessage {
    private final RabbitTemplate rabbitTemplate;

    public BusAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsg(String message) {
        rabbitTemplate.convertAndSend("bank.exchange", "bank.routingKey", message);
    }
}