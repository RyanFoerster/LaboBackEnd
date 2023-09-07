package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dev extends User {

    @Column(name = "dev_birthdate")
    private LocalDate birthDate;

    @Column(name = "dev_technologies_back_end")
    private Set<TechnologyBackEnd> technologiesBackEnd = new HashSet<>();//TODO enum Technologies

    @Column(name = "dev_technologies_front_end")
    private Set<TechnologyFrontEnd> technologiesFrontEnd = new HashSet<>();//TODO enum Technologies

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
    private List<PostHelp> posts;


}
