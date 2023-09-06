package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    private final UserService userService;

    public RecruiterController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id:[0-9]+}")
    ResponseEntity<?> delete(@PathVariable Long id){
        userService.deleteRecruiter(id);
        return ResponseEntity.noContent()
                .build();
    }
}
