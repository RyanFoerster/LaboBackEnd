package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.JobOfferDTO;
import be.techifutur.labo.adoptadev.models.dtos.JobOfferIndexDTO;
import be.techifutur.labo.adoptadev.models.forms.JobOfferForm;
import be.techifutur.labo.adoptadev.models.forms.JobOfferToUpdateForm;
import be.techifutur.labo.adoptadev.services.JobOfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody @Valid JobOfferForm form,Authentication authentication){
        String recruiterName = authentication.getName();
        System.out.println(recruiterName+"RECRUTEURNAME");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobOfferService.add(form.toEntity(),recruiterName));
    }

    @GetMapping
    public ResponseEntity<JobOfferIndexDTO> findAll(){
        return ResponseEntity.ok(JobOfferIndexDTO.toDTO(jobOfferService.getAll()));
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<JobOfferDTO> findOne(@PathVariable Long id){
        return ResponseEntity.ok(JobOfferDTO.toDTO(jobOfferService.getOne(id)));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody JobOfferToUpdateForm form){
        jobOfferService.update(id,form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        jobOfferService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
