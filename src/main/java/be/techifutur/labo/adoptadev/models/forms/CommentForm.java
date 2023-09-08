package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Comment;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

    @NotNull
    @Size(min = 1, max = 500)
    private String message;



    public Comment toEntity(){

        Comment comment = new Comment();

        comment.setMessage(this.message);


        return comment;
    }

}
