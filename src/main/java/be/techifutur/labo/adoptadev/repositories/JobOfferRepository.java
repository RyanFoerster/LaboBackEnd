package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer,Long> {
}
