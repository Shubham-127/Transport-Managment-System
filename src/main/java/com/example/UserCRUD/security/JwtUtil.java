package com.example.UserCRUD.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

private SecretKey getSigningKey(){
    return Keys.hmacShaKeyFor(JwtCredential.SECRET.getBytes());

}

public String generateToken(String email){
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+ JwtCredential.EXPIRY))
            .signWith(getSigningKey())
            .compact();

}
public String extractEmail(String token){
    return getClaims(token).getSubject();
}

public boolean isTokenExpired(String token){
    return getClaims(token).getExpiration().before(new Date());
}
public boolean validateToken(String token, String email){
    String extractedEmail = extractEmail(token);
    return extractEmail(token).equals(email)&& !isTokenExpired(token);
}
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
