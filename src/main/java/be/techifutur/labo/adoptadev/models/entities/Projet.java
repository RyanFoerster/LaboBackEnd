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
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projet_id", nullable = false)
    private Long id;

    @Column(name = "projet_name", nullable = false)
    private String name;

    @Column(name = "projet_technologie_frontend")
    private TechnologyFrontEnd technologyFrontEnd;

    @Column(name = "projet_technologie_backend")
    private TechnologyBackEnd technologyBackEnd;

    @Column(name = "projet_description", nullable = false)
    private String description;

    @Column(name = "projet_github", nullable = false)
    private String github;

    @OneToMany(mappedBy = "projet")
    private Set<Participant> participants = new HashSet<>();

}
