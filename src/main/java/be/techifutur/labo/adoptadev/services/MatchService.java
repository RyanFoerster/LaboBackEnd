package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Match;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    Long create(Long devId, Long recruiterId);

    Optional<Match> getOne(Long devId, Long recruiterId);

    List<Match> getAll();

    Match update(Long id, Match match);

    void delete(Long devId, Long recruiterId);

    List<Match> getMatchByUser(String username);

    Match findById(Long matchId);
}
