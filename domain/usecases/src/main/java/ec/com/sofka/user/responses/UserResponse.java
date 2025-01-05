package ec.com.sofka.user.responses;

public class UserResponse {
    private final String id;
    private final String customerId;
    private final String name;
    private final String documentId;

    public UserResponse(String id, String customerId, String name, String documentId) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.documentId = documentId;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getDocumentId() {
        return documentId;
    }
}
