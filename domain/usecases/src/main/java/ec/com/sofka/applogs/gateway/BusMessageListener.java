package ec.com.sofka.applogs.gateway;

//18. Port for listening messages
public interface BusMessageListener {
    void receiveMsg(String message);
}

