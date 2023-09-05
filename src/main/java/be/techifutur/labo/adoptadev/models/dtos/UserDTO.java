package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.models.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private Set<Role> roles;

    public static UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .roles(entity.getRoles())
                .build();
    }


}
