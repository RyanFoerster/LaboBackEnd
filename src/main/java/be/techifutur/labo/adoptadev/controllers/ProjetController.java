package be.techifutur.labo.adoptadev.controllers;


import be.techifutur.labo.adoptadev.models.dtos.ProjetDTO;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.forms.ProjetForm;
import be.techifutur.labo.adoptadev.services.ProjetService;
import be.techifutur.labo.adoptadev.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/projet")
@RestController
public class ProjetController {

    private final ProjetService projetService;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public ProjetController(ProjetService projetService,
                            JwtUtil jwtUtil,
                            UserDetailsService userDetailsService
    ) {
        this.projetService = projetService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping
    public ResponseEntity<List<ProjetDTO>> getAll(Authentication authentication){
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());

        return ResponseEntity.ok(
                projetService.getAllProjects(dev.getId())
                        .stream()
                        .map(ProjetDTO::toDTO)
                        .toList());
    }

    @PostMapping
    public ResponseEntity<Long> addProjet(@RequestBody ProjetForm form, Authentication authentication){

        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());


        return ResponseEntity.ok(projetService.add(form.toEntity(), dev.getId()));

    }

    @PostMapping("/{projetId}")
    public ResponseEntity<?> register(Authentication authentication,
                                      @PathVariable Long projetId){

        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        System.out.println("dev = " + dev.toString());
        projetService.register(dev.getId(), projetId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dev" + dev.getId() + " added in the projet with success");

    }

    @DeleteMapping("/{projetId}")
    public ResponseEntity<?> unregister(Authentication authentication,
                                        @PathVariable Long projetId){
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());

        projetService.unregister(dev.getId(), projetId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dev" + dev.getId() + " deleted from the projet with success");
    }



}
