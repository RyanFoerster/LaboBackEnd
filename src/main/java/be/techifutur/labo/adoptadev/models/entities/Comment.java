package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.VoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity @Getter
@Setter
public class Comment {

    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "post_help_id")
    private PostHelp post;

    @ManyToOne
    @JoinColumn(name = "dev_id")
    private Dev dev;

    @OneToMany(mappedBy = "comment")
    private Set<VoteComment> voteComments;

    @Column(name = "Score")
    private Integer score = 0;


    public int getScore() {

        for (VoteComment vote : voteComments) {
            score += (vote.getVoteType() == VoteType.UPVOTE) ? 1 : -1;
        }
        setScore(score);
        return score;
    }

}
