package ec.com.sofka.card;

import ec.com.sofka.Utils;
import ec.com.sofka.account.GetAccountByAccountNumberUseCase;
import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.AccountNumber;
import ec.com.sofka.account.values.objects.AccountType;
import ec.com.sofka.account.values.objects.Balance;
import ec.com.sofka.account.values.objects.OwnerName;
import ec.com.sofka.aggregate.account.Customer;
import ec.com.sofka.aggregate.account.events.CardCreated;
import ec.com.sofka.gateway.ICardRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.CardDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.request.CardRequest;
import ec.com.sofka.request.GetAccountRequest;
import ec.com.sofka.request.GetCardByNumRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Supplier;

public class CreateCardUseCase implements IUseCase<CardRequest, CardDTO> {

    private final IEventStore eventRepository;
    private final ICardRepository cardRepository;
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;




    public CreateCardUseCase(IEventStore eventStore, ICardRepository cardRepository, GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase) {
        this.eventRepository = eventStore;
        this.cardRepository = cardRepository;
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;

    }

    @Override
    public Mono<CardDTO> execute(CardRequest cmd) {
        Flux<DomainEvent> events = eventRepository.findAggregate(cmd.getCustomerId());
        System.out.println("CMDD " + cmd.getCardNumber() + " " + cmd.getAccount().getAccountNumber() + " " + cmd.getAggregateId());

        return Customer.from(cmd.getCustomerId(), events)
                .flatMap(customer -> {
                    //Card card = customer.getCard();
                    Optional<Card> optionalCard = customer.getCards().stream()
                            .filter(cardRec -> cardRec.getCardNumber().getValue().equals(cmd.getCardNumber()))
                            .findFirst();

                    if (optionalCard.isPresent()) {
                        return cardRepository.findByCardNumber(optionalCard.get().getCardNumber().getValue())
                                .flatMap(cardDTO -> Mono.error(new RuntimeException("Card already exists")));
                    } else {
                        return getAccountByAccountNumberUseCase.execute(new GetAccountRequest(
                                        cmd.getCustomerId(), null, null, null
                                ))
                                .switchIfEmpty(Mono.error(new RuntimeException("Account does not exist")))
                                .flatMap(accountResponse -> getCvvCard()
                                        .flatMap(cvv -> {
                                            CardCreated cardCreated = new CardCreated(
                                                    cmd.getCardName(),
                                                    cmd.getCardNumber(),
                                                    cmd.getCardType(),
                                                    cmd.getCardStatus(),
                                                    cmd.getCardExpiryDate(),
                                                    cvv,
                                                    cmd.getCardLimit(),
                                                    cmd.getCardHolderName(),
                                                    accountResponse.getAccount()
                                            );
                                            customer.createCard(cardCreated);

                                            Card cardFromCustomer = customer.getCards().stream()
                                                    .filter(cardRec -> cardRec.getCardNumber().getValue().equals(cmd.getCardNumber()))
                                                    .findFirst()
                                                    .orElseThrow(() -> new RuntimeException("Getting creation failed in transaction"));

                                            CardDTO cardToSave = new CardDTO(
                                                    null,
                                                    cardFromCustomer.getCardName().getValue(),
                                                    cardFromCustomer.getCardNumber().getValue(),
                                                    cardFromCustomer.getCardType().getValue(),
                                                    cardFromCustomer.getCardStatus().getValue(),
                                                    cardFromCustomer.getCardExpiryDate().getValue(),
                                                    cvv,
                                                    cardFromCustomer.getCardLimit().getValue(),
                                                    cardFromCustomer.getCardHolderName().getValue(),
                                                    accountResponse.getAccountDTO()
                                            );
                                            return cardRepository.save(cardToSave);
                                        }))
                                .flatMap(savedCard -> Flux.fromIterable(customer.getUncommittedEvents())
                                        .flatMap(eventRepository::save)
                                        .then(Mono.just(savedCard))
                                )
                                .doOnSuccess(savedCard -> customer.markEventsAsCommitted());
                    }

                });


    }

    public Mono<String> getCvvCard() {
        Supplier<String> cvvGenerator = Utils::generateCvvCode;

        return Mono.defer(() -> Mono.just(cvvGenerator.get()))
                .flatMap(cvv -> existsCvv(cvv)
                        .flatMap(exists -> exists ? Mono.empty() : Mono.just(cvv)))
                .repeat()
                .next();
    }
    private Mono<Boolean> existsCvv(String cvv) {
        return cardRepository.existsByCardCVV(cvv);
    }


}
