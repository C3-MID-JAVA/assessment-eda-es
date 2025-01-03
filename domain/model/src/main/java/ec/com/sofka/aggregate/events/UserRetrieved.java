package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;

public class UserRetrieved extends DomainEvent {
    private final String name;
    private final String documentId;

    public UserRetrieved(String name, String documentId) {
        super(EventsEnum.USER_RETRIEVED.name());
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
