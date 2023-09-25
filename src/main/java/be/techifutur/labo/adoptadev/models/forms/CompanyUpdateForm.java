package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Company;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class CompanyUpdateForm {
    private String name;
    private String description;

    public Company toEntity(){
        Company company = new Company();
        company.setName(this.name);
        company.setDescription(this.description);
        return company;
    }
}
