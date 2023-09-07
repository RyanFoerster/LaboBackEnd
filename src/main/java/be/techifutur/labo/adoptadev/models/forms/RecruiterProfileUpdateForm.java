package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RecruiterProfileUpdateForm {

    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String description;
    private String companyName;

    public Recruiter toEntity(){
        Recruiter recruiter = new Recruiter();

        recruiter.setFirstName(this.firstName);
        recruiter.setLastName(this.lastName);
        recruiter.setEmail(this.email);
        recruiter.setDescription(this.description);
        return recruiter;
    }

}
