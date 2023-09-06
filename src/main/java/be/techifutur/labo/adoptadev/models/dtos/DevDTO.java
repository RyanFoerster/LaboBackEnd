package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class DevDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String pseudo;
    private Set<Role> roles;

    public static DevDTO toDTO(Dev entity) {
        if (entity == null) {
            return null;
        }

        return DevDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .pseudo(entity.getPseudo())
                .roles(entity.getRoles())
                .build();
    }
}
