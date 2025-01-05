package ec.com.sofka.aggregate.customer.events;

import ec.com.sofka.generics.domain.DomainEvent;

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
