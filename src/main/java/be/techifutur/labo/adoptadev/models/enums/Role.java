package be.techifutur.labo.adoptadev.models.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    RECRUITER,
    DEVELOPER,
    ADMIN;

    public String getAuthorityString(){
        return "ROLE_"+this.toString();
    }

    public GrantedAuthority getAuthority(){
        return new SimpleGrantedAuthority(getAuthorityString());
    }
}
