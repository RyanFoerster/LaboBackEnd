package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.RecruiterDTO;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
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
}
