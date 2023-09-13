package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recruiter extends User{

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Company company;

    @OneToMany(mappedBy = "recruiter")
    private Set<JobOffer> jobOfferSet = new HashSet<>();

    @OneToMany(mappedBy = "recruiter")
    private Set<Match> matches = new LinkedHashSet<>();

}
