package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmallRecruiterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private CompanyDTO company;

    public static SmallRecruiterDTO toDTO(Recruiter entity){
        if (entity == null)
            return null;

        return SmallRecruiterDTO.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .company(CompanyDTO.toDTO(entity.getCompany()))
                .build();
    }

}
