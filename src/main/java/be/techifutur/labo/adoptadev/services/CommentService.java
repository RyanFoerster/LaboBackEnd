package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.Comment;

import java.util.List;

public interface CommentService {

    Long add(Comment comment, String devName);
    List<Comment> findAll();
    Comment findOne(Long id);
    void update(Long id, Comment comment);
    void delete(Long id);

}
