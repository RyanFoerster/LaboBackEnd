package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
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
    private String gitHub;
    private String linkedIn;
    private String cv;
    private Set<TechnologyBackEnd> technologyBackEnds;
    private Set<TechnologyFrontEnd> technologyFrontEnds;
    private Set<Role> roles;
    private AddressDTO address;

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
                .gitHub(entity.getGitHub())
                .linkedIn(entity.getLinkedIn())
                .cv(entity.getCv())
                .technologyBackEnds(entity.getTechnologiesBackEnd())
                .technologyFrontEnds(entity.getTechnologiesFrontEnd())
                .roles(entity.getRoles())
                .address(AddressDTO.toDTO(entity.getAddress()))
                .build();
    }
}
