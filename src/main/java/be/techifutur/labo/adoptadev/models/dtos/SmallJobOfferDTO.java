package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SmallJobOfferDTO {

    private Long id;
    private String title;
    private String description;
    private Set<TechnologyFrontEnd> technologyFrontEnds;
    private Set<TechnologyBackEnd> technologyBackEnds;
    private String link;

    public static SmallJobOfferDTO toDTO(JobOffer entity){
        if (entity == null)
            return null;

        return SmallJobOfferDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .technologyFrontEnds(entity.getTechnologyFrontEnds())
                .technologyBackEnds(entity.getTechnologyBackEnds())
                .link(entity.getLink())
                .build();
    }
}
