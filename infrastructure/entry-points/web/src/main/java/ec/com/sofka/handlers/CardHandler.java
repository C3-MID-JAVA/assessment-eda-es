package ec.com.sofka.handlers;

import ec.com.sofka.card.CreateCardUseCase;
import ec.com.sofka.card.GetAllCardsUseCase;
import ec.com.sofka.data.CardReqDTO;
import ec.com.sofka.mapper.CardDTOMapper;
import ec.com.sofka.request.GetCardByNumRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CardHandler {
    private final CreateCardUseCase createCardUseCase;
    private final GetAllCardsUseCase getAllCardsUseCase;


    public CardHandler(CreateCardUseCase createCardUseCase, GetAllCardsUseCase getAllCardsUseCase) {
        this.createCardUseCase = createCardUseCase;
        this.getAllCardsUseCase = getAllCardsUseCase;
    }

    public Mono<CardReqDTO> createCard(CardReqDTO request) {

        return createCardUseCase.execute(CardDTOMapper.toCardRequest(request)).map(CardDTOMapper::toCardReqDTO);
    }

    public Flux<CardReqDTO> getCardsByAccountNumber(String customerId) {
        return getAllCardsUseCase.execute(new GetCardByNumRequest(customerId))
                .map(CardDTOMapper::toCardReqDTO);
    }
}
