package be.techifutur.labo.adoptadev.models.entities;


import be.techifutur.labo.adoptadev.models.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@Inheritance
public class User implements UserDetails {

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

    @Column(name = "user_enabled")
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
