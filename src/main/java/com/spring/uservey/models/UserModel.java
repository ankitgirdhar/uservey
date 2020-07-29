package com.spring.uservey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
public class UserModel extends Audit implements UserDetails {

    @Getter
    @Setter
    @Column(unique = true)
    @NotBlank(message = "Enter email id")
    @Email( message = "Email syntax is invalid!")
    private String username;

    @Getter
    @Setter
    @NotBlank(message = "Enter full name")
    private String fullname;

    @Getter
    @Setter
    @NotBlank(message = "Password field is required")
    private String password;

    @Getter
    @Setter
    private String confirmPassword;

    @Getter
    @Setter
    private Long credits;

    public UserModel() {
        setCredits(1L);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
    public boolean isEnabled() {
        return true;
    }
}
