package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class DevProfileUpdateForm {

    @Size(min = 6)
    @Pattern(
            regexp = "^(?=.*[!=@#$%^&*()_+{}\\\\[\\\\]:;<>,.?~\\\\-]).*(?=.*[A-Z]).*(?=.*[0-9]).*$",
            message = "Doit contenir une majuscule, un chiffre, un caractère spécial et minimum 6 caractères"
    )
    private String password;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String description;
    private LocalDate birthDate;
    private Set<TechnologyBackEnd> technologyBackEnds;
    private Set<TechnologyFrontEnd> technologyFrontEnds;
    private String gitHub;
    private String LinkedIn;
    private String cv;
    private String pseudo;

    public Dev toEntity(){
        Dev dev = new Dev();
        dev.setPassword(this.password);
        dev.setFirstName(this.firstName);
        dev.setLastName(this.lastName);
        dev.setEmail(this.email);
        dev.setDescription(this.description);
        dev.setBirthDate(this.birthDate);
        dev.setTechnologiesBackEnd(this.technologyBackEnds);
        dev.setTechnologiesFrontEnd(this.technologyFrontEnds);
        dev.setGitHub(this.gitHub);
        dev.setLinkedIn(this.LinkedIn);
        dev.setCv(this.cv);
        dev.setPseudo(this.pseudo);
        return dev;
    }
}
