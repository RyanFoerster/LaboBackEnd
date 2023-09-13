package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data @Builder
public class CommentDTO {


    private Long id;
    private String message;
    private int score;
    private DevDTO dev;
    private Set<VoteCommentDTO> voteComments;


    public static CommentDTO toDTO(Comment comment){
        if(comment == null){
            return null;
        }

        return CommentDTO.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .dev(DevDTO.toDTO(comment.getDev()))
                .voteComments(comment.getVoteComments().stream().map(VoteCommentDTO::toDTO).collect(Collectors.toSet()))
                .score(comment.getScore())
                .build();
    }

}
