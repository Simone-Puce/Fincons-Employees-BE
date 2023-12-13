package com.fincons.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {   // It contains methods for generating, parsing, and validating JWT tokens.

    // For SIGNATURE.
    public static final String SECRET = "32536956584729542659842569";

    // Extracts Username thanks to token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // This method extracts the expiration date from the JWT token’s claims.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // It takes a JWT token and a `Function` that specifies how to extract the desired claim
    // and returns the extracted claim.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //It uses the `Jwts` builder to create a parser that is configured with the appropriate signing key and then extracts the token’s claims.
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // This method checks whether a JWT token has expired by comparing the token’s expiration date (obtained using `extractExpiration`) to the current date.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //  This method is used to validate a JWT token.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //This method is used to generate a JWT token. It takes a username as input, creates a set of claims
    //and then builds a JWT token using the claims and the signing key.
    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

//    This method is responsible for creating the JWT token. It uses the `Jwts` builder to specify the claims, subject,
//    issue date, expiration date, and the signing key. The token is then signed and compacted to produce the final JWT token, which is returned.
    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

//    This method is used to obtain the signing key for JWT token creation and validation.
//    It decodes the `SECRET` key, which is typically a Base64-encoded key, and converts it into
//    a cryptographic key using the `Keys.hmacShaKeyFor` method.
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
