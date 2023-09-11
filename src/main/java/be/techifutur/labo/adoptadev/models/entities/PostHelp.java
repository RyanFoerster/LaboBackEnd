package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "post_help")
@Getter
@Setter
public class PostHelp {

    @Id
    @Column(name = "post_help_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_help_techno_front")
    private TechnologyFrontEnd technologyFrontEnd;


    @Enumerated(EnumType.STRING)
    @Column(name = "post_help_techno_back")
    private TechnologyBackEnd technologyBackEnd;

    @Column(name = "post_help_description", nullable = false)
    private String description;

    @Column(name = "post_help_github", nullable = false)
    private String github;

    @Column(name = "post_help_ouvert", nullable = false)
    private Boolean ouvert = true;


    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "dev_id")
    private Dev dev;

    @OneToMany(mappedBy = "postHelp")
    private Set<VoteSujet> voteSujets;




}
