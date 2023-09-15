package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.MatchDTO;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;
    private final UserDetailsService userDetailsService;

    public MatchController(MatchService matchService, UserDetailsService userDetailsService) {
        this.matchService = matchService;
        this.userDetailsService = userDetailsService;
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody Long devId, Authentication authentication) {
        String username = authentication.getPrincipal().toString();

        Recruiter recruiter = (Recruiter) userDetailsService.loadUserByUsername(username);

        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.create(devId, recruiter.getId()));
    }

    @PreAuthorize("hasAnyRole('RECRUITER', 'DEVELOPER')")
    @GetMapping
    public ResponseEntity<List<MatchDTO>> getMatchByConnectedUser(Authentication authentication) {

        String username = authentication.getPrincipal().toString();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<MatchDTO> matches = authorities.contains(Role.DEVELOPER.getAuthority()) ?
                matchService.getMatchByUser(username)
                        .stream()
                        .map(MatchDTO::toDevDTO)
                        .toList()
                : matchService.getMatchByUser(username)
                    .stream()
                    .map(MatchDTO::toRecruiterDTO)
                    .toList();

        return ResponseEntity.ok(matches);
    }
}
