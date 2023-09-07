package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter
@Setter
public class VoteComment {

    @Id
    @Column(name = "vote_comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vote_comment_score")
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "dev_id")
    private Dev dev;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
