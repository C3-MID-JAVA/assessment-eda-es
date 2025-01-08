package ec.com.sofka.data;

public class AccountSimpleRequestDTO {
    private String customerId;

    public AccountSimpleRequestDTO(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
