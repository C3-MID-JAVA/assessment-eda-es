package com.bank.generics.interfaces;

//7. Generics creation to apply DDD: IApplyEvent - Interface to apply events
@FunctionalInterface
public interface IEvent {
    void apply();
}
