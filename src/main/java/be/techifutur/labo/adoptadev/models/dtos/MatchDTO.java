package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Match;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDTO {

    private Long id;

    private String name;

    /**
     * Point de vue du développeur
     * @param match
     * @return
     */
    public static MatchDTO toDevDTO(Match match){
        if(match == null){
            return null;
        }

        return MatchDTO.builder()
                .id(match.getId())
                .name(match.getRecruiter().getFirstName() + " " + match.getRecruiter().getLastName())
                .build();
    }


    /**
     * Point de vue du recruteur
     * @param match
     * @return
     */
    public static MatchDTO toRecruiterDTO(Match match){
        if(match == null){
            return null;
        }

        return MatchDTO.builder()
                .id(match.getId())
                .name(match.getDev().getFirstName() + " " + match.getDev().getLastName())
                .build();
    }
}
