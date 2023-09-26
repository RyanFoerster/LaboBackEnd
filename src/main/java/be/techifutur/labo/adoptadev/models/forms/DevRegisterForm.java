package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collections;

@Data
public class DevRegisterForm {

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

    @Valid
    private DevProfileUpdateForm devProfileUpdateForm;

    @Valid
    private AddressForm addressForm;

    public Dev toEntity(){
        Dev dev = new Dev();
        dev.setUsername(this.username);
        dev.setPassword(this.password);
        dev.setFirstName(this.firstName);
        dev.setLastName(this.lastName);
        dev.setEmail(this.email);
        dev.setRole(Role.DEVELOPER);
        dev.setBirthDate(devProfileUpdateForm.toEntity().getBirthDate());
        dev.setTechnologyBackEnds(devProfileUpdateForm.getTechnologyBackEnds());
        dev.setTechnologyFrontEnds(devProfileUpdateForm.getTechnologyFrontEnds());
        dev.setDescription(devProfileUpdateForm.getDescription());
        dev.setCv(devProfileUpdateForm.getCv());
        dev.setGitHub(devProfileUpdateForm.getGitHub());
        dev.setLinkedIn(devProfileUpdateForm.getLinkedIn());
        dev.setPseudo(devProfileUpdateForm.getPseudo());
        dev.setAddress(this.addressForm.toEntity());
        return dev;
    }
}
