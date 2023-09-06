package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
