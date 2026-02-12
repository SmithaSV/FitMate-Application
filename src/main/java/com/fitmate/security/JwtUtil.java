package com.fitmate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class JwtUtil {
    private static final String SECRET = "fitmate-secret-key-fitmate-secret-key";
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractEmail(String token){
        Claims claims=Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();

    }
    private boolean isExpired(String token){
        Claims claims=Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration().before(new Date());

    }
    public boolean validateToken(String token,String email){
        try {
            String extractedEmail=extractEmail(token);
            return extractedEmail.equals(email) && !isExpired(token);
        }catch(Exception e){
            return false;

        }
    }
}
