package ec.com.sofka.request;

import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

public class CardRequest extends Request {
    private String customerId;
    private String cardName;
    private String cardNumber;
    private String cardType;
    private String cardStatus;
    private String cardExpiryDate;
    private String cardCVV;
    private BigDecimal cardLimit;
    private String cardHolderName;
    private AccountDTO account;

    public CardRequest(String customerId, String cardName, String cardNumber, String cardType, String cardStatus, String cardExpiryDate, String cardCVV, BigDecimal cardLimit, String cardHolderName, AccountDTO account) {
        super(null);
        this.customerId = customerId;
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

    public String getCustomerId() {
        return customerId;
    }
}
