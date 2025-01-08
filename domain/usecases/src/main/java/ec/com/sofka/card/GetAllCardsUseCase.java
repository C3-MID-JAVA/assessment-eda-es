package ec.com.sofka.card;




import ec.com.sofka.aggregate.account.Customer;
import ec.com.sofka.gateway.ICardRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.CardDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.request.GetCardByNumRequest;
import ec.com.sofka.responses.GetCardByNumResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class GetAllCardsUseCase implements IUseCase<GetCardByNumRequest, CardDTO> {
    private final IEventStore eventStore;


    public GetAllCardsUseCase(IEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public Flux<CardDTO> execute(GetCardByNumRequest request) {
        Flux<DomainEvent> events = eventStore.findAggregate(request.getAggregateId());
        return Customer.from(request.getAggregateId(), events)
                .flatMapMany(customer ->
                        Flux.fromIterable(customer.getCards())
                                .map(card -> new CardDTO(
                                        customer.getId().getValue(),
                                        card.getCardName().getValue(),
                                        card.getCardNumber().getValue(),
                                        card.getCardType().getValue(),
                                        card.getCardStatus().getValue(),
                                        card.getCardExpiryDate().getValue(),
                                        card.getCardCVV().getValue(),
                                        card.getCardLimit().getValue(),
                                        card.getCardHolderName().getValue(),
                                        null

                                ))
                );
    }
}
