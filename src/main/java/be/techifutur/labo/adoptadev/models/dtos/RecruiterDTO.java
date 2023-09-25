package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class RecruiterDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<SmallJobOfferDTO> jobOffers;
    private CompanyDTO company;

    public static RecruiterDTO toDTO(Recruiter entity){
        if (entity == null)
            return null;

        return RecruiterDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .jobOffers(
                        entity.getJobOfferSet().stream()
                                .map(SmallJobOfferDTO::toDTO)
                                .toList()
                )
                .company(CompanyDTO.toDTO(entity.getCompany()))
                .build();
    }

}
