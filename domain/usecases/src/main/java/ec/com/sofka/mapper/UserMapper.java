package ec.com.sofka.mapper;

import ec.com.sofka.gateway.dto.UserDTO;
import ec.com.sofka.aggregate.entities.user.User;
import ec.com.sofka.aggregate.entities.user.values.UserId;
import ec.com.sofka.aggregate.entities.user.values.objects.IdentityCard;
import ec.com.sofka.responses.UserResponse;

public class UserMapper {

    public static User mapToModelFromDTO(UserDTO user) {
        if (user == null) {
            return null;
        }
        return new User(
                UserId.of(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                IdentityCard.of(user.getIdentityCard()),
                user.getEmail(),
                user.getPassword(),
                user.getStatus());
    }

    public static UserResponse mapToResponseFromModel(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getIdentityCard().getValue(),
                user.getEmail(),
                user.getStatus()
        );
    }

    public static UserResponse mapToResponseFromDTO(UserDTO user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getIdentityCard(),
                user.getEmail(),
                user.getStatus()
        );
    }

    public static UserDTO mapToDTOFromModel(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getId().getValue(),
                user.getFirstName(),
                user.getLastName(),
                user.getIdentityCard().getValue(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus()
        );
    }

    public static User mapToModelFromResponse(UserResponse user) {
        if (user == null) {
            return null;
        }

        return new User(
                null,
                user.getFirstName(),
                user.getLastName(),
                IdentityCard.of(user.getIdentityCard()),
                user.getEmail(),
                null,
                user.getStatus()
        );
    }

}