package ec.com.sofka.mapper;

import ec.com.sofka.data.UserRequestDTO;
import ec.com.sofka.data.UserResponseDTO;
import ec.com.sofka.requests.UserRequest;
import ec.com.sofka.responses.UserResponse;
import ec.com.sofka.utils.enums.StatusEnum;

public class UserModelMapper {

    public static UserRequest mapToRequest(UserRequestDTO user) {
        if (user == null) {
            return null;
        }

        return new UserRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getIdentityCard(),
                user.getEmail(),
                user.getPassword(),
                StatusEnum.ACTIVE.name().compareTo(user.getStatus()) == 0 ? StatusEnum.ACTIVE : StatusEnum.INACTIVE
        );
    }

    public static UserResponseDTO mapToDTO(UserResponse user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getIdentityCard(),
                user.getEmail(),
                user.getStatus().name()
        );
    }



}