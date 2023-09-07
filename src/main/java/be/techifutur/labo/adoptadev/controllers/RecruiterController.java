package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.DevDTO;
import be.techifutur.labo.adoptadev.models.dtos.RecruiterDTO;
import be.techifutur.labo.adoptadev.models.forms.DevProfileUpdateForm;
import be.techifutur.labo.adoptadev.models.forms.RecruiterProfileUpdateForm;
import be.techifutur.labo.adoptadev.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    private final UserService userService;

    public RecruiterController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id[0-9]+}")
    public ResponseEntity<RecruiterDTO> update(@PathVariable Long id, @RequestBody RecruiterProfileUpdateForm form){
        userService.updateRecruiter(id,form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }
    @DeleteMapping("/{id:[0-9]+}")
    ResponseEntity<?> delete(@PathVariable Long id){
        userService.deleteRecruiter(id);
        return ResponseEntity.noContent()
                .build();
    }
}
