package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.*;

@Entity
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

}
