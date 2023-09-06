package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Company;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDTO {

    private Long id;
    private String name;
    private String description;
    private AddressDTO address;

    public static CompanyDTO toDTO(Company entity){
        if (entity == null)
            return null;

        return CompanyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .address(AddressDTO.toDTO(entity.getAddress()))
                .build();
    }
}
