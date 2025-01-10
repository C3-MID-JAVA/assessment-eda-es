package ec.com.sofka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.sofka.gateway.ErrorBusMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogErrorBusAdapter implements ErrorBusMessage {
    private final RabbitTemplate rabbitTemplate;

    @Value("${log.direct.exchange}")
    private String exchangeDirectLogValue;

    @Value("${log.direct.routingKey}")
    private String routingKeyLogValue;

    public LogErrorBusAdapter(RabbitTemplate rabbitTemplate) {
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

        rabbitTemplate.convertAndSend(exchangeDirectLogValue,
                routingKeyLogValue,
                jsonMessage);
    }

}