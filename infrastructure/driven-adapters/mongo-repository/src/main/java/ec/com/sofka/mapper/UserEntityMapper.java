package ec.com.sofka.mapper;

import ec.com.sofka.data.UserEntity;
import ec.com.sofka.gateway.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public static UserEntity mapToEntity(UserDTO user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getIdentityCard(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus());
    }

    public static UserDTO mapToDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserDTO(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getIdentityCard(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getStatus()
        );
    }

}