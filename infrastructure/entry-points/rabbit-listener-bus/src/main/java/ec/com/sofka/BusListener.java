package ec.com.sofka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.sofka.applogs.logs.PrintLogUseCase;
import ec.com.sofka.applogs.gateway.BusMessageListener;
import ec.com.sofka.utils.QueueManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusListener implements BusMessageListener {

    private final PrintLogUseCase printLogUseCase;
    private final QueueManager queueManager;

    public BusListener(PrintLogUseCase printLogUseCase, QueueManager queueManager) {
        this.printLogUseCase = printLogUseCase;
        this.queueManager = queueManager;
    }

    @Override
    @RabbitListener(queues = "#{@queueManager.getAllQueues()}")
    public void receiveMsg(String payload) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convierte el payload JSON a un mapa
            Map<String, String> messageMap = mapper.readValue(payload, new TypeReference<>() {});
            String entity = messageMap.get("entity");
            String message = messageMap.get("message");

            printLogUseCase.accept(entity, message);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing message payload: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error in message listener: " + e.getMessage());
        }
    }
}
