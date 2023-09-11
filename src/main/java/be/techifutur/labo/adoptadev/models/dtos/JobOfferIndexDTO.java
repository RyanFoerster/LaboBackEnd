package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.JobOffersIndex;
import lombok.Builder;
import lombok.Data;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;

import java.util.List;

@Data
@Builder
public class JobOfferIndexDTO {

    private int total;
    private List<JobOfferDTO> jobOfferDTOList;

    public static JobOfferIndexDTO toDTO(JobOffersIndex entity){
        if (entity == null)
            return null;

        return JobOfferIndexDTO.builder()
                .total(entity.getTotal())
                .jobOfferDTOList(
                        entity.getResult().stream()
                                .map(JobOfferDTO::toDTO)
                                .toList()
                )
                .build();
    }

}
