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
    private String description;
    private String email;
    private String pseudo;
    private LocalDate birthDate;
    private Set<TechnologyBackEnd> technologyBackEnds;
    private Set<TechnologyFrontEnd> technologyFrontEnds;
    private String gitHub;
    private String linkedIn;
    private Role role;
    private String cv;
    private AddressDTO address;

    public static DevDTO toDTO(Dev entity) {
        if (entity == null) {
            return null;
        }

        return DevDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .description(entity.getDescription())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .pseudo(entity.getPseudo())
                .birthDate(entity.getBirthDate())
                .technologyBackEnds(entity.getTechnologyBackEnds())
                .technologyFrontEnds(entity.getTechnologyFrontEnds())
                .gitHub(entity.getGitHub())
                .linkedIn(entity.getLinkedIn())
                .role(entity.getRole())
                .gitHub(entity.getGitHub())
                .linkedIn(entity.getLinkedIn())
                .cv(entity.getCv())
                .address(AddressDTO.toDTO(entity.getAddress()))

                .build();
    }
}
