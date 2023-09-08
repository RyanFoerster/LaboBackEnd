package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.models.entities.Comment;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import be.techifutur.labo.adoptadev.repositories.CommentRepository;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.VoteCommentRepository;
import be.techifutur.labo.adoptadev.services.VoteService;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteCommentRepository voteCommentRepository;
    private final CommentRepository commentRepository;
    private final DevRepository devRepository;

    public VoteServiceImpl(VoteCommentRepository voteCommentRepository,
                           CommentRepository commentRepository,
                           DevRepository devRepository) {
        this.voteCommentRepository = voteCommentRepository;
        this.commentRepository = commentRepository;
        this.devRepository = devRepository;
    }



    public VoteComment addVote(Long commentId, Long devId, VoteType voteType) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(commentId, Comment.class));

        Dev dev = devRepository.findById(devId)
                .orElseThrow(() -> new ResourceNotFoundException(devId, Dev.class));

        VoteComment voteComment = new VoteComment();
        voteComment.setDev(dev);
        voteComment.setComment(comment);
        voteComment.setVoteType(voteType);

        return voteCommentRepository.save(voteComment);
    }
}
