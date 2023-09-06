package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Participant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantDTO {

    private Long projetId;
    private String projetName;


    public static ParticipantDTO toDTO(Participant entity){

        if (entity == null){
            return null;
        }

        return ParticipantDTO.builder()
                .projetId(entity.getProjet().getId())
                .projetName(entity.getProjet().getName())
                .build();

    }

}
