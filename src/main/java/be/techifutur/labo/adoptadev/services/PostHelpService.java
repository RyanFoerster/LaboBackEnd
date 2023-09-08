package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import be.techifutur.labo.adoptadev.models.entities.PostHelp;

import java.util.List;

public interface PostHelpService {

    Long add(PostHelp jobOffer, String devName);
    List<PostHelp> getAll();
    PostHelp getOne(Long id);
    void update(Long id, PostHelp entity);
    void delete(Long id);

}
