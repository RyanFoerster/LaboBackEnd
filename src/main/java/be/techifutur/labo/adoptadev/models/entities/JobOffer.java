package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.Technologie;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
@Entity
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_offer_id", nullable = false)
    private Long id;

    @Column(name = "job_offer_title", nullable = false)
    private String title;

    @Column(name = "job_offer_description")
    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "job_offer_technologie",
            joinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Technologie> technologies = new HashSet<>();

    @Column(name = "job_offer_link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "job_offer_link", nullable = false)
    private Recruiter recruiter;
}
