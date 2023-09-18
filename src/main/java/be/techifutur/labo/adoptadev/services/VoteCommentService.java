package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import be.techifutur.labo.adoptadev.models.entities.VoteSujet;
import be.techifutur.labo.adoptadev.models.enums.VoteType;

import java.util.Optional;

public interface VoteCommentService {

    public VoteComment addVote(Long commentId, Long devId, VoteType voteType);
    public Optional<VoteComment> getVote(Long commentId, Long devId);

}
