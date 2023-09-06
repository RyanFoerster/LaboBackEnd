package be.techifutur.labo.adoptadev.models.forms;

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

    public CommentForm toEntity(){

        CommentForm commentForm = new CommentForm();

        commentForm.setMessage(this.message);

        return commentForm;
    }

}
