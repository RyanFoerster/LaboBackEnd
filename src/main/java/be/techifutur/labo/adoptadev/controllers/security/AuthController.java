package be.techifutur.labo.adoptadev.controllers.security;

import be.techifutur.labo.adoptadev.models.dtos.AuthDTO;
import be.techifutur.labo.adoptadev.models.dtos.UserDTO;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.models.forms.DevRegisterForm;
import be.techifutur.labo.adoptadev.models.forms.LoginForm;
import be.techifutur.labo.adoptadev.models.forms.RecruiterRegisterForm;
import be.techifutur.labo.adoptadev.services.EmailService;
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
    private final EmailService emailService;

    public AuthController(UserService userService, UserDetailsService userDetailsService, EmailService emailService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    @PostMapping("/dev-register")
    public ResponseEntity<UserDTO> devRegister(@RequestBody @Valid DevRegisterForm form){

        Dev dev = form.toEntity();
        userService.devRegister(dev);
        String email = dev.getEmail();
        String subject = "Confirmation d'inscription";
        String body = "Bienvenue dans la communauté adoptAdev. Vous pouvez désormais vous connecter en tant que développeur ";
        emailService.sendEmail(email,subject,body);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.toDTO(dev));
    }

    @PostMapping("/recruiter-register")
    public ResponseEntity<UserDTO> recruiterRegister(@RequestBody @Valid RecruiterRegisterForm form){
        Recruiter recruiter = form.toEntity();
        userService.recruiterRegister(recruiter);
        String email = recruiter.getEmail();
        String subject = "Confirmation d'inscription";
        String body = "Bienvenue dans la communauté adoptAdev. Vous pouvez désormais vous connecter en tant que recruteur ";
        emailService.sendEmail(email,subject,body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm loginForm){
        String token = userService.login(loginForm.getUsername(), loginForm.getPassword());
        User user = (User) userDetailsService.loadUserByUsername(loginForm.getUsername());

        return ResponseEntity.ok(AuthDTO.toDTO(token, user));
    }
}
