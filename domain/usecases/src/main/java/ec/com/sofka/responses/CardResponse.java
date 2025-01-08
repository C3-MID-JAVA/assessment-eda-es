package ec.com.sofka.responses;

import ec.com.sofka.gateway.dto.AccountDTO;

import java.math.BigDecimal;

public class CardResponse {
    private String id;
    private String cardName;
    private String cardNumber;
    private String cardType;
    private String cardStatus;
    private String cardExpiryDate;
    private String cardCVV;
    private BigDecimal cardLimit;
    private String cardHolderName;
    private AccountDTO account;

    public CardResponse(String id, String cardName, String cardNumber, String cardType, String cardStatus, String cardExpiryDate, String cardCVV, BigDecimal cardLimit, String cardHolderName, AccountDTO account) {
        this.id = id;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cardStatus = cardStatus;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCVV = cardCVV;
        this.cardLimit = cardLimit;
        this.cardHolderName = cardHolderName;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public AccountDTO getAccount() {
        return account;
    }
}
