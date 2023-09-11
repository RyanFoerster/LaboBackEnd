package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import be.techifutur.labo.adoptadev.models.entities.JobOffersIndex;

import java.util.List;

public interface JobOfferService  {

    Long add(JobOffer jobOffer, String recruiterName);

    JobOffersIndex getAll();

    JobOffer getOne(Long id);

    void update(Long id,JobOffer entity);
    void delete(Long id);
}
