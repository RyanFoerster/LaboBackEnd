package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.DevDTO;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.forms.DevPasswordUpdateForm;
import be.techifutur.labo.adoptadev.models.forms.DevProfileUpdateForm;
import be.techifutur.labo.adoptadev.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev")
public class DevController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public DevController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<DevDTO> getOne(@PathVariable Long id) {
        Dev dev = userService.getOneDev(id);
        DevDTO body = DevDTO.toDTO(dev);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{confirmationToken}")
    public ResponseEntity<?> confirmRegister(@PathVariable String confirmationToken) {
        Dev dev = userService.getDevByConfirmationToken(confirmationToken);
        dev.setEnabled(true);
        dev.setConfirmationToken(null);
        userService.updateDev(dev.getId(), dev);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<DevDTO> update(Authentication authentication, @RequestBody @Valid DevProfileUpdateForm form) {
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateDev(dev.getId(), form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping
    public ResponseEntity<DevDTO> updatePassword(Authentication authentication, @RequestBody @Valid DevPasswordUpdateForm form) {
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateDevPassword(dev.getId(), form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Authentication authentication) {
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.deleteDev(dev.getId());
        return ResponseEntity.noContent()
                .build();
    }
}
