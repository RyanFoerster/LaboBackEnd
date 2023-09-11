package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.services.MatchService;
import be.techifutur.labo.adoptadev.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;
    private final UserDetailsService userDetailsService;

    public MatchController(MatchService matchService, UserDetailsService userDetailsService) {
        this.matchService = matchService;
        this.userDetailsService = userDetailsService;
    }

    @PreAuthorize("hasAuthority('RECRUITER')")
    @PostMapping
    public ResponseEntity<Long> create(@RequestParam Long devId, Authentication authentication){
        String username = authentication.getPrincipal().toString();

        Recruiter recruiter = (Recruiter) userDetailsService.loadUserByUsername(username);

        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.create(devId, recruiter.getId()));
    }
}
