package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressForm {
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String city;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String country;

    public Address toEntity(){
        Address address = new Address();
        address.setStreet( this.street );
        address.setNumber( this.number );
        address.setCity( this.city );
        address.setZipcode( this.zipcode );
        address.setCountry( this.country );
        return address;
    }
}
