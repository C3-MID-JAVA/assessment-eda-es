package ec.com.sofka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.sofka.appservice.gateway.IBusMessage;
import ec.com.sofka.utils.AccountProperties;
import ec.com.sofka.utils.TransactionProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusAdapter implements IBusMessage {

    private final RabbitTemplate rabbitTemplate;
    private final AccountProperties accountProperties;
    private final TransactionProperties transactionProperties;

    public BusAdapter(RabbitTemplate rabbitTemplate, AccountProperties accountProperties, TransactionProperties transactionProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.accountProperties = accountProperties;
        this.transactionProperties = transactionProperties;
    }

    @Override
    public void sendMsg(String entity, String message) {
        String exchange;
        String routingKey;

        // Determina los valores de exchange y routingKey según la entidad
        switch (entity.toLowerCase()) {
            case "account" -> {
                exchange = accountProperties.getExchangeName();
                routingKey = accountProperties.getRoutingKey();
            }
            case "transaction" -> {
                exchange = transactionProperties.getExchangeName();
                routingKey = transactionProperties.getRoutingKey();
            }
            default -> throw new IllegalArgumentException("Invalid entity type: " + entity);
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Crea el payload JSON
            String payload = mapper.writeValueAsString(Map.of(
                    "entity", entity,
                    "message", message
            ));
            // Envía el mensaje al RabbitMQ
            rabbitTemplate.convertAndSend(exchange, routingKey, payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating JSON payload", e);
        }
    }
}
