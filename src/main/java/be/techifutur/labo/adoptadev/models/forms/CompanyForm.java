package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Company;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyForm {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Valid
    private AddressForm address;

    public Company toEntity(){
        Company company = new Company();
        company.setName(this.name);
        company.setAddress(this.address.toEntity());
        return company;
    }
}
