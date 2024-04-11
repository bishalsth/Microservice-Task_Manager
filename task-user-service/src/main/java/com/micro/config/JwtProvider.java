package com.micro.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class JwtProvider {

    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_KEY.getBytes());

    public static String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);
//        roles=ROLE_ADMIN,ROLE_USER

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+864000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();

return jwt;
    }

    public static String getEmailFrowJwtToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email =String.valueOf(claims.get("email"));
        return email;

    }

    public static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }


}
