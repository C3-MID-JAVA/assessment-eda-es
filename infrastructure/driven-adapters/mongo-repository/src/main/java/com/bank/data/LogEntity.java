package com.bank.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "logs")
public class LogEntity {
    @Id
    private String id;

    @Field("message")
    private String message;

    @Field("date_time")
    private LocalDateTime dateTime;

    public LogEntity() {}

    public LogEntity(String message) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public LogEntity(String id, String message, LocalDateTime dateTime) {
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
