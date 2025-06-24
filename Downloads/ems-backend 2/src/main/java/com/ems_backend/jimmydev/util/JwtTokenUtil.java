package com.ems_backend.jimmydev.util;


import com.ems_backend.jimmydev.contants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private static final long JWT_EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 1 day in milliseconds

    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(
            ApplicationConstants.JWT_SECRET_DEFAULT_VALUE.getBytes(StandardCharsets.UTF_8)
    );

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles",userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
    /// extract username and check if it matches the username provided Userdetails
    /// and check if the token is expired
    public boolean validToken(String token, UserDetails userDetails){
        final  String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }



    /// It uses this to extract the expiration date of the token
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    ///  extract token to check what claims such as username,roles are in the jwt
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /// It extracts the token expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public  String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
