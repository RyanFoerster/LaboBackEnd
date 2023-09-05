package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String name;

    @Column(name = "company_description", nullable = false)
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "company_address_id", nullable = false)
    private Address address;


}
