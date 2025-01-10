package ec.com.sofka.generics.interfaces;

import org.reactivestreams.Publisher;

public interface IUseCaseGet<R> {
    Publisher<R> get();
}