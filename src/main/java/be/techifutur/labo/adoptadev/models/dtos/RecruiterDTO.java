package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecruiterDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private CompanyDTO company;

    public static RecruiterDTO toDTO(Recruiter entity){
        if (entity == null)
            return null;

        return RecruiterDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .company(CompanyDTO.toDTO(entity.getCompany()))
                .build();
    }

}
