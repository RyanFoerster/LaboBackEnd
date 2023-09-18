package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.VoteSujet;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VoteSujetDTO {
    private Long id;
    private VoteType voteType;

    public static VoteSujetDTO toDTO(VoteSujet voteSujet) {
        if (voteSujet == null) {
            return null;
        }
        return VoteSujetDTO.builder()
                .id(voteSujet.getId())
                .voteType(voteSujet.getVoteType())
                .build();
    }
}