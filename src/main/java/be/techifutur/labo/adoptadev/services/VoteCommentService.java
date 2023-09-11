package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import be.techifutur.labo.adoptadev.models.enums.VoteType;

public interface VoteCommentService {

    public VoteComment addVote(Long commentId, Long devId, VoteType voteType);

}
