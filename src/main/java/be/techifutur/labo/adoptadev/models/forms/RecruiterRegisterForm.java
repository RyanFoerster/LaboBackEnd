package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.models.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collections;

@Data
public class RecruiterRegisterForm {

    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6)
    @Pattern(
            regexp = "^(?=.*[!=@#$%^&*()_+{}\\\\[\\\\]:;<>,.?~\\\\-]).*(?=.*[A-Z]).*(?=.*[0-9]).*$",
            message = "Doit contenir une majuscule, un chiffre, un caractère spécial et minimum 6 caractères"
    )
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String description;

    public Recruiter toEntity(){
        Recruiter recruiter = new Recruiter();
        recruiter.setUsername(this.username);
        recruiter.setPassword(this.password);
        recruiter.setFirstName(this.firstName);
        recruiter.setLastName(this.lastName);
        recruiter.setEmail(this.email);
        recruiter.setDescription(this.description);
        recruiter.setRoles(Collections.singleton(Role.RECRUITER));
        return recruiter;
    }
}
