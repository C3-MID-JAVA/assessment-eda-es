package com.bank;

import java.time.LocalDateTime;
import java.util.UUID;

public class Log {
    private String id;

    private String message;

    private LocalDateTime dateTime;

    public Log() {}

    public Log(String message) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public Log(String id, String message, LocalDateTime dateTime) {
        this.id = id;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
