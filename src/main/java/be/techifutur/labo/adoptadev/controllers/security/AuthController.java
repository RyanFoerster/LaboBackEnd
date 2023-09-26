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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<UserDTO> devRegister(@RequestBody @Valid DevRegisterForm form) {
        Dev dev = form.toEntity();
        String confirmationToken = generateConfirmationToken();

        dev.setConfirmationToken(confirmationToken);

        userService.devRegister(dev);
        String email = dev.getEmail();
        String subject = "Confirmation d'inscription";
        String body = "Cliquez sur ce lien pour activer finaliser votre inscription : " + "http://localhost:4200/dev/" + confirmationToken;
        emailService.sendEmail(email, subject, body);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.toDTO(dev));
    }

    @PostMapping("/recruiter-register")


    public ResponseEntity<UserDTO> recruiterRegister(@RequestBody @Valid RecruiterRegisterForm form) {
        Recruiter recruiter = form.toEntity();
        String confirmationToken = generateConfirmationToken();

        recruiter.setConfirmationToken(confirmationToken);

        userService.recruiterRegister(recruiter);
        String email = recruiter.getEmail();
        String subject = "Confirmation d'inscription";
        String body = "Cliquez sur ce lien pour activer finaliser votre inscription : " + "http://localhost:4200/recruiter/" + confirmationToken;
        emailService.sendEmail(email, subject, body);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.toDTO(recruiter));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm loginForm) {
        String token = userService.login(loginForm.getUsername(), loginForm.getPassword());

        User user = (User) userDetailsService.loadUserByUsername(loginForm.getUsername());
        if (!user.isEnabled())
            return ResponseEntity.ok(null);

        return ResponseEntity.ok(AuthDTO.toDTO(token, user));
    }

    private String generateConfirmationToken() {
        String token = UUID.randomUUID().toString();
        return token;
    }
}
