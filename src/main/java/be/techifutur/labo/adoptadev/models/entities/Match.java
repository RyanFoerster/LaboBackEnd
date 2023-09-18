package be.techifutur.labo.adoptadev.models.entities;

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
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @ManyToOne
    private Dev dev;

    @ManyToOne
    private Recruiter recruiter;

    @OneToMany(mappedBy = "match")
    private Set<Message> messages = new HashSet<>();
}
