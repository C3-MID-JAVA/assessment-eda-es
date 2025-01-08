package ec.com.sofka.responses;

import ec.com.sofka.utils.enums.StatusEnum;

import java.io.Serializable;

public class UserResponse implements Serializable {

    private String firstName;
    private String lastName;
    private String identityCard;
    private String email;
    private StatusEnum status;

    public UserResponse(String firstName, String lastName, String identityCard, String email, StatusEnum status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.email = email;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}