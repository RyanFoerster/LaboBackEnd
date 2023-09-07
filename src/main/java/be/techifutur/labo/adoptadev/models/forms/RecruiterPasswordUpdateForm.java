package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecruiterPasswordUpdateForm {

    @NotBlank
    @Size(min = 6)
    @Pattern(
            regexp = "^(?=.*[!=@#$%^&*()_+{}\\\\[\\\\]:;<>,.?~\\\\-]).*(?=.*[A-Z]).*(?=.*[0-9]).*$",
            message = "Doit contenir une majuscule, un chiffre, un caractère spécial et minimum 6 caractères"
    )
    private String password;

    public Recruiter toEntity(){
        Recruiter recruiter = new Recruiter();

        recruiter.setPassword(this.password);

        return recruiter;
    }
}
