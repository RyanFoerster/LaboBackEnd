package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.AddressDTO;
import be.techifutur.labo.adoptadev.models.dtos.CompanyDTO;
import be.techifutur.labo.adoptadev.models.dtos.DevDTO;
import be.techifutur.labo.adoptadev.models.dtos.RecruiterDTO;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.models.forms.AddressUpdateForm;
import be.techifutur.labo.adoptadev.models.forms.CompanyUpdateForm;
import be.techifutur.labo.adoptadev.models.forms.RecruiterPasswordUpdateForm;
import be.techifutur.labo.adoptadev.models.forms.RecruiterProfileUpdateForm;
import be.techifutur.labo.adoptadev.services.UserService;
import be.techifutur.labo.adoptadev.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public RecruiterController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<RecruiterDTO> getOne(@PathVariable Long id){
        Recruiter recruiter = userService.getOneRecruiter(id);
        RecruiterDTO body = RecruiterDTO.toDTO(recruiter);
        return ResponseEntity.ok(body);
    }
    @PatchMapping("/{confirmationToken}")
    public ResponseEntity<?> confirmRegister(@PathVariable String confirmationToken){
        Recruiter recruiter = userService.getRecByConfirmationToken(confirmationToken);
        recruiter.setEnabled(true);
        recruiter.setConfirmationToken(null);
        userService.updateRecruiter(recruiter.getId(), recruiter);
        return ResponseEntity.noContent()
                .build();
    }
    @PutMapping
    public ResponseEntity<RecruiterDTO> update(Authentication authentication, @RequestBody @Valid RecruiterProfileUpdateForm form) {

        Recruiter recruiter = (Recruiter) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());

        userService.updateRecruiter(recruiter.getId(), form.toEntity());

        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping
    public ResponseEntity<RecruiterDTO> updatePassword(Authentication authentication, @RequestBody @Valid RecruiterPasswordUpdateForm form){
        Recruiter recruiter = (Recruiter) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateRecruiterPassword(recruiter.getId(), form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }
    @DeleteMapping
    ResponseEntity<?> delete(Authentication authentication) {
        Recruiter recruiter = (Recruiter) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.deleteRecruiter(recruiter.getId());
        return ResponseEntity.noContent()
                .build();
    }
    @PutMapping("/company")
    public ResponseEntity<CompanyDTO> updateRecruiterCompany(Authentication authentication, @RequestBody CompanyUpdateForm form){
        Recruiter recruiter = (Recruiter) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateRecruiterCompany(recruiter.getId(), form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }
    @PutMapping("/company/address")
    public ResponseEntity<AddressDTO> updateCompanyAddress(Authentication authentication, @RequestBody AddressUpdateForm form){
        Recruiter recruiter = (Recruiter) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateCompanyAddress(recruiter.getId(), form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }
}
