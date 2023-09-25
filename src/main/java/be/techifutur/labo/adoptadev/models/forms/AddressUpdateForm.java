package be.techifutur.labo.adoptadev.models.forms;

import be.techifutur.labo.adoptadev.models.entities.Address;
import lombok.Data;

@Data
public class AddressUpdateForm {
    private String street;
    private String number;
    private String city;
    private String zipcode;
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
