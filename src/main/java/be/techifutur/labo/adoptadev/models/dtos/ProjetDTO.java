package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Projet;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjetDTO {

    private Long id;
    private String name;
    private TechnologyFrontEnd technologyFrontEnd;
    private TechnologyBackEnd technologyBackEnd;
    private String description;
    private String github;

    public  static ProjetDTO toDTO(Projet projet){
        if (projet == null){
            return null;
        }

        return  ProjetDTO.builder()
                .id(projet.getId())
                .name(projet.getName())
                .technologyFrontEnd(projet.getTechnologyFrontEnd())
                .technologyBackEnd(projet.getTechnologyBackEnd())
                .description(projet.getDescription())
                .github(projet.getGithub())
                .build();
    }

}
