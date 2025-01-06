package ec.com.sofka.appservice.gateway;

//7. New gateway to establish the link for the outside just as we do with repository
public interface IBusMessage {
    //It's a void bc does an action
    void sendMsg(String entity, String message);
}
