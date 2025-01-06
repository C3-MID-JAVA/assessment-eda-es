package ec.com.sofka;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JSONMap implements IJSONMapper {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    @Override
    public String writeToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException | ClassCastException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }

    @Override
    public DomainEvent readFromJson(String json, Class<?> clazz) {
        try {
            JsonNode node = mapper.readTree(json);

            Instant when = Instant.ofEpochMilli((long) (node.get("when").asDouble() * 1000));
            String eventId = node.get("eventId").asText();
            String eventType = node.get("eventType").asText();
            String aggregateRootId = node.has("aggregateRootId") ? node.get("aggregateRootId").asText() : null;
            String aggregateRootName = node.get("aggregateRootName").asText();
            Long version = node.has("version") ? node.get("version").asLong() : 1L;

            return new DomainEvent(when, eventId, eventType, aggregateRootId, aggregateRootName, version);
        } catch (JsonProcessingException | ClassCastException e) {
            throw new RuntimeException("Failed to deserialize event", e);
        }
    }
}
