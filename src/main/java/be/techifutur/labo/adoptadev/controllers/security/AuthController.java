package be.techifutur.labo.adoptadev.controllers.security;

import be.techifutur.labo.adoptadev.models.dtos.AuthDTO;
import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.models.forms.LoginForm;
import be.techifutur.labo.adoptadev.models.forms.RegisterForm;
import be.techifutur.labo.adoptadev.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public AuthController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterForm registerForm){
        User user = registerForm.toEntity();
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm loginForm){
        String token = userService.login(loginForm.getUsername(), loginForm.getPassword());
        User user = (User) userDetailsService.loadUserByUsername(loginForm.getUsername());

        return ResponseEntity.ok(AuthDTO.toDTO(token, user));
    }
}