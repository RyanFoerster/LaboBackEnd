package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VoteCommentDTO {
    private Long id;
    private VoteType voteType;

    public static VoteCommentDTO toDTO(VoteComment voteComment){
        if (voteComment == null){
            return null;
        }

        return VoteCommentDTO.builder()
                .id(voteComment.getId())
                .voteType(voteComment.getVoteType())
                .build();
    }
}
