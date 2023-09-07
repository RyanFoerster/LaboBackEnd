package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.DevDTO;
import be.techifutur.labo.adoptadev.models.forms.DevProfileUpdateForm;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import be.techifutur.labo.adoptadev.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev")
public class DevController {

    private final UserService userService;

    public DevController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id[0-9]+}")
    public ResponseEntity<DevDTO> update(@PathVariable Long id, @RequestBody DevProfileUpdateForm form) {
        userService.updateDev(id, form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        userService.deleteDev(id);
        return ResponseEntity.noContent()
                .build();
    }
}
