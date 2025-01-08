package ec.com.sofka.utils;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class QueueManager {

    private final AccountProperties accountProperties;
    private final TransactionProperties transactionProperties;

    public QueueManager(AccountProperties accountProperties, TransactionProperties transactionProperties) {
        this.accountProperties = accountProperties;
        this.transactionProperties = transactionProperties;
    }

    public String[] getAllQueues() {
        return new String[] {
                accountProperties.getQueueName(),
                transactionProperties.getQueueName()
        };
    }
}
