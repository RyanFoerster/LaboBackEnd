package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.models.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterForm {

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

    private String firstName;

    private String lastName;

    private String email;

    private String description;

    private Set<Role> roles;

    public User toEntity(){
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setDescription(this.description);
        user.setRoles(this.roles);
        return user;
    }
}
