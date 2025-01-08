package ec.com.sofka.config;

import ec.com.sofka.utils.AccountProperties;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountRabbitConfig {

    private final AccountProperties accountProperties;

    public AccountRabbitConfig(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }

    @Bean
    public TopicExchange accountExchange() {
        return new TopicExchange(accountProperties.getExchangeName());
    }

    @Bean
    public Queue accountQueue() {
        return new Queue(accountProperties.getQueueName(), true);
    }

    @Bean
    public Binding accountBinding() {
        return BindingBuilder.bind(accountQueue())
                .to(accountExchange())
                .with(accountProperties.getRoutingKey());
    }
}
