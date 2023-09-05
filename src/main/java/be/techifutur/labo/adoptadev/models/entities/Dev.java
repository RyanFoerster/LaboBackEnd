package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Dev extends User {

    @Column(name = "dev_birthdate")
    private LocalDate birthDate;
    @Column(name = "dev_technologies")
    private String technologies;//TODO enum Technologies
    @Column(name = "dev_github")
    private String gitHub;
    @Column(name = "dev_linkedin")
    private String linkedIn;
    @Column(name = "dev_cv")
    private String cv; //TODO à revoir
    @Column(name = "dev_pseudo")
    private String pseudo;

}
