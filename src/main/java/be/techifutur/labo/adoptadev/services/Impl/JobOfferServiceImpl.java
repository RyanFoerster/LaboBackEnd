package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.repositories.JobOfferRepository;
import be.techifutur.labo.adoptadev.repositories.RecruiterRepository;
import be.techifutur.labo.adoptadev.services.JobOfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final RecruiterRepository recruiterRepository;


    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
        this.jobOfferRepository = jobOfferRepository;
    }

    @Override
    public Long add(JobOffer jobOffer, String recruiterName) {
        Recruiter recruiter = recruiterRepository.findByUsername(recruiterName).orElseThrow(()-> new RuntimeException("User doesn't exist"));
        jobOffer.setRecruiter(recruiter);
        return jobOfferRepository.save(jobOffer).getId();
    }

    @Override
    public List<JobOffer> getAll() {
        return jobOfferRepository.findAll();
    }

    @Override
    public JobOffer getOne(Long id) {
        return jobOfferRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Job inexistant"));
    }

    @Override
    public void update(Long id, JobOffer jobOffer) {

        JobOffer entity = getOne(id);

        entity.setTitle(jobOffer.getTitle());
        entity.setDescription(jobOffer.getDescription());
        entity.setTechnologyFrontEnds(jobOffer.getTechnologyFrontEnds());
        entity.setTechnologyBackEnds(jobOffer.getTechnologyBackEnds());
        entity.setLink(jobOffer.getLink());
        entity.setRecruiter(jobOffer.getRecruiter());

        jobOfferRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        jobOfferRepository.delete(getOne(id));
    }
}
