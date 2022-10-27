package br.com.wszd.jboard.security;

import br.com.wszd.jboard.model.Role;

import java.util.Date;
import java.util.List;

public class JWTObject {

    private String subject;
    private Date issuedAt;
    private Date expiration;
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
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
