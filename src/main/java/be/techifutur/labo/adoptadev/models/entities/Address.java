package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;

    @Column(name = "address_street", nullable = false)
    private String street;

    @Column(name = "address_number", nullable = false)
    private String number;

    @Column(name = "address_city", nullable = false)
    private String city;

    @Column(name = "address_zipcode", nullable = false)
    private String zipcode;

    @Column(name = "address_country", nullable = false)
    private String country;

}