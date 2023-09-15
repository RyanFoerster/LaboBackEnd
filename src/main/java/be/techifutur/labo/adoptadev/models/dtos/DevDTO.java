package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
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
    private LocalDate birthDate;
    private Set<TechnologyBackEnd> technologiesBackEnd;
    private Set<TechnologyFrontEnd> technologiesFrontEnd;
    private String gitHub;
    private String linkedIn;
    private Role role;

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
                .birthDate(entity.getBirthDate())
                .technologiesBackEnd(entity.getTechnologiesBackEnd())
                .technologiesFrontEnd(entity.getTechnologiesFrontEnd())
                .gitHub(entity.getGitHub())
                .linkedIn(entity.getLinkedIn())
                .role(entity.getRole())
                .build();
    }
}
