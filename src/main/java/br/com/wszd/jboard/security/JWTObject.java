package br.com.wszd.jboard.security;

import br.com.wszd.jboard.model.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JWTObject {

    private String email;
    private Date issuedAt;
    private Date expiration;
    private List<String> roles;


    public void setRoles(String... roles){
        this.roles = Arrays.asList(roles);
    }
    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
