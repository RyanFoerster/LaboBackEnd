package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dev extends User {

    @Column(name = "dev_birthdate")
    private LocalDate birthDate;

    @Column(name = "dev_technologies_back_end")
    @Enumerated(EnumType.STRING)
    private Set<TechnologyBackEnd> technologyBackEnds = new HashSet<>();//TODO enum Technologies

    @Column(name = "dev_technologies_front_end")
    @Enumerated(EnumType.STRING)
    private Set<TechnologyFrontEnd> technologyFrontEnds = new HashSet<>();//TODO enum Technologies

    @Column(name = "dev_github")
    private String gitHub;

    @Column(name = "dev_linkedin")
    private String linkedIn;

    @Column(name = "dev_cv")
    private String cv; //TODO à revoir

    @Column(name = "dev_pseudo", unique = true)
    private String pseudo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "address_id"
    )
    private Address address;

    @OneToMany(mappedBy = "dev")
    private Set<Participant> participants = new HashSet<>();


    @OneToMany(mappedBy = "dev")
    private List<PostHelp> posts = new ArrayList<>();

    @OneToMany(mappedBy = "dev")
    private Set<Match> matches = new LinkedHashSet<>();

}
