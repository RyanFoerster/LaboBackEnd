package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Projet;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetForm {

    private String name;
    private TechnologyFrontEnd technologyFrontEnd;
    private TechnologyBackEnd technologyBackEnd;
    private String description;
    private String github;

    public Projet projet(){

        Projet projet = new Projet();

        projet.setName(this.name);
        projet.setTechnologyFrontEnd(this.technologyFrontEnd);
        projet.setTechnologyBackEnd(this.technologyBackEnd);
        projet.setDescription(this.description);
        projet.setGithub(this.github);

        return projet;

    }

}
