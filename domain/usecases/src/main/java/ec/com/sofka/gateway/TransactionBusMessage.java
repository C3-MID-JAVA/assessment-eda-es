package ec.com.sofka.gateway;

public interface TransactionBusMessage {

    void sendMsg(Object message);

}