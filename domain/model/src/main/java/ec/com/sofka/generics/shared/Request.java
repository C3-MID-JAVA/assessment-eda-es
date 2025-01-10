package ec.com.sofka.generics.shared;

public abstract class Request {
    private final String aggregateId;

    protected Request(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

}