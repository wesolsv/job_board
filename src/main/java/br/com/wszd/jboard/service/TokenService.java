package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class TokenService {

    private static final long expirationTime = 18000000;
    private String key = "String Aleatoria";

    public String genereteToken(PersonDTO person){
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject("Teste Jwt API")
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
