package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class DevProfileUpdateForm {

    private String firstName;
    private String lastName;
    private String description;
    private Set<TechnologyBackEnd> technologiesBackEnd;
    private Set<TechnologyFrontEnd> technologiesFrontEnd;
    private String gitHub;
    private String linkedIn;
    private String cv;
    private String pseudo;

    public Dev toEntity(){
        Dev dev = new Dev();

        dev.setFirstName(this.firstName);
        dev.setLastName(this.lastName);
        dev.setDescription(this.description);
        dev.setTechnologiesBackEnd(this.technologiesBackEnd);
        dev.setTechnologiesFrontEnd(this.technologiesFrontEnd);
        dev.setGitHub(this.gitHub);
        dev.setLinkedIn(this.linkedIn);
        dev.setCv(this.cv);
        dev.setPseudo(this.pseudo);
        return dev;
    }
}
