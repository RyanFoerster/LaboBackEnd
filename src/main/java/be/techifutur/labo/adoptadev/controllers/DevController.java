package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.repositories.UserRepository;
import be.techifutur.labo.adoptadev.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class DevController {

    private final UserService userService;

    public DevController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        userService.deleteDev(id);
        return ResponseEntity.noContent()
                .build();
    }
}
