package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JobOfferForm {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Set<TechnologyFrontEnd> technologyFrontEnds;
    private Set<TechnologyBackEnd> technologyBackEnds;
    @NotBlank
    private String link;

    public JobOffer toEntity(){
        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitle(this.title);
        jobOffer.setDescription(this.description);
        jobOffer.setTechnologyFrontEnds(this.technologyFrontEnds);
        jobOffer.setTechnologyBackEnds(this.technologyBackEnds);
        jobOffer.setLink(this.link);
        return jobOffer;
    }
}
