package ec.com.sofka.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:rabbit-application.properties")
public class TransactionProperties {

    @Value("${transaction.exchange.name}")
    private String exchangeName;

    @Value("${transaction.queue.name}")
    private String queueName;

    @Value("${transaction.routing.key}")
    private String routingKey;

    public String getExchangeName() {
        return exchangeName;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
