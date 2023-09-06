package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet, Long> {

    public boolean existsByName(String name);

}
