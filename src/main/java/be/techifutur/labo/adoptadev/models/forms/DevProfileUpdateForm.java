package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import be.techifutur.labo.adoptadev.validations.constraints.TimesAgo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class DevProfileUpdateForm {

    private String description;

    @TimesAgo(message = "should be at least 16 years old")
    private LocalDate birthDate;
    private Set<TechnologyBackEnd> technologyBackEnds;
    private Set<TechnologyFrontEnd> technologyFrontEnds;
    private String gitHub;
    private String linkedIn;
    private String cv;
    private String pseudo;

    public Dev toEntity(){
        Dev dev = new Dev();

        dev.setDescription(this.description);
        dev.setBirthDate(this.birthDate);
        dev.setTechnologiesBackEnd(this.technologyBackEnds);
        dev.setTechnologiesFrontEnd(this.technologyFrontEnds);
        dev.setGitHub(this.gitHub);
        dev.setLinkedIn(this.linkedIn);
        dev.setCv(this.cv);
        dev.setPseudo(this.pseudo);
        return dev;
    }
}
