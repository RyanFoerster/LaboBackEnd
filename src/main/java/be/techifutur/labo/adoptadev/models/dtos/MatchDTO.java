package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Match;
import lombok.Builder;
import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
@Builder
public class MatchDTO {

    private Long id;

    private String name;

    private List<MessageDTO> messages;

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
                .messages(match.getMessages().stream().map(MessageDTO::toDTO).sorted(Comparator.comparing(MessageDTO::getCreatedAt)).toList())
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
                .messages(match.getMessages().stream().map(MessageDTO::toDTO).sorted(Comparator.comparing(MessageDTO::getCreatedAt)).toList())
                .build();
    }
}
