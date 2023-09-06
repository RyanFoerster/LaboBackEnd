package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_offer_id", nullable = false)
    private Long id;

    @Column(name = "job_offer_title", nullable = false)
    private String title;

    @Column(name = "job_offer_description", nullable = false)
    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "job_offer_front_technologies",
            joinColumns = @JoinColumn(name = "job_offer_id")
    )
    private Set<TechnologyFrontEnd> technologyFrontEnds = new HashSet<>();
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "job_offer_back_technologies",
            joinColumns = @JoinColumn(name = "job_offer_id")
    )
    private Set<TechnologyBackEnd> technologyBackEnds = new HashSet<>();

    @Column(name = "job_offer_link", nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "job_offer_recruiter")
    private Recruiter recruiter;
}
