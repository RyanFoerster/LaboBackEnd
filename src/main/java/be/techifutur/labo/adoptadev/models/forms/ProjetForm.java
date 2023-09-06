package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Projet;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ProjetForm {

    @NotBlank
    @Size(min = 10,max = 150)
    private String name;

    private TechnologyFrontEnd technologyFrontEnd;

    private TechnologyBackEnd technologyBackEnd;

    @NotBlank
    private String description;

    @NotBlank
    private String github;

    public Projet toEntity(){

        Projet projet = new Projet();

        projet.setName(this.name);
        projet.setTechnologyFrontEnd(this.technologyFrontEnd);
        projet.setTechnologyBackEnd(this.technologyBackEnd);
        projet.setDescription(this.description);
        projet.setGithub(this.github);

        return projet;

    }

}
