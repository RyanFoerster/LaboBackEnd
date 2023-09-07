package be.techifutur.labo.adoptadev.models.dtos;


import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PostHelpDTO {

    private Long id;
    private TechnologyFrontEnd technologyFrontEnd;
    private TechnologyBackEnd technologyBackEnd;
    private String description;
    private String github;
    private Boolean ouvert;

}
