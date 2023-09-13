package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevRepository extends JpaRepository<Dev, Long> {

    Optional<Dev> findByUsername(String pseudo);

    Optional<Dev> findByConfirmationToken(String confirmToken);


}
