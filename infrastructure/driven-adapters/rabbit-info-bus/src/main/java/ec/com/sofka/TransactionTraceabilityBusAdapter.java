package ec.com.sofka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.sofka.gateway.TransactionBusMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionTraceabilityBusAdapter implements TransactionBusMessage {
    private final RabbitTemplate rabbitTemplate;

    @Value("${traceability.direct.exchange}")
    private String exchangeDirectTraceabilityValue;

    @Value("${traceability.direct.routingKey}")
    private String routingKeyTraceabilityValue;

    public TransactionTraceabilityBusAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsg(Object message) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage;

        try {
            jsonMessage = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        rabbitTemplate.convertAndSend(exchangeDirectTraceabilityValue,
                routingKeyTraceabilityValue,
                jsonMessage);
    }

}