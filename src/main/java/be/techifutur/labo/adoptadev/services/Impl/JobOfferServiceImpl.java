package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.NameNotFoundException;
import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.models.entities.JobOffer;
import be.techifutur.labo.adoptadev.models.entities.JobOffersIndex;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.repositories.JobOfferRepository;
import be.techifutur.labo.adoptadev.repositories.RecruiterRepository;
import be.techifutur.labo.adoptadev.services.JobOfferService;
import jakarta.mail.search.SearchTerm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        Recruiter recruiter = recruiterRepository.findByUsername(recruiterName)
                .orElseThrow(()-> new NameNotFoundException(recruiterName,Recruiter.class));
        jobOffer.setRecruiter(recruiter);
//        recruiter.getJobOfferSet().add(jobOffer);
        return jobOfferRepository.save(jobOffer).getId();
    }

    @Override
    public JobOffersIndex getAll() {
        int total=0;
        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        for (JobOffer element: jobOfferList) {
            total++;
        } 
        
        JobOffersIndex list = new JobOffersIndex();
        list.setTotal(total);
        list.setResult(jobOfferList);
        return list;
    }

    @Override
    public JobOffer getOne(Long id) {
        return jobOfferRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(id,JobOffer.class));
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
