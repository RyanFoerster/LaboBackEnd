package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Address;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Data
public class DevRegisterForm {

    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6)
    @Pattern(
            regexp = "^(?=.*[!=@#$%^&*()_+{}\\\\[\\\\]:;<>,.?~\\\\-]).*(?=.*[A-Z]).*(?=.*[0-9]).*$",
            message = "Doit contenir une majuscule, un chiffre, un caractère spécial et minimum 6 caractères"
    )
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate birthDate;
    private Set<TechnologyBackEnd> technologyBackEnds;
    private Set<TechnologyFrontEnd> technologyFrontEnds;
    private String gitHub;
    private String linkedIn;
    private String cv;
    @NotBlank
    private String pseudo;
    @Valid
    private Address address;

    public Dev toEntity(){
        Dev dev = new Dev();
        dev.setUsername(this.username);
        dev.setPassword(this.password);
        dev.setPseudo(this.pseudo);
        dev.setFirstName(this.firstName);
        dev.setLastName(this.lastName);
        dev.setEmail(this.email);
        dev.setDescription(this.description);
        dev.setRoles(Collections.singleton(Role.DEVELOPER));
        dev.setBirthDate(this.birthDate);
        dev.setTechnologiesBackEnd(this.technologyBackEnds);
        dev.setTechnologiesFrontEnd(this.technologyFrontEnds);
        dev.setGitHub(this.gitHub);
        dev.setLinkedIn(this.linkedIn);
        dev.setCv(this.cv);
        dev.setAddress(this.address);
        return dev;
    }
}
