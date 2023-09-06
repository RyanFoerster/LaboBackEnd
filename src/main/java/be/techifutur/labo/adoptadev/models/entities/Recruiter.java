package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Recruiter extends User{

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "recruiter")
    private Set<JobOffer> jobOfferSet = new HashSet<>();

}
