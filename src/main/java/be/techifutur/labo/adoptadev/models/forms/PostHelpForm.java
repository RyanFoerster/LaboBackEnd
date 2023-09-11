package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.PostHelp;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostHelpForm {

    @NotBlank
    private String title;

    private TechnologyFrontEnd technologyFrontEnd;

    private TechnologyBackEnd technologyBackEnd;

    @NotNull(message = "La description ne peut pas être nulle")
    @Size(min = 10, max = 500, message = "La description doit comporter entre 10 et 500 caractères")
    private String description;

    private String github;

    @NotNull
    private Boolean ouvert;

    public PostHelp toEntity() {

        PostHelp postHelp = new PostHelp();

        postHelp.setTitle(this.title);
        postHelp.setTechnologyFrontEnd(this.technologyFrontEnd);
        postHelp.setTechnologyBackEnd(this.technologyBackEnd);
        postHelp.setDescription(this.description);
        postHelp.setGithub(this.github);
        postHelp.setOuvert(this.ouvert);

        return postHelp;
    }

}
