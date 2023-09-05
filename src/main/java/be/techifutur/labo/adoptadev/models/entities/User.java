package be.techifutur.labo.adoptadev.models.entities;


import be.techifutur.labo.adoptadev.models.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@Inheritance
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_username")
    private String username;
    @Column(name = "user_password")
    private String password;
    @Column(name="user_name")
    private String name;
    @Column(name="user_email")
    private String email;
    @Column(name = "user_description")
    private String description;
    @Column(name = "user_roles")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
