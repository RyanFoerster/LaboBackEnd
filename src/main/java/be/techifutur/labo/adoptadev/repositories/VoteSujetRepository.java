package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteSujetRepository extends JpaRepository<VoteSujet, Long> {

    Optional<VoteSujet> findByPostHelpAndDev(PostHelp comment, Dev dev);

}
