package com.estsoft13.matdori.domain;

import com.estsoft13.matdori.util.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Table(name = "Users")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
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

    @Column(name ="username", nullable = false)
    private  String username;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name ="password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name ="role", nullable = false)
    private Role role;
    @Override
    public String getUsername() {
        return email;
    }

    public String getEnteredUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}