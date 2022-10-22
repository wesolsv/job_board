package br.com.wszd.jboard.security;

import br.com.wszd.jboard.model.Role;
import io.jsonwebtoken.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    public static String create(String prefix,String key, JWTObject jwtObject) {
        String token = Jwts.builder()
                .setSubject(jwtObject.getEmail())
                .setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return prefix + " " + token;
    }
    public static JWTObject create(String token,String prefix,String key)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        JWTObject object = new JWTObject();
        token = token.replace(prefix, "");
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        object.setEmail(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());
        object.setRoles((List) claims.get(ROLES_AUTHORITIES));
        return object;

    }
    private static List<String> checkRoles(List<Role> roles) {

        List<String> ret = new ArrayList<>();

        for (Role r: roles){
            ret.add(new String("ROLE_"+r.getName()));
        }

        return ret;
    }

//user.get().getRoles().stream().map(role -> {
//                    return new SimpleGrantedAuthority("ROLE_" + role.getName());
//                }).collect(Collectors.toList());
}
