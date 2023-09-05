package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {

    private String token;
    private UserDTO userDTO;

    public static AuthDTO toDTO(String token, User user) {
        return AuthDTO.builder()
                .token(token)
                .userDTO(UserDTO.toDTO(user))
                .build();
    }
}
