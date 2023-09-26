package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Match;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByDevAndRecruiter(Dev dev, Recruiter recruiter);

    boolean existsByDevAndRecruiter(Dev dev, Recruiter recruiter);

    void deleteByDevAndRecruiter(Dev dev, Recruiter recruiter);

    List<Match> findByDevId(Long devId);

    List<Match> findByRecruiterId(Long recruiter);
}
