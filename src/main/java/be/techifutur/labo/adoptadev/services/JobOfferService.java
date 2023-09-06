package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;

import java.util.List;

public interface JobOfferService  {

    Long add(JobOffer jobOffer, String recruiterName);

    List<JobOffer> getAll();

    JobOffer getOne(Long id);

    void update(Long id,JobOffer entity);
    void delete(Long id);
}
