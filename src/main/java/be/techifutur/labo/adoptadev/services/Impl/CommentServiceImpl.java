package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.NameNotFoundException;
import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.models.entities.Comment;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import be.techifutur.labo.adoptadev.repositories.CommentRepository;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.VoteCommentRepository;
import be.techifutur.labo.adoptadev.services.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final DevRepository devRepository;
    private final CommentRepository commentRepository;
    private final VoteCommentRepository voteCommentRepository;

    public CommentServiceImpl(DevRepository devRepository,
                              CommentRepository commentRepository,
                              VoteCommentRepository voteCommentRepository) {
        this.devRepository = devRepository;
        this.commentRepository = commentRepository;
        this.voteCommentRepository = voteCommentRepository;
    }

    @Override
    public Long add(Comment comment, String devName) {

        Dev dev = devRepository.findByUsername(devName).orElseThrow(
                () -> new NameNotFoundException(devName, Dev.class)
        );

        comment.setDev(dev);

        return commentRepository.save(comment).getId();
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll().stream().toList();
    }

    @Override
    public Comment findOne(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id, Comment.class)
        );
    }

    @Override
    public void update(Long id, Comment comment) {

        Comment entity = findOne(id);

        entity.setMessage(comment.getMessage());

    }

    @Override
    public void delete(Long id) {

        Comment comment = findOne(id);
        commentRepository.delete(comment);

    }
}
