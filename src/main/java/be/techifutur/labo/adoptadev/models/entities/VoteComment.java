package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.VoteType;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type")
    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "dev_id")
    private Dev dev;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
