package ec.com.sofka.adapter;

import ec.com.sofka.IJSONMapper;
import ec.com.sofka.JSONMap;
import ec.com.sofka.data.EventEntity;
import ec.com.sofka.database.events.IEventMongoRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class EventMongoAdapter implements IEventStore {

    private final IEventMongoRepository repository;
    private final IJSONMapper mapper;
    private final ReactiveMongoTemplate eventMongoTemplate;

    public EventMongoAdapter(IEventMongoRepository repository, JSONMap mapper, @Qualifier("eventMongoTemplate") ReactiveMongoTemplate eventMongoTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventMongoTemplate = eventMongoTemplate;
    }

    @Override
    public Mono<DomainEvent> save(DomainEvent event) {
        EventEntity e = new EventEntity(
                event.getEventId(),
                event.getAggregateRootId(),
                event.getEventType(),
                mapper.writeToJson(event),
                event.getWhen().toString(),
                event.getVersion()
        );
        return repository.save(e)
                .thenReturn(event);
    }

    @Override
    public Flux<DomainEvent> findAggregate(String aggregateId) {
        return repository.findByAggregateId(aggregateId)
                .map(eventEntity -> mapper.readFromJson(eventEntity.getEventData(), DomainEvent.class));
    }

}
