package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;

import java.time.Instant;

public class UserCreated extends DomainEvent {
    private final String name;
    private final String documentId;

    public UserCreated(String name, String documentId) {
        super(EventsEnum.USER_CREATED.name());
        this.name = name;
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public String getDocumentId() {
        return documentId;
    }
}
