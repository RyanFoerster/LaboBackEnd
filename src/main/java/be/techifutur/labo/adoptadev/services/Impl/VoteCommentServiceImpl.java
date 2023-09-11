package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.models.entities.Comment;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import be.techifutur.labo.adoptadev.repositories.CommentRepository;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.VoteCommentRepository;
import be.techifutur.labo.adoptadev.services.VoteCommentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteCommentServiceImpl implements VoteCommentService {
    private final VoteCommentRepository voteCommentRepository;
    private final CommentRepository commentRepository;
    private final DevRepository devRepository;

    public VoteCommentServiceImpl(VoteCommentRepository voteCommentRepository,
                                  CommentRepository commentRepository,
                                  DevRepository devRepository) {
        this.voteCommentRepository = voteCommentRepository;
        this.commentRepository = commentRepository;
        this.devRepository = devRepository;
    }


    @Override
    public VoteComment addVote(Long commentId, Long devId, VoteType voteType) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(commentId, Comment.class));

        Dev dev = devRepository.findById(devId)
                .orElseThrow(() -> new ResourceNotFoundException(devId, Dev.class));


        Optional<VoteComment> existingVote = voteCommentRepository.findByCommentAndDev(comment, dev);
        // Va servir pour savoir si un vote existe déjà ou non

        if (existingVote.isPresent()) {
            if (existingVote.get().getVoteType() == voteType) {
                // annuler le vote, donc soustraire le score actuel
                adjustScore(comment, existingVote.get().getVoteType() == VoteType.UPVOTE ? -1 : 1);
                voteCommentRepository.delete(existingVote.get());
                return null;
            } else {
                // changer le type de vote, donc ajuster le score en conséquence
                adjustScore(comment, existingVote.get().getVoteType() == VoteType.UPVOTE ? -2 : 2);
                existingVote.get().setVoteType(voteType);
                return voteCommentRepository.save(existingVote.get());
            }
        } else {
            // nouveau vote, donc ajouter au score
            adjustScore(comment, voteType == VoteType.UPVOTE ? 1 : -1);

            VoteComment voteComment = new VoteComment();
            voteComment.setDev(dev);
            voteComment.setComment(comment);
            voteComment.setVoteType(voteType);

            return voteCommentRepository.save(voteComment);
        }
    }

    private void adjustScore(Comment comment, int adjustment) {
        comment.setScore(comment.getScore() + adjustment);
        commentRepository.save(comment);
    }
}
