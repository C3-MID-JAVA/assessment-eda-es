package com.bank.applogs;

public class PrintLogUseCase {
    public void apply(String message) {
        System.out.println("Message received: " + message);
    }
}
