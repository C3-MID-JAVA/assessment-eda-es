package com.bank.generics.interfaces;

import com.bank.generics.utils.Request;

import java.util.List;

public interface IUseCaseGet <R> {
    List<R> get();
}
