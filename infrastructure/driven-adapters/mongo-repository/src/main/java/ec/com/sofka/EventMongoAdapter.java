/*package ec.com.sofka;

import ec.com.sofka.data.EventEntity;
import ec.com.sofka.database.events.IEventMongoRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventMongoAdapter implements IEventStore {

    private final IEventMongoRepository repository;
    private final IJSONMapper mapper;
    private final MongoTemplate eventMongoTemplate;

    public EventMongoAdapter(IEventMongoRepository repository, JSONMap mapper, @Qualifier("eventMongoTemplate")MongoTemplate eventMongoTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventMongoTemplate = eventMongoTemplate;
    }

    @Override
    public DomainEvent save(DomainEvent event) {
        EventEntity e = new EventEntity(
                event.getEventId(),
                event.getAggregateRootId(),
                event.getEventType(),
                mapper.writeToJson(event),
                event.getWhen().toString(),
                event.getVersion()

        );
        repository.save(e);
        return event;
    }

    @Override
    public List<DomainEvent> findAggregate(String aggregateId) {
        List<EventEntity> entities = repository.findByAggregateId(aggregateId);
        return entities.stream()
                .map(eventEntity -> mapper.readFromJson(eventEntity.getEventData(), DomainEvent.class))
                .toList();
    }
}

 */
package ec.com.sofka;

import ec.com.sofka.data.EventEntity;
import ec.com.sofka.database.events.IEventMongoRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Repository
public class EventMongoAdapter implements IEventStore {

    private final IEventMongoRepository repository;
    private final JSONMap mapper;
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
        return repository.save(e).thenReturn(event);
    }
/*
    @Override
    public Flux<DomainEvent> findAggregate(String aggregateId) {
        return repository.findByAggregateId(aggregateId)
                .flatMap(eventEntity -> Mono.just(mapper.readFromJson(eventEntity.getEventData(), DomainEvent.class)));
    }
*/
@Override
public Flux<DomainEvent> findAggregate(String aggregateId) {
    return repository.findByAggregateId(aggregateId)
            .map(eventEntity -> eventEntity.deserializeEvent(mapper))
            .sort(Comparator.comparing(DomainEvent::getVersion));
}

    @Override
    public Flux<DomainEvent> findAllAggregates() {
        return repository.findAll()
                .map(eventEntity -> eventEntity.deserializeEvent(mapper))
                .sort(Comparator.comparing(DomainEvent::getAggregateRootId)
                        .thenComparing(DomainEvent::getVersion, Comparator.reverseOrder()));
    }
}
