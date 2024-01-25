package com.fincons.jwt;

import com.fincons.auth.CustomAuthenticationProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate; // changed in long because expireDate is long

    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        // Generate JWT token
        String token =  Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }


    // get email from JWT token
    public String getEmailFromJWT(String token) {

        Claims claims =
                (Claims) Jwts.parserBuilder()
                        .setSigningKey(key())
                        .build()
                        .parse(token)
                        .getBody();


        String email = claims.getSubject();

        return email;
    }

    // Validate JWT token
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            // if analysis goes up return  true
            return true;
        } catch (JwtException e) {
            // if analysis goes down return false and exception
            return false;
        }
    }
    // key for JWT token generation and verification
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }



}
