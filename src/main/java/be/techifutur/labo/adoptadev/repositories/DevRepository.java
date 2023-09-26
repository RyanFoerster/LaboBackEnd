package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DevRepository extends JpaRepository<Dev, Long> {

    Optional<Dev> findByUsername(String pseudo);

    Optional<Dev> findByConfirmationToken(String confirmToken);


}
