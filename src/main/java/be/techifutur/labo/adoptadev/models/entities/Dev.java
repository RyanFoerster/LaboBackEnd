package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.TechnologyBackEnd;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Dev extends User {

    @Column(name = "dev_birthdate", nullable = false)
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

    @Column(name = "dev_pseudo", nullable = false, unique = true)
    private String pseudo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "address_id",
            nullable = false
    )
    private Address address;

}
