package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.PostHelp;
import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import be.techifutur.labo.adoptadev.models.entities.VoteSujet;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.stream.Collectors;

@Data @Builder
public class VoteInfoDTO {

    private Long id;
    private VoteType voteType;
    private boolean hasVoted;
    private Integer score;

    public static VoteInfoDTO toDto(VoteComment voteComment){
        if( voteComment == null )
            return null;

        return VoteInfoDTO.builder()
                .hasVoted(true)
                .voteType(voteComment.getVoteType())
                .score(voteComment.getComment().getScore())
                .build();
    }

    public static VoteInfoDTO toDTO(VoteSujet voteSujet){
        if( voteSujet == null )
            return null;

        return VoteInfoDTO.builder()
                .hasVoted(true)
                .voteType(voteSujet.getVoteType())
                .score(voteSujet.getPostHelp().getScore())
                .build();
    }

    public static VoteInfoDTO toEmptyDTO(int score){
        return VoteInfoDTO.builder()
                .hasVoted(false)
                .voteType(null)
                .score(score)
                .build();
    }

}
