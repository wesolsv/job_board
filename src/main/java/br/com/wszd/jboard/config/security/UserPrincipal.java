package br.com.wszd.jboard.config.security;

import br.com.wszd.jboard.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal (Users users){
        this.email = users.getEmail();
        this.password = users.getPassword();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // ROLE_ADMIN, ROLE_USER
        authorities = users.getRoles().stream().map(role -> {
            return new SimpleGrantedAuthority("ROLE_" + role.getName());
        }).collect(Collectors.toList());

        this.authorities = authorities;
    }

    public static UserPrincipal create(Users users){
        return new UserPrincipal(users);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
