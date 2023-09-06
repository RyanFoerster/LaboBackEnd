package be.techifutur.labo.adoptadev.controllers;


import be.techifutur.labo.adoptadev.models.forms.ProjetForm;
import be.techifutur.labo.adoptadev.services.ProjetService;
import be.techifutur.labo.adoptadev.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/projet")
@RestController
public class ProjetController {

    private final ProjetService projetService;

    private final JwtUtil jwtUtil;

    public ProjetController(ProjetService projetService, JwtUtil jwtUtil) {
        this.projetService = projetService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping
    public ResponseEntity<Long> addProjet(@RequestBody ProjetForm form){

        return ResponseEntity.ok(projetService.add(form.toEntity()));

    }

    @PostMapping("/{projetId}")
    public ResponseEntity<?> register(Authentication authentication,
                                      @PathVariable Long projetId){

        String token = authentication.getCredentials().toString();
        String tokenId = jwtUtil.getId(token);
        Long devId = Long.parseLong(tokenId);  // Convertit la chaîne en Long

        projetService.register(devId, projetId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dev" + devId + " added in the projet with success");

    }

    @DeleteMapping("/{projetId}")
    public ResponseEntity<?> unregister(Authentication authentication,
                                        @PathVariable Long projetId){
        String token = authentication.getCredentials().toString();
        String tokenId = jwtUtil.getId(token);
        Long devId = Long.parseLong(tokenId);

        projetService.unregister(devId, projetId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dev" + devId + " deleted from the projet with success");
    }



}
