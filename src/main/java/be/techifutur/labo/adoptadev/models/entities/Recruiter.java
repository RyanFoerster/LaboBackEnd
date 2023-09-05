package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;

@Entity
@Getter
@Setter
public class Recruiter extends User{

    @OneToMany
    private Set<JobOffer> jobOfferList;

}
