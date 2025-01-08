package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.aggregate.events.UserCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.aggregate.entities.user.User;
import ec.com.sofka.aggregate.entities.user.values.UserId;
import ec.com.sofka.aggregate.entities.user.values.objects.IdentityCard;

public class UserHandler extends DomainActionsContainer {

    public UserHandler(AccountAggregate accountAggregate) {
        addDomainActions((UserCreated event) -> {
            User user = new User(
                    UserId.of(event.getUserId()),
                    event.getFirstName(),
                    event.getLastName(),
                    IdentityCard.of(event.getIdentityCard()),
                    event.getEmail(),
                    event.getPassword(),
                    event.getStatus());

            accountAggregate.setUser(user);
        });
    }

}