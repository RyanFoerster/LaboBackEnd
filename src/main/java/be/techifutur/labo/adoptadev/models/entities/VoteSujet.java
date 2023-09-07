package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class VoteSujet {

    @Id
    @Column(name = "vote_sujet_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vote_sujet_score")
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "dev_id")
    private Dev dev;

    @ManyToOne
    @JoinColumn(name = "post_help_id")
    private PostHelp postHelp;

}
