package ec.com.sofka.log;

import java.time.LocalDateTime;

public class Log {
    private String id;
    private String message;
    private String entity;
    private LocalDateTime timestamp;

    public Log(String message, String entity, LocalDateTime timestamp) {
        this.message = message;
        this.entity = entity;
        this.timestamp = timestamp;
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

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
