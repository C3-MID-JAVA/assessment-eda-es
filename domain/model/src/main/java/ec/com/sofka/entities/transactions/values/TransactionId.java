package ec.com.sofka.entities.transactions.values;

import ec.com.sofka.generics.utils.Identity;

public class TransactionId extends Identity {
    public TransactionId() {
        super();
    }

    private TransactionId(final String id){
        super(id);
    }

    public static TransactionId of(final String id) {
        return new TransactionId(id);
    }

}
