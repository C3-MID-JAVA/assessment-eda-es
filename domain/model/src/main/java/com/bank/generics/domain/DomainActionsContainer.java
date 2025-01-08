package com.bank.generics.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

//5. Generics creation to apply DDD: DomainActionsContainer - To store the actions that will be executed
public abstract class DomainActionsContainer {
    public Set<Consumer<? super DomainEvent>>  domainActions = new HashSet<>();

    public void addDomainActions(Consumer<? extends DomainEvent> action) {
       domainActions.add((Consumer<? super DomainEvent>) action );
    }
}
