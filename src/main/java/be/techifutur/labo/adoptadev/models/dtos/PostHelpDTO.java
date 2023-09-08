package be.techifutur.labo.adoptadev.models.dtos;


import be.techifutur.labo.adoptadev.models.entities.PostHelp;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;


@Data @Builder
public class PostHelpDTO {

    private Long id;
    private TechnologyFrontEnd technologyFrontEnd;
    private TechnologyBackEnd technologyBackEnd;
    private String description;
    private String github;
    private Boolean ouvert;
    private Set<CommentDTO> comments;

    public static PostHelpDTO toDTO(PostHelp postHelp){
        if(postHelp == null){
            return null;
        }

        return PostHelpDTO.builder()
                .id(postHelp.getId())
                .technologyFrontEnd(postHelp.getTechnologyFrontEnd())
                .technologyBackEnd(postHelp.getTechnologyBackEnd())
                .description(postHelp.getDescription())
                .github(postHelp.getGithub())
                .ouvert(postHelp.getOuvert())
                .comments(postHelp.getComments().stream().map(CommentDTO::toDTO).collect(Collectors.toSet()))
                .build();
    }

}
