package ec.com.sofka.config;

import ec.com.sofka.utils.TransactionProperties;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionRabbitConfig {

    private final TransactionProperties transactionProperties;

    public TransactionRabbitConfig(TransactionProperties transactionProperties) {
        this.transactionProperties = transactionProperties;
    }

    @Bean
    public TopicExchange transactionExchange() {
        return new TopicExchange(transactionProperties.getExchangeName());
    }

    @Bean
    public Queue transactionQueue() {
        return new Queue(transactionProperties.getQueueName(), true);
    }

    @Bean
    public Binding transactionBinding() {
        return BindingBuilder.bind(transactionQueue())
                .to(transactionExchange())
                .with(transactionProperties.getRoutingKey());
    }

}
