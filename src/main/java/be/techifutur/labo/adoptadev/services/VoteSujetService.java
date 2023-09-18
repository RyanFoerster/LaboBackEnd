package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.VoteSujet;
import be.techifutur.labo.adoptadev.models.enums.VoteType;

import java.util.Optional;

public interface VoteSujetService {

    public VoteSujet addVote(Long postHelpId, Long devId, VoteType voteType);
    public Optional<VoteSujet> getVote(Long postHelpId, Long devId);

}
