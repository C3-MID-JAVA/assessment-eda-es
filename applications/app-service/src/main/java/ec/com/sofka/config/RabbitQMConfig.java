package ec.com.sofka.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQMConfig {

    @Value("${log.direct.exchange}")
    private String exchangeDirectLogValue;
    @Value("${log.direct.queue}")
    private String queveLogValue;
    @Value("${log.direct.routingKey}")
    private String routingKeyLogValue;

    @Value("${traceability.direct.exchange}")
    private String exchangeDirectTraceabilityValue;
    @Value("${traceability.direct.queue}")
    private String queveTraceabilityValue;
    @Value("${traceability.direct.routingKey}")
    private String routingKeyTraceabilityValue;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeDirectLogValue);
    }

    @Bean
    public Queue queue() {
        return new Queue(queveLogValue, true);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKeyLogValue);
    }

    @Bean
    public DirectExchange exchangeTraceability() {
        return new DirectExchange(exchangeDirectTraceabilityValue);
    }

    @Bean
    public Queue queueTraceability() {
        return new Queue(queveTraceabilityValue, true);
    }

    @Bean
    public Binding bindingTraceability(Queue queueTraceability, DirectExchange exchangeTraceability) {
        return BindingBuilder.bind(queueTraceability).to(exchangeTraceability).with(routingKeyTraceabilityValue);
    }

}