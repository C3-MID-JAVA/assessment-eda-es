package com.bank.generics.interfaces;

import com.bank.generics.utils.Request;
import org.reactivestreams.Publisher;

import java.util.concurrent.Flow;

//9. Generics creation to apply DDD: IUseCase - Interface to execute use cases
public interface IUseCaseExecute<T extends Request, R> {
    Publisher<R> execute(T request);

}
