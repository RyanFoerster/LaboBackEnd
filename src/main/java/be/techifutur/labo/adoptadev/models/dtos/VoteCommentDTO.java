package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VoteCommentDTO {

    private Long id;
    private Integer score;


    public static VoteCommentDTO toDTO(VoteComment voteComment){
        if (voteComment == null){
            return null;
        }

        return VoteCommentDTO.builder()
                .id(voteComment.getId())
                .score(voteComment.getScore())
                .build();
    }

}
