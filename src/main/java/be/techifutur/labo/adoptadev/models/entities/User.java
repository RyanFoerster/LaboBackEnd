package be.techifutur.labo.adoptadev.models.entities;


import be.techifutur.labo.adoptadev.models.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_username", nullable = false, unique = true)
    private String username;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Column(name = "user_first_name", nullable = false)
    private String firstName;
    @Column(name = "user_last_name", nullable = false)
    private String lastName;
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_description")
    private String description;

    @Column(name = "user_roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "user_enabled")
    private boolean isEnabled = true;

    @OneToMany(mappedBy = "emitter", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Message> messagesEmitter = new LinkedHashSet<>();

    @OneToMany(mappedBy = "receptor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Message> messagesReceptor = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
