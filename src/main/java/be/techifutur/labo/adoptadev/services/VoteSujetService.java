package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.VoteSujet;
import be.techifutur.labo.adoptadev.models.enums.VoteType;

public interface VoteSujetService {

    public VoteSujet addVote(Long postHelpId, Long devId, VoteType voteType);

}
